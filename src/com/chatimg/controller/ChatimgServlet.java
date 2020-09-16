package com.chatimg.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.chatimg.model.ChatimgService;
import com.chatimg.model.ChatimgVO;

@WebServlet("/ChatimgServlet")
@MultipartConfig()
public class ChatimgServlet extends HttpServlet implements Serializable{
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		res.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		
		if ("chatimg".equals(action)) {
			System.out.println("ServletController-chatimg in");
			ChatimgService ciSvc = new ChatimgService();
			ChatimgVO ciVO = new ChatimgVO();
			byte[] img = null;
			
			String img_name = req.getParameter("img_name");
			Part part = req.getPart("file");
			java.io.InputStream in = part.getInputStream();
			byte[] buf = new byte[in.available()];
			in.read(buf);
			img = buf;
			
			ciVO.setImg(img);
			ciVO.setImg_name(img_name);
			
			ciSvc.insert(ciVO);
		}
	}

}
