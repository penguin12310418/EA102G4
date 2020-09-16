package com.chatimg.model;

public class ChatimgVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String img_no;
	private String img_name;
	private byte[] img;

	public ChatimgVO() {
	}

	public String getImg_no() {
		return img_no;
	}

	public void setImg_no(String img_no) {
		this.img_no = img_no;
	}

	public String getImg_name() {
		return img_name;
	}

	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}
	
	
}
