package com.zafar.blog.util;

public class Constants {
	public static final String TABLE_BLOG="blog";
	public static final String TABLE_COMMENT="detailed_blog";
	public static final String TABLE_BLOG_PARAGRAPHS="detailed_blog_paragraphs";
	public static final String ADD_BLOG = "insert into "+TABLE_BLOG+" (id, title, plainText) VALUES (?,?,?)";
	public static final String ADD_BLOG_PARAGRAPHS = "insert into "+TABLE_BLOG_PARAGRAPHS+" (blogId, paragraphId, paragraph) VALUES (?,?,?)";
	public static final String SUCCESS_MESSAGE = "OK";
	public static final String ERROR_MESSAGE = "ERROR";
	public static final String GET_ALL_BLOGS = "select * from "+TABLE_BLOG;
	public static final int PAGE_SIZE = 5;
	public static final String ERROR_MESSAGE_INVALID_PAGE = "Invalid page";
	public static final String SELECT_BLOG = "select blog.title, blog.plainText from blog where blog.id='";
	public static final String SELECT_PARAGRAPHS = "select detailed_blog_paragraphs.paragraphId, detailed_blog_paragraphs.paragraph from blog,  detailed_blog_paragraphs  where  blog.id=detailed_blog_paragraphs.blogId  and blog.id='";
	public static final String SELECT_COMMENTS = "select detailed_blog.paragraphId, detailed_blog.comment from blog, detailed_blog, detailed_blog_paragraphs  where  blog.id=detailed_blog.id and  blog.id=detailed_blog_paragraphs.blogId and detailed_blog.paragraphId=detailed_blog_paragraphs.paragraphId and blog.id='";
	public static final String ADD_COMMENT = "insert into "+TABLE_COMMENT+" (id, paragraphId, comment_id,comment) VALUES (?,?,?,?)";
}
