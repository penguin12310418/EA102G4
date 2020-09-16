package com.chatimg.model;

public class ChatimgService {
	private ChatimgDAO_interface dao;
	
	public ChatimgService() {
		dao = new ChatimgJDBCDAO();
	}
	
	public String insert(ChatimgVO chatimgVO) {
		return dao.insert(chatimgVO);
	}
}
