package com.grouplist.model;

import java.util.List;
import java.util.Set;

public interface GroupListDAO_interface {
	public void insert(GroupListVO grouplistVO);
    public void update(GroupListVO grouplistVO);
    public void scoreIntroUpdate(String gro_rating_intro, String group_no, String mem_id, Integer rating_num);
    public void delete(String grouplist_no);
    public List<GroupListVO> findMemberByGroup(String gro_no);
    public GroupListVO findByPrimaryKey(String grouplist_no);
    public List<GroupListVO> getAll();
    public Set<GroupListVO> getEmpsByDeptno(String grouplist_no);
    public List<GroupListVO> findJoinGroupByMember(String mem_id);
    public void exitGroup(String gro_no, String mem_id);
    public GroupListVO getOneGroupList(String gro_no, String mem_id);
}
