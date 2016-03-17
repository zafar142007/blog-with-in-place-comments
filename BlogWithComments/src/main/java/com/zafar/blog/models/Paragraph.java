package com.zafar.blog.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paragraph implements Serializable{
	
	private String paragraphId;
	private String paragraph;
	private List<String> comments=new ArrayList<String>();
	public Paragraph(){}
	
	public Paragraph(String n, String p){
		paragraphId=n;
		paragraph=p;		
	}
	public String getParagraph() {
		return paragraph;
	}
	public void setParagraph(String paragraph) {
		this.paragraph = paragraph;
	}
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	public String getParagraphId() {
		return paragraphId;
	}
	public void setParagraphId(String paragraphNumber) {
		this.paragraphId = paragraphNumber;
	}
	public String toString(){
		return paragraphId+" "+paragraph+" "+comments;
	}
	
}
