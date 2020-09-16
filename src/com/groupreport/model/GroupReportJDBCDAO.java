package com.groupreport.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class GroupReportJDBCDAO implements GroupReportDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/INGYM");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT = "INSERT INTO GROUPREPORT(REP_NO, MEM_ID, GRO_NO, REP_REASON, REP_STATUS, REP_TIME) VALUES('GR'||LPAD(GROUPREPORT_SEQ.NEXTVAL,5,0), ?, ?, ?, 'R0', SYSDATE)";
	private static final String GETALL = "SELECT * FROM GROUPREPORT WHERE MEM_ID = ? ORDER BY REP_TIME DESC";
	private static final String ALL = "SELECT * FROM GROUPREPORT ORDER BY REP_TIME DESC";
	private static final String UPDATE = "UPDATE GROUPREPORT SET REP_STATUS=? WHERE REP_NO=?";
	private static final String GET_ONE_REPORT = "SELECT * FROM GROUPREPORT WHERE MEM_ID = ? AND GRO_NO = ?";
	
	@Override
	public void insert(GroupReportVO groupreportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);

			pstmt.setString(1, groupreportVO.getMem_id());
			pstmt.setString(2, groupreportVO.getGro_no());
			pstmt.setString(3, groupreportVO.getRep_reason());
			
			System.out.println("getMem_id = "+groupreportVO.getMem_id());
			System.out.println("getGro_no = "+groupreportVO.getGro_no());
			System.out.println("getRep_reason = "+groupreportVO.getRep_reason());
			
			pstmt.executeUpdate();

			System.out.println("檢舉新增成功");

		} catch (SQLException e) {
			e.printStackTrace();
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
	public void update(GroupReportVO groupreportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, groupreportVO.getRep_status());
			pstmt.setString(2, groupreportVO.getRep_no());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(String groupreportVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GroupReportVO findByPrimaryKey(String groupreportVO) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public GroupReportVO findByMemGroNo(String mem_id, String gro_no) {
		GroupReportVO grVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_REPORT);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, gro_no);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				grVO = new GroupReportVO();
				grVO.setGro_no(rs.getString("gro_no"));
				grVO.setRep_time(rs.getTimestamp("rep_time"));
				grVO.setRep_reason(rs.getString("rep_reason"));
			}

			System.out.println("查詢"+mem_id+"會員檢舉成功");

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
		return grVO;
	}
	
	
	@Override
	public List<GroupReportVO> getAll(String mem_id) {
		List<GroupReportVO> list = new ArrayList<GroupReportVO>();
		GroupReportVO grVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			pstmt.setString(1, mem_id);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				grVO = new GroupReportVO();
				grVO.setGro_no(rs.getString("gro_no"));
				grVO.setRep_time(rs.getTimestamp("rep_time"));
				grVO.setRep_reason(rs.getString("rep_reason"));
				

				list.add(grVO);
			}

			System.out.println("查詢會員檢舉成功");

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
	public List<GroupReportVO> all() {
		List<GroupReportVO> list = new ArrayList<GroupReportVO>();
		GroupReportVO grVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ALL);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				grVO = new GroupReportVO();
				grVO.setRep_no(rs.getString("rep_no"));
				grVO.setMem_id(rs.getString("mem_id"));
				grVO.setGro_no(rs.getString("gro_no"));
				grVO.setRep_reason(rs.getString("rep_reason"));
				grVO.setRep_time(rs.getTimestamp("rep_time"));
				grVO.setRep_status(rs.getString("rep_status"));

				list.add(grVO);
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
	public Set<GroupReportVO> getEmpsByDeptno(String groupreportVO) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
