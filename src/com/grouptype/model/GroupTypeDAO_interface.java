package com.grouptype.model;

import java.util.*;

public interface GroupTypeDAO_interface {
	public void insert(GroupTypeVO grouptypeVO);
    public void update(GroupTypeVO grouptypeVO);
    public void delete(String type_no);
    public GroupTypeVO findByPrimaryKey(String type_no);
    public List<GroupTypeVO> getAll();
    public Set<GroupTypeVO> getEmpsByDeptno(String grouptypeVO);
}
