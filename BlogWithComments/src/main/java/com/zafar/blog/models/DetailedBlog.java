package com.zafar.blog.models;

public class DetailedBlog extends Blog {
	
	private Paragraph[] paragraphs;
	
	public DetailedBlog(){
		super();
	}
	
	public DetailedBlog(String id, String title, String plainText){
		super(title, plainText);
		setId(id);
		mapParagraphs();
	}

	private void mapParagraphs() {
		String paras[]=plainText.split("\n\n");
		int i=0;
		this.paragraphs=new Paragraph[paras.length];
		for(String para:paras){			
			this.paragraphs[i]=new Paragraph(""+i, para);
			i++;
		}
	}
	public static void main(String arg[]){
		DetailedBlog blog=new DetailedBlog("asda","asdaSa","asdasdasd\n\nasasdadasds\n\naw");
		blog.mapParagraphs();
		System.out.println(blog.getParagraphs()[0].getParagraph()+" "+blog.getParagraphs()[1].getParagraph()+" "+blog.getParagraphs()[2].getParagraph());
	}

	public Paragraph[] getParagraphs() {
		return paragraphs;
	}

	public void setParagraphs(Paragraph[] paragraphs) {
		this.paragraphs = paragraphs;
	}
}
