package com.zafar.blog.models;

public class Response {
	private String status="OK";
	private Object data;
	
	public Response(){
		
	}
	public Response(Object dat){
		data=dat;
	}
	public Response(String st, Object da){
		status=st;
		data=da;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
