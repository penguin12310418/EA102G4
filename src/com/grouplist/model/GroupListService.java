package com.grouplist.model;

import java.util.List;

public class GroupListService {
	GroupListDAO_interface dao;
	
	public GroupListService() {
		dao = new GroupListJDBCDAO();
	}
	
	public void insert(String gro_no,String mem_id, String gro_name, String mem_name) {
		GroupListVO glVO = new GroupListVO();
		glVO.setGro_no(gro_no);
		glVO.setGro_name(gro_name);
		glVO.setMem_id(mem_id);
		glVO.setMem_name(mem_name);
		
		dao.insert(glVO);
	}
	
	public List<GroupListVO> findGroupMember(String gro_no){
		return dao.findMemberByGroup(gro_no);
	}
	
	public void deleteGroupListNo(String grouplist_no) {
		dao.delete(grouplist_no);
	}
	
	public void memberExitGroup(String gro_no, String mem_id) {
		dao.exitGroup(gro_no, mem_id);
	}
	
	public List<GroupListVO> getJoinGroupByMember(String mem_id) { // SELECT GRO_NO FROM GROUPLIST WHERE MEM_ID
		return dao.findJoinGroupByMember(mem_id);
	}
	
	public void scoreIntroUpdate(String gro_rating_intro, String gro_no, String mem_id, Integer rating_num) {
		dao.scoreIntroUpdate(gro_rating_intro, gro_no, mem_id, rating_num);
	}
	
	public GroupListVO getOneGroupListDetail(String gro_no, String mem_id) {
		return dao.getOneGroupList(gro_no, mem_id);
	}
}
