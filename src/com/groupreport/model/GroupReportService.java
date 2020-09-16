package com.groupreport.model;

import java.util.List;

public class GroupReportService {
	private GroupReportDAO_interface dao;
	
	public GroupReportService() {
		dao = new GroupReportJDBCDAO();
	}
	
	public void add(String gro_no,String mem_id,String rep_reason) {
		GroupReportVO grVO = new GroupReportVO();
		grVO.setGro_no(gro_no);
		grVO.setMem_id(mem_id);
		grVO.setRep_reason(rep_reason);
		
		dao.insert(grVO);
	}
	
	public List<GroupReportVO> getAll(String mem_id) {
		return dao.getAll(mem_id);
	}
	
	public GroupReportVO getReportByMemGroNo(String mem_id, String gro_no) {
		return dao.findByMemGroNo(mem_id, gro_no);
	}
	
	public void update(String rep_no,String rep_status) {
		GroupReportVO grVO = new GroupReportVO();
		grVO.setRep_no(rep_no);
		grVO.setRep_status(rep_status);
		
		dao.update(grVO);
	}
	
	public List<GroupReportVO> All() {
		return dao.all();
	}
}
