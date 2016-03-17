package com.zafar.blog.controllers;

import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zafar.blog.models.Blog;
import com.zafar.blog.models.BlogComment;
import com.zafar.blog.models.BlogList;
import com.zafar.blog.models.DetailedBlog;
import com.zafar.blog.models.Response;
import com.zafar.blog.services.BlogService;
import com.zafar.blog.util.Constants;

@Controller
@RequestMapping("/blog")
public class MainController {
	
	@Autowired
	private BlogService blogService;
	
	@Value("${page.size.default}")
	private int pageSize;
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces="application/json")
	public @ResponseBody ResponseEntity<?> addBlog(@RequestBody String blogString) {
		logger.info("input "+blogString);
		
		Blog blog=null;
		try {
			blog=(new ObjectMapper()).readValue(URLDecoder.decode(blogString,"UTF-8"), Blog.class);
		} catch (Exception e) {
			logger.error("exception",e);
			return new ResponseEntity<Response>(new Response("Correct the JSON",""), HttpStatus.BAD_REQUEST);
		}				
		String status=blogService.addBlog(blog);
		return new ResponseEntity<Response>(new Response(blog),HttpStatus.OK);
	}
	/**
	 * 
	 * @param pageNumber begins from 1. If not provided, first page will be returned 
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseEntity<?> allBlogs(@RequestParam(value = "pageNumber", defaultValue="-100") int pageNumber) {
		logger.info("paged response for page "+pageNumber);
		if(pageNumber==-100)
			pageNumber=1;
		Blog blog=null;		
		int numberOfPages=(int)Math.ceil((double)blogService.getTotalRecords()/(double)pageSize) ;
		if(pageNumber>numberOfPages || pageNumber<1)
			return new ResponseEntity<Response>(new Response(Constants.ERROR_MESSAGE_INVALID_PAGE,""),HttpStatus.BAD_REQUEST);
		Blog blogs[]=blogService.getAPageOfBlogs(pageSize, pageNumber-1);
		return new ResponseEntity<Response>(new Response(new BlogList(pageNumber, numberOfPages ,blogs)),HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces="application/json")
	public @ResponseBody ResponseEntity<?> getDetailedBlog(@PathVariable String id){
		DetailedBlog blog=blogService.getDetailedBlog(id);
		return new ResponseEntity<Response>(new Response(blog), HttpStatus.OK);
	}

	@RequestMapping(value = "/{blogId}/paragraph/{paraId}", method = RequestMethod.POST, produces="application/json")
	public @ResponseBody ResponseEntity<?> addComment(@PathVariable String blogId, @PathVariable String paraId, @RequestBody String com){
		BlogComment comment = null;
		try {
			comment = (new ObjectMapper()).readValue(URLDecoder.decode(com,"UTF-8"), BlogComment.class);
		} 
		catch (Exception e) {
			logger.error("Error in parsing comment",e);
		}		
		String state=blogService.addComment(blogId, paraId,comment.getComment());
		return new ResponseEntity<Response>(new Response(state,comment.getComment()), HttpStatus.OK);
	}}
