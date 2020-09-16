package com.chatimg.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ChatimgJDBCDAO implements ChatimgDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/INGYM");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT = "INSERT INTO CHATIMG(IMG_NO, IMG_NAME, IMG) VALUES('CI'||LPAD(GROUP_SEQ.NEXTVAL,5,0), ?, ?)";
	
	@Override
	public String insert(ChatimgVO chatimgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			pstmt.setString(1, chatimgVO.getImg_name());
			pstmt.setBytes(2, chatimgVO.getImg());
		
			pstmt.executeUpdate();
			
			System.out.println("聊天室照片上傳成功");

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "+ e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return chatimgVO.getImg_name();
	}
}