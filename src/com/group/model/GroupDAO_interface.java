package com.group.model;

import java.util.List;
import java.util.Set;
import com.grouplist.model.GroupListVO;

public interface GroupDAO_interface {
	public void insert(GroupVO groupVO);
    public void update(GroupVO groupVO);
    public void delete(String gro_no);
    public List<GroupVO> findByCity(String cityScope);
    public GroupVO findByPrimaryKey(String gro_no);
    public List<GroupVO> getAll();
    public Set<GroupVO> getEmpsByDeptno(String gro_no);
    public List<GroupVO> findByType(String typeScope);
    public List<GroupVO> findGroupByMember(String mem_id);
    public GroupVO getGroupByGroNo(String gro_no);
    public void scoreUpdate(String gro_no, Integer gro_rating, Integer gro_mem);
    public List<GroupVO> findByGroupName(String gro_name);
    public List<GroupVO> getAll_G2();
}
