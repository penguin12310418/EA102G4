package com.groupreport.model;

public class GroupReportVO {
	private String rep_no;
	private String mem_id;
	private String gro_no;
	private String rep_reason;
	private java.sql.Timestamp rep_time;
	private String rep_status;

	public GroupReportVO() {}


	public String getRep_no() {
		return rep_no;
	}

	public void setRep_no(String rep_no) {
		this.rep_no = rep_no;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	public String getGro_no() {
		return gro_no;
	}

	public void setGro_no(String gro_no) {
		this.gro_no = gro_no;
	}

	public String getRep_reason() {
		return rep_reason;
	}

	public void setRep_reason(String rep_reason) {
		this.rep_reason = rep_reason;
	}

	public java.sql.Timestamp getRep_time() {
		return rep_time;
	}

	public void setRep_time(java.sql.Timestamp rep_time) {
		this.rep_time = rep_time;
	}

	public String getRep_status() {
		return rep_status;
	}

	public void setRep_status(String rep_status) {
		this.rep_status = rep_status;
	}
}
