package com.grouptype.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class GroupTypeJDBCDAO implements GroupTypeDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/INGYM");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GETALL = "SELECT * FROM GROUPTYPE";
	private static final String GETONE_GROUPTYPE_NAME = "SELECT * FROM GROUPTYPE WHERE TYPE_NO=?";
	
	
	
	@Override
	public void insert(GroupTypeVO grouptypeVO) {
		
	}

	@Override
	public void update(GroupTypeVO grouptypeVO) {
		
	}

	@Override
	public void delete(String type_no) {
		
	}

	@Override
	public GroupTypeVO findByPrimaryKey(String type_no) {
		GroupTypeVO grouptypeVO = null;
		
		Connection con =null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETONE_GROUPTYPE_NAME);
			
			pstmt.setString(1, type_no);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				grouptypeVO = new GroupTypeVO();
				grouptypeVO.setType_name(rs.getString("type_name"));
				grouptypeVO.setType_no(rs.getString("type_no"));
				
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return grouptypeVO;
	}

	@Override
	public List<GroupTypeVO> getAll() {
		List<GroupTypeVO> list = new ArrayList<GroupTypeVO>();
		GroupTypeVO grouptypeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				grouptypeVO = new GroupTypeVO();
				grouptypeVO.setType_no(rs.getString("type_no"));
				grouptypeVO.setType_name(rs.getString("type_name"));
				
				
				list.add(grouptypeVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public Set<GroupTypeVO> getEmpsByDeptno(String grouptypeVO) {
		return null;
	}
	
	public static void main(String[] args) {
		GroupTypeJDBCDAO grouptypeTest = new GroupTypeJDBCDAO();
		System.out.println(grouptypeTest.findByPrimaryKey("GT00001"));
	}
}	

	
