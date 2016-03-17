package com.zafar.blog.models;

public class BlogList {

	private int currentPage;
	private int totalPages;
	private Blog[] blogs;
	
	public BlogList(){
		
	}
	public BlogList(int currentPage, int totalPages, Blog[] blogs){
		this.currentPage=currentPage;
		this.totalPages=totalPages;
		this.blogs=blogs;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public Blog[] getBlogs() {
		return blogs;
	}
	public void setBlogs(Blog[] blogs) {
		this.blogs = blogs;
	}	
}
