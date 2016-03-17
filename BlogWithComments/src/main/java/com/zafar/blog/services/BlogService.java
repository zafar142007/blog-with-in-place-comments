package com.zafar.blog.services;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zafar.blog.dao.BlogDao;
import com.zafar.blog.models.Blog;
import com.zafar.blog.models.DetailedBlog;

@Service
public class BlogService {
	private static final Logger logger = LoggerFactory.getLogger(BlogService.class);
	
	@Autowired
	private BlogDao dao;
	
	public String addBlog(Blog blog){
		return dao.addBlog(blog);
	}
	/**
	 * 
	 * @param pageSize is the number of blogs in a single page
	 * @param pageNumber is the number of total pages in this resultset
	 * @return
	 */
	@Cacheable("blog_cache")
	public Blog[] getAPageOfBlogs(int pageSize, int pageNumber){
		Blog[] list= dao.getAllBlogs();
		Blog aPageOfBlogs[]=null;
		if(list.length>=(pageNumber+1)*pageSize)
			aPageOfBlogs=Arrays.copyOfRange(list, pageNumber*pageSize, ((pageNumber+1)*pageSize));
		else
			aPageOfBlogs=Arrays.copyOfRange(list, pageNumber*pageSize, list.length);
		return aPageOfBlogs;
	}
	public int getTotalRecords(){
		return dao.getAllBlogs().length;
	}
	@Cacheable("blog_cache")
	public DetailedBlog getDetailedBlog(String id) {
		return dao.getDetailedBlog(id);
	}
	public String addComment(String blogId, String paraId, String comment) {
		
		return dao.addComment( blogId, paraId, comment);
	}
	
}
