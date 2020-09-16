package com.grouplist.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.group.model.GroupVO;

public class GroupListJDBCDAO implements GroupListDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/INGYM");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT = "INSERT INTO GROUPLIST(GROUPLIST_NO, GRO_NO, GRO_NAME, MEM_ID, MEM_NAME, RATING_STATUS, JOIN_TIME) VALUES('GL'||LPAD(GROUPLIST_SEQ.NEXTVAL,5,0), ?,?,?,?, 'GR0', SYSDATE)";
	private static final String GET_GROUP_MEMBER_LIST = "SELECT * FROM GROUPLIST WHERE GRO_NO = ?";
	private static final String DELETE = "DELETE FROM GROUPLIST WHERE GROUPLIST_NO = ?";
	private static final String UPDATE_SCORE_INTRO = "UPDATE GROUPLIST SET GRO_RATING_INTRO = ?, RATING_STATUS = 'GR1', RATING_NUM = ? WHERE GRO_NO = ? AND MEM_ID = ?";
	private static final String GET_JOIN_GROUP_BY_MEMBER = "SELECT GROUPLIST_NO, GRO_NO FROM GROUPLIST WHERE MEM_ID = ? ORDER BY JOIN_TIME DESC";
	private static final String EXIT_GROUP = "DELETE FROM GROUPLIST WHERE GRO_NO = ? AND MEM_ID = ?";
	private static final String GET_ONE_GROUPLIST = "SELECT * FROM GROUPLIST WHERE GRO_NO = ? AND MEM_ID = ?";
	
	
	@Override
	public GroupListVO getOneGroupList(String gro_no, String mem_id) {
		GroupListVO glVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_GROUPLIST);
			pstmt.setString(1, gro_no);
			pstmt.setString(2, mem_id);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				glVO = new GroupListVO();
				glVO.setGrouplist_no((rs.getString("grouplist_no")));
				glVO.setGro_no(rs.getString("gro_no"));
				glVO.setMem_id(rs.getString("mem_id"));
				glVO.setGro_name(rs.getString("gro_name"));
				glVO.setMem_name(rs.getString("mem_name"));
				glVO.setRating_status(rs.getString("rating_status"));
				glVO.setJoin_time(rs.getTimestamp("join_time"));
				glVO.setGro_rating_intro(rs.getString("gro_rating_intro"));
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
		return glVO;
	}
	
	@Override
	public void scoreIntroUpdate(String gro_rating_intro, String gro_no, String mem_id, Integer rating_num) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_SCORE_INTRO);

			pstmt.setString(1, gro_rating_intro);
			pstmt.setInt(2, rating_num);
			pstmt.setString(3, gro_no);
			pstmt.setString(4, mem_id);
			

			pstmt.executeUpdate();

			System.out.println("評論更新成功");

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
	public void exitGroup(String gro_no, String mem_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(EXIT_GROUP);

			pstmt.setString(1, gro_no);
			pstmt.setString(2, mem_id);
			pstmt.executeUpdate();
			
			
			System.out.println("exitGroup, 刪除揪團會員列表編號: gro_no = " + gro_no+", mem_id = "+mem_id);

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
	public List<GroupListVO> findJoinGroupByMember(String mem_id) {
		List<GroupListVO> list = new ArrayList<GroupListVO>();
		GroupListVO groupListVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_JOIN_GROUP_BY_MEMBER);
			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				groupListVO = new GroupListVO();
				groupListVO.setGro_no(rs.getString("gro_no"));
				groupListVO.setGrouplist_no(rs.getString("grouplist_no"));
				

				list.add(groupListVO);
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
	public void insert(GroupListVO grouplistVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);

			pstmt.setString(1, grouplistVO.getGro_no());
			pstmt.setString(2, grouplistVO.getGro_name());
			pstmt.setString(3, grouplistVO.getMem_id());
			pstmt.setString(4, grouplistVO.getMem_name());
			
			pstmt.executeUpdate();

			System.out.println(grouplistVO.getMem_name() + "成功加入" + grouplistVO.getGro_name() + "(揪團)");

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
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

	}

	@Override
	public void update(GroupListVO grouplistVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
	}

	@Override
	public void delete(String grouplist_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, grouplist_no);
			pstmt.executeUpdate();

			System.out.println("揪團團員列表編號:" + grouplist_no);

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
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

	}

	@Override
	public GroupListVO findByPrimaryKey(String grouplistVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		return null;
	}

	@Override
	public List<GroupListVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;

		return null;
	}

	@Override
	public Set<GroupListVO> getEmpsByDeptno(String grouplistVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		return null;
	}

	@Override
	public List<GroupListVO> findMemberByGroup(String gro_no) {
		List<GroupListVO> list = new ArrayList<GroupListVO>();
		GroupListVO glVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_GROUP_MEMBER_LIST);

			pstmt.setString(1, gro_no);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				glVO = new GroupListVO();
				glVO.setGro_name(rs.getString("gro_name"));
				glVO.setGro_no(rs.getString("gro_no"));
				glVO.setGrouplist_no(rs.getString("grouplist_no"));
				glVO.setJoin_time(rs.getTimestamp("join_time"));
				glVO.setMem_id(rs.getString("mem_id"));
				glVO.setMem_name(rs.getString("mem_name"));
				glVO.setRating_status(rs.getString("rating_status"));
				glVO.setRating_num(rs.getInt("rating_num"));
				glVO.setGro_rating_intro(rs.getString("gro_rating_intro"));
				
				list.add(glVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
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
	
	
}
