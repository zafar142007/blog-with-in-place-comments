package com.zafar.blog.dao;

import java.lang.reflect.Method;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.zafar.blog.models.Blog;
import com.zafar.blog.models.DetailedBlog;
import com.zafar.blog.models.Paragraph;
import com.zafar.blog.util.Constants;

@Component
public class BlogDao {
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(BlogDao.class);

	@PostConstruct
	public void init(){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public String addBlog(Blog blog){
		
		String id=Long.toString((long)(Math.abs(UUID.randomUUID().getLeastSignificantBits()/Math.pow(10,11))));
		blog.setId(id);
		Object[] params = new Object[] {id, blog.getTitle(), blog.getPlainText()};		
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
		int rowsUpdated=jdbcTemplate.update(Constants.ADD_BLOG, params, types);
		DetailedBlog detailedBlog=new DetailedBlog(id, blog.getTitle(), blog.getPlainText());
		int i=0, rowsUpdatedP=0;
		for(Paragraph p:detailedBlog.getParagraphs()){
			params = new Object[] {id, p.getParagraphId(),p.getParagraph()};		
			types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
			rowsUpdatedP=jdbcTemplate.update(Constants.ADD_BLOG_PARAGRAPHS, params, types);
			i++;
		}
		if(rowsUpdated==1 && rowsUpdatedP==detailedBlog.getParagraphs().length){
			return Constants.SUCCESS_MESSAGE;
		}
		else
			return Constants.ERROR_MESSAGE;
	}
	@Cacheable("blog_cache")
	public Blog[] getAllBlogs() {
		List<Map<String, Object>> list=jdbcTemplate.queryForList(Constants.GET_ALL_BLOGS);
		Class blogClass=Blog.class;
		Method[] methods = blogClass.getMethods();
		Map<String, Method> map=new HashMap<String, Method>();
		String name="";
		Blog[] blogs=new Blog[list.size()];
		for(Method m:methods){
			name=m.getName();
			
			//extract all setters
			if(name.contains("set"))
				map.put(name.substring(name.indexOf("set")+3).toLowerCase(), m);
		}
		logger.info("all setters: {}",map);
		int i=0;
		Blog blog=null;
		for(Map<String, Object> row: list){
			logger.info("Row: {}",row);
			blog=new Blog();
			for(String value: row.keySet()){
				try {
					//populate the pojo
					if(map.get(value.toLowerCase())!=null)
						map.get(value.toLowerCase()).invoke(blog, row.get(value));					
				} catch (Exception e) {
					logger.error("Error",e);
				}
			}
			logger.info(""+blog);
			blogs[i]=blog;
			i++;
		}
		return blogs;
	}

	public DetailedBlog getDetailedBlog(String id) {
		DetailedBlog detailedBlog=new DetailedBlog();
		detailedBlog.setId(id);
		Map<String, Object> blog=jdbcTemplate.queryForMap(Constants.SELECT_BLOG+id+"'");
		List<Map<String, Object>> paragraphs=jdbcTemplate.queryForList(Constants.SELECT_PARAGRAPHS+id+"'");
		List<Map<String, Object>> comments=jdbcTemplate.queryForList(Constants.SELECT_COMMENTS+id+"'");
		detailedBlog.setTitle((String)blog.get("title"));
		detailedBlog.setPlainText((String)blog.get("plainText"));
		Paragraph para=null;
		List<Paragraph> list=new ArrayList<Paragraph>();
		Map<String,Paragraph> map = new HashMap<String, Paragraph>();
		logger.debug(comments.size()+" "+paragraphs.size());
		if(paragraphs.size()>0){
			for(Map<String, Object> paragraph: paragraphs){
				para=new Paragraph();
				para.setParagraphId((String)paragraph.get("paragraphId"));
				para.setParagraph((String)paragraph.get("paragraph"));
				map.put(para.getParagraphId(), para);
			}
			logger.debug("map "+map.toString());
			for(Map<String, Object> comment: comments){
				para=map.get(comment.get("paragraphId"));
				logger.debug("paragraph "+para.toString());
				List<String> comm=para.getComments();
				if(comm==null){
					comm=new ArrayList<String>();
					para.setComments(comm);
				}
				comm.add((String)comment.get("comment"));							
				logger.debug("comment "+comm.toString());
			}
		}
		logger.debug(map.toString());

		detailedBlog.setParagraphs(map.values().toArray(new Paragraph[0]));
		return detailedBlog;
	}

	public String addComment(String blogId, String paraId, String comment) {
		String id=Long.toString((long)(Math.abs(UUID.randomUUID().getLeastSignificantBits()/Math.pow(10,11))));
		Object[] params = new Object[] {blogId, paraId, id, comment};		
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR};
		int rowsUpdated=0;
		try{
			rowsUpdated=jdbcTemplate.update(Constants.ADD_COMMENT, params, types);
		}catch(Exception e){
			logger.error("error",e);
		}
		if(rowsUpdated==1){
			return Constants.SUCCESS_MESSAGE;
		}
		else
			return Constants.ERROR_MESSAGE;
		
	}
}
