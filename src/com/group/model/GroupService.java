package com.group.model;

import java.util.*;

import com.grouplist.model.GroupListVO;


public class GroupService {

	private GroupDAO_interface dao;

	public GroupService() {
		dao = new GroupJDBCDAO();
	}

	public GroupVO add(String mem_id, String type_no, String gro_name, String gro_intro, java.sql.Timestamp gro_start,
			java.sql.Timestamp gro_end, String gro_addr, Integer gro_min, Integer gro_max, byte[] gro_pic,Double gro_lat,Double gro_lng) {

		GroupVO groupVO = new GroupVO();
		groupVO.setMem_id(mem_id);
		groupVO.setType_no(type_no);
		groupVO.setGro_name(gro_name);
		groupVO.setGro_intro(gro_intro);
		groupVO.setGro_start(gro_start);
		groupVO.setGro_end(gro_end);
		groupVO.setGro_addr(gro_addr);
		groupVO.setGro_min(gro_min);
		groupVO.setGro_max(gro_max);
		groupVO.setGro_pic(gro_pic);
		groupVO.setGro_lat(gro_lat);
		groupVO.setGro_lng(gro_lng);
		
		dao.insert(groupVO);

		return groupVO;
	}

	public List<GroupVO> getAll() { //  focus gro_status = G0
		return dao.getAll();
	}
	
	public List<GroupVO> getAll_G2() { //  focus gro_status = G0
		return dao.getAll_G2();
	}

	public void deleteOneGroup(String gro_no) {
		dao.delete(gro_no);
	}

	public GroupVO updateGroup(String gro_no, String mem_id, String type_no, String gro_name, String gro_intro,
			java.sql.Timestamp gro_start, java.sql.Timestamp gro_end, String gro_addr, Integer gro_min, Integer gro_max,
			byte[] gro_pic, String gro_status, Double gro_lat, Double gro_lng) {
		GroupVO groupVO = new GroupVO();
		groupVO.setGro_no(gro_no);
		groupVO.setMem_id(mem_id);
		groupVO.setType_no(type_no);
		groupVO.setGro_name(gro_name);
		groupVO.setGro_intro(gro_intro);
		groupVO.setGro_start(gro_start);
		groupVO.setGro_end(gro_end);
		groupVO.setGro_addr(gro_addr);
		groupVO.setGro_min(gro_min);
		groupVO.setGro_max(gro_max);
		groupVO.setGro_pic(gro_pic);
		groupVO.setGro_status(gro_status);
		groupVO.setGro_lat(gro_lat);
		groupVO.setGro_lng(gro_lng);

		dao.update(groupVO);
		
		
		return groupVO;
	}

	public GroupVO getOneGroup(String gro_no) {
		return dao.findByPrimaryKey(gro_no);
	}

	public List<GroupVO> getGroupByCity(String cityScope) {
		return dao.findByCity(cityScope);
	}

	public List<GroupVO> getGroupByType(String typeScope) {
		return dao.findByType(typeScope);
	}
	
	public List<GroupVO> getGroupByMember(String mem_id) {
		return dao.findGroupByMember(mem_id);
	}
	
	public GroupVO getGroupByGroNo(String gro_no) { // select all group without gro_status
		return dao.getGroupByGroNo(gro_no);
	}
	
	public void setGroupRatingAndMem(String gro_no, Integer gro_rating, Integer gro_mem) {
		dao.scoreUpdate(gro_no, gro_rating, gro_mem);
	}
	
	public List<GroupVO> getGroupByName(String word) {
		return dao.findByGroupName(word);
	}
}
