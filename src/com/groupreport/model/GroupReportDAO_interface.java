package com.groupreport.model;

import java.util.*;

public interface GroupReportDAO_interface {
	public void insert(GroupReportVO groupreportVO);
    public void update(GroupReportVO groupreportVO);
    public void delete(String rep_no);
    public GroupReportVO findByMemGroNo(String mem_id, String gro_no);
    public GroupReportVO findByPrimaryKey(String rep_no);
    public List<GroupReportVO> getAll(String mem_id);
    public List<GroupReportVO> all();
    public Set<GroupReportVO> getEmpsByDeptno(String rep_no);
}
