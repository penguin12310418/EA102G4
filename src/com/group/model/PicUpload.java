package com.group.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.grouplist.model.GroupListVO;

public class PicUpload implements GroupDAO_interface {
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "INGYM";
	String passwd = "123456";

	private static final String INSERT = "INSERT INTO INGROUP(GRO_NO, MEM_ID, TYPE_NO, GRO_NAME, GRO_INTRO, GRO_START, GRO_END, GRO_ADDR, GRO_MIN, GRO_MAX, GRO_PIC, GRO_LAT, GRO_LNG, GRO_RATING, GRO_MEM, GRO_STATUS, GRO_TIME) VALUES('G'||LPAD(GROUP_SEQ.NEXTVAL,5,0), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 0, 'G0', SYSDATE)";
	private static final String GETALL = "SELECT * FROM INGROUP WHERE GRO_STATUS = 'G0' ORDER BY GRO_START";
	private static final String UPDATE = "UPDATE INGROUP SET TYPE_NO = ?, GRO_NAME = ?, GRO_INTRO = ?, GRO_START = ?, GRO_END = ?, GRO_ADDR = ?, GRO_MIN = ?, GRO_MAX = ?, GRO_PIC = ?, GRO_STATUS = ?, GRO_LAT = ?, GRO_LNG = ? WHERE GRO_NO = ?";
	private static final String GET_ONE_GROUP_GRONO = "SELECT * FROM INGROUP WHERE GRO_NO = ?";
	private static final String GET_TYPE_GROUP = "SELECT * FROM INGROUP WHERE TYPE_NO = ? AND GRO_STATUS = 'G0' ORDER BY GRO_TIME DESC";
	private static final String GET_ALL_GROUP_BY_MEMBER = "SELECT * FROM INGROUP WHERE MEM_ID = ? ORDER BY GRO_TIME DESC";
	private static final String GET_GROUP_BY_GRO_NO = "SELECT * FROM INGROUP WHERE GRO_NO = ?";
	private static final String UPDATE_RATING_MEM = "UPDATE INGROUP SET GRO_RATING = ?, GRO_MEM = ? WHERE GRO_NO = ?";
	private static final String GETALL_G2 = "SELECT * FROM INGROUP WHERE GRO_STATUS = 'G2' ORDER BY GRO_TIME DESC";
	
	
	@Override
	public List<GroupVO> findByGroupName(String word) {
		List<GroupVO> list = new ArrayList<GroupVO>();
		String upperWord = word.toUpperCase();
		GroupVO groupVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(
					"SELECT * FROM INGROUP WHERE UPPER(GRO_NAME) LIKE '%" + upperWord + "%' AND GRO_STATUS = 'G0' ORDER BY GRO_TIME DESC");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupVO = new GroupVO();
				groupVO.setGro_no(rs.getString("gro_no"));
				groupVO.setMem_id(rs.getString("mem_id"));
				groupVO.setType_no(rs.getString("type_no"));
				groupVO.setGro_name(rs.getString("gro_name"));
				groupVO.setGro_intro(rs.getString("gro_intro"));
				groupVO.setGro_start(rs.getTimestamp("gro_start"));
				groupVO.setGro_end(rs.getTimestamp("gro_end"));
				groupVO.setGro_addr(rs.getString("gro_addr"));
				groupVO.setGro_min(rs.getInt("gro_min"));
				groupVO.setGro_max(rs.getInt("gro_max"));
				groupVO.setGro_pic(rs.getBytes("gro_pic"));

				list.add(groupVO);
			}

			System.out.println("查詢揪團名稱含有:" + word + "字樣,成功");

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
	public void scoreUpdate(String gro_no, Integer gro_rating, Integer gro_mem) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_RATING_MEM);

			pstmt.setInt(1, gro_rating);
			pstmt.setInt(2, gro_mem);
			pstmt.setString(3, gro_no);
			

			pstmt.executeUpdate();

			System.out.println("揪團編號:" + gro_no + ",評分更新成功");

			// Handle any driver errors
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
	}
	
	@Override
	public void delete(String gro_no) {
		GroupVO groupVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_GROUP_BY_GRO_NO);

			pstmt.setString(1, gro_no);
			pstmt.executeUpdate();
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				groupVO = new GroupVO();
				groupVO.setGro_no(rs.getString("gro_no"));
				groupVO.setMem_id(rs.getString("mem_id"));
				groupVO.setType_no(rs.getString("type_no"));
				groupVO.setGro_name(rs.getString("gro_name"));
				groupVO.setGro_intro(rs.getString("gro_intro"));
				groupVO.setGro_start(rs.getTimestamp("gro_start"));
				groupVO.setGro_end(rs.getTimestamp("gro_end"));
				groupVO.setGro_addr(rs.getString("gro_addr"));
				groupVO.setGro_min(rs.getInt("gro_min"));
				groupVO.setGro_max(rs.getInt("gro_max"));
				groupVO.setGro_pic(rs.getBytes("gro_pic"));
				groupVO.setGro_status(rs.getString("gro_status"));
				groupVO.setGro_lat(rs.getDouble("gro_lat"));
				groupVO.setGro_lng(rs.getDouble("gro_lng"));
			}
			
			groupVO.setGro_status("G1");
			pstmt.close();
			
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, groupVO.getType_no());
			pstmt.setString(2, groupVO.getGro_name());
			pstmt.setString(3, groupVO.getGro_intro());
			pstmt.setTimestamp(4, groupVO.getGro_start());
			pstmt.setTimestamp(5, groupVO.getGro_end());
			pstmt.setString(6, groupVO.getGro_addr());
			pstmt.setInt(7, groupVO.getGro_min());
			pstmt.setInt(8, groupVO.getGro_max());
			pstmt.setBytes(9, groupVO.getGro_pic());
			pstmt.setString(10, groupVO.getGro_status());
			pstmt.setDouble(11, groupVO.getGro_lat());
			pstmt.setDouble(12, groupVO.getGro_lng());
			pstmt.setString(13, groupVO.getGro_no());
			

			pstmt.executeUpdate();

			System.out.println("更改揪團編號:" + gro_no+",Status = "+ groupVO.getGro_status());

			// Handle any driver errors
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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

	}
	
	@Override
	public GroupVO getGroupByGroNo(String gro_no) {
		GroupVO groupVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_GROUP_BY_GRO_NO);
			pstmt.setString(1, gro_no);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupVO = new GroupVO();
				groupVO.setGro_no(rs.getString("gro_no"));
				groupVO.setMem_id(rs.getString("mem_id"));
				groupVO.setType_no(rs.getString("type_no"));
				groupVO.setGro_name(rs.getString("gro_name"));
				groupVO.setGro_intro(rs.getString("gro_intro"));
				groupVO.setGro_start(rs.getTimestamp("gro_start"));
				groupVO.setGro_end(rs.getTimestamp("gro_end"));
				groupVO.setGro_addr(rs.getString("gro_addr"));
				groupVO.setGro_min(rs.getInt("gro_min"));
				groupVO.setGro_max(rs.getInt("gro_max"));
				groupVO.setGro_pic(rs.getBytes("gro_pic"));
				groupVO.setGro_status(rs.getString("gro_status"));
			}

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
		return groupVO;
	}
	
		
	@Override
	public List<GroupVO> findGroupByMember(String mem_id) {
		List<GroupVO> list = new ArrayList<GroupVO>();
		GroupVO groupVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_GROUP_BY_MEMBER);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupVO = new GroupVO();
				groupVO.setGro_no(rs.getString("gro_no"));
				groupVO.setMem_id(rs.getString("mem_id"));
				groupVO.setType_no(rs.getString("type_no"));
				groupVO.setGro_name(rs.getString("gro_name"));
				groupVO.setGro_intro(rs.getString("gro_intro"));
				groupVO.setGro_start(rs.getTimestamp("gro_start"));
				groupVO.setGro_end(rs.getTimestamp("gro_end"));
				groupVO.setGro_addr(rs.getString("gro_addr"));
				groupVO.setGro_min(rs.getInt("gro_min"));
				groupVO.setGro_max(rs.getInt("gro_max"));
				groupVO.setGro_pic(rs.getBytes("gro_pic"));
				groupVO.setGro_status(rs.getString("gro_status"));
				groupVO.setGro_mem(rs.getInt("gro_mem"));
				groupVO.setGro_rating(rs.getInt("gro_rating"));

				list.add(groupVO);
			}

			System.out.println("查詢所有揪團成功");

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
	public void insert(GroupVO groupVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);
			pstmt.setString(1, groupVO.getMem_id());
			pstmt.setString(2, groupVO.getType_no());
			pstmt.setString(3, groupVO.getGro_name());
			pstmt.setString(4, groupVO.getGro_intro());
			pstmt.setTimestamp(5, groupVO.getGro_start());
			pstmt.setTimestamp(6, groupVO.getGro_end());
			pstmt.setString(7, groupVO.getGro_addr());
			pstmt.setInt(8, groupVO.getGro_min());
			pstmt.setInt(9, groupVO.getGro_max());
			pstmt.setBytes(10, groupVO.getGro_pic());
			pstmt.setDouble(11, groupVO.getGro_lat());
			pstmt.setDouble(12, groupVO.getGro_lng());
			
			pstmt.executeUpdate();
			
			System.out.println("揪團新增成功");

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
	}

	@Override
	public GroupVO findByPrimaryKey(String gro_no) {

		GroupVO groupVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_GROUP_GRONO);
			pstmt.setString(1, gro_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupVO = new GroupVO();
				groupVO.setGro_no(rs.getString("gro_no"));
				groupVO.setMem_id(rs.getString("mem_id"));
				groupVO.setType_no(rs.getString("type_no"));
				groupVO.setGro_name(rs.getString("gro_name"));
				groupVO.setGro_intro(rs.getString("gro_intro"));
				groupVO.setGro_start(rs.getTimestamp("gro_start"));
				groupVO.setGro_end(rs.getTimestamp("gro_end"));
				groupVO.setGro_addr(rs.getString("gro_addr"));
				groupVO.setGro_min(rs.getInt("gro_min"));
				groupVO.setGro_max(rs.getInt("gro_max"));
				groupVO.setGro_pic(rs.getBytes("gro_pic"));
				groupVO.setGro_status(rs.getString("gro_status"));
				groupVO.setGro_lat(rs.getDouble("gro_lat"));
				groupVO.setGro_lng(rs.getDouble("gro_lng"));
				groupVO.setGro_rating(rs.getInt("gro_rating"));
				groupVO.setGro_mem(rs.getInt("gro_mem"));
				
			}

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new RuntimeException("A database error occured. "
							+ e.getMessage());
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw new RuntimeException("A database error occured. "+ e.getMessage());
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					throw new RuntimeException("A database error occured. "+ e.getMessage());
				}
			}
		}
		return groupVO;
	}

	@Override
	public List<GroupVO> getAll_G2() {
		List<GroupVO> list = new ArrayList<GroupVO>();
		GroupVO groupVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pstmt = con.prepareStatement(GETALL_G2);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupVO = new GroupVO();
				groupVO.setGro_no(rs.getString("gro_no"));
				groupVO.setMem_id(rs.getString("mem_id"));
				groupVO.setType_no(rs.getString("type_no"));
				groupVO.setGro_name(rs.getString("gro_name"));
				groupVO.setGro_intro(rs.getString("gro_intro"));
				groupVO.setGro_start(rs.getTimestamp("gro_start"));
				groupVO.setGro_end(rs.getTimestamp("gro_end"));
				groupVO.setGro_addr(rs.getString("gro_addr"));
				groupVO.setGro_min(rs.getInt("gro_min"));
				groupVO.setGro_max(rs.getInt("gro_max"));
				
				list.add(groupVO);
			}

//			System.out.println("查詢所有揪團成功");

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
	public List<GroupVO> getAll() {
		List<GroupVO> list = new ArrayList<GroupVO>();
		GroupVO groupVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupVO = new GroupVO();
				groupVO.setGro_no(rs.getString("gro_no"));
				groupVO.setMem_id(rs.getString("mem_id"));
				groupVO.setType_no(rs.getString("type_no"));
				groupVO.setGro_name(rs.getString("gro_name"));
				groupVO.setGro_intro(rs.getString("gro_intro"));
				groupVO.setGro_start(rs.getTimestamp("gro_start"));
				groupVO.setGro_end(rs.getTimestamp("gro_end"));
				groupVO.setGro_addr(rs.getString("gro_addr"));
				groupVO.setGro_min(rs.getInt("gro_min"));
				groupVO.setGro_max(rs.getInt("gro_max"));
				groupVO.setGro_lat(rs.getDouble("gro_lat"));
				groupVO.setGro_lng(rs.getDouble("gro_lng"));
				groupVO.setGro_rating(rs.getInt("gro_rating"));
				groupVO.setGro_mem(rs.getInt("gro_mem"));
				groupVO.setGro_status(rs.getString("gro_status"));
				groupVO.setGro_time(rs.getTimestamp("gro_time"));
				
				list.add(groupVO);
			}

//			System.out.println("查詢所有揪團成功");

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
	public Set<GroupVO> getEmpsByDeptno(String gro_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(GroupVO groupVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, groupVO.getType_no());
			pstmt.setString(2, groupVO.getGro_name());
			pstmt.setString(3, groupVO.getGro_intro());
			pstmt.setTimestamp(4, groupVO.getGro_start());
			pstmt.setTimestamp(5, groupVO.getGro_end());
			pstmt.setString(6, groupVO.getGro_addr());
			pstmt.setInt(7, groupVO.getGro_min());
			pstmt.setInt(8, groupVO.getGro_max());
			pstmt.setBytes(9, groupVO.getGro_pic());
			pstmt.setString(10, groupVO.getGro_status());
			pstmt.setDouble(11, groupVO.getGro_lat());
			pstmt.setDouble(12, groupVO.getGro_lng());
			pstmt.setString(13, groupVO.getGro_no());

			pstmt.executeUpdate();

			System.out.println("揪團編號:" + groupVO.getGro_no() + ",更新成功");

			// Handle any driver errors
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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

	}

	@Override
	public List<GroupVO> findByCity(String cityScope) {
		List<GroupVO> list = new ArrayList<GroupVO>();
		GroupVO groupVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pstmt = con.prepareStatement(
					"SELECT * FROM INGROUP WHERE GRO_ADDR LIKE '%" + cityScope + "%' AND GRO_STATUS = 'G0' ORDER BY GRO_TIME DESC");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupVO = new GroupVO();
				groupVO.setGro_no(rs.getString("gro_no"));
				groupVO.setMem_id(rs.getString("mem_id"));
				groupVO.setType_no(rs.getString("type_no"));
				groupVO.setGro_name(rs.getString("gro_name"));
				groupVO.setGro_intro(rs.getString("gro_intro"));
				groupVO.setGro_start(rs.getTimestamp("gro_start"));
				groupVO.setGro_end(rs.getTimestamp("gro_end"));
				groupVO.setGro_addr(rs.getString("gro_addr"));
				groupVO.setGro_min(rs.getInt("gro_min"));
				groupVO.setGro_max(rs.getInt("gro_max"));
				groupVO.setGro_pic(rs.getBytes("gro_pic"));

				list.add(groupVO);
			}

			System.out.println("查詢地區:" + cityScope + ",成功");

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
	public List<GroupVO> findByType(String typeScope) {
		List<GroupVO> list = new ArrayList<GroupVO>();
		GroupVO groupVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pstmt = con.prepareStatement(GET_TYPE_GROUP);

			pstmt.setString(1, typeScope);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupVO = new GroupVO();
				groupVO.setGro_no(rs.getString("gro_no"));
				groupVO.setMem_id(rs.getString("mem_id"));
				groupVO.setType_no(rs.getString("type_no"));
				groupVO.setGro_name(rs.getString("gro_name"));
				groupVO.setGro_intro(rs.getString("gro_intro"));
				groupVO.setGro_start(rs.getTimestamp("gro_start"));
				groupVO.setGro_end(rs.getTimestamp("gro_end"));
				groupVO.setGro_addr(rs.getString("gro_addr"));
				groupVO.setGro_min(rs.getInt("gro_min"));
				groupVO.setGro_max(rs.getInt("gro_max"));

				list.add(groupVO);
			}

			System.out.println("類別:" + typeScope + "揪團,收尋成功");

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
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
}
