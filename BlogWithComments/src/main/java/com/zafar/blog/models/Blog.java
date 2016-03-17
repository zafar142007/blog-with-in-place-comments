package com.zafar.blog.models;

import java.io.Serializable;

public class Blog implements Serializable{

	protected String id;
	protected String title;
	protected String plainText;
	public Blog(){
		
	}
	public Blog(String title, String text){
		this.title=title;
		plainText=text;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setPlainText(String text) {
		this.plainText = text;
	}
	public String toString(){
		return title+" "+plainText;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlainText() {
		return plainText;
	}
}
