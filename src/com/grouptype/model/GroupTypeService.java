package com.grouptype.model;

import java.util.List;
import com.grouptype.model.GroupTypeVO;

public class GroupTypeService {
	private GroupTypeDAO_interface dao;
	
	public GroupTypeService() {
		dao = new GroupTypeJDBCDAO();
	}
	
	public List<GroupTypeVO> getAll() {
		return dao.getAll();
	}
	
	public GroupTypeVO getTypeName(String type_no) {
		return dao.findByPrimaryKey(type_no);
	}
	
	
}
