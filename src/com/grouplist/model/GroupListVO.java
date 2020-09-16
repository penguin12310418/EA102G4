package com.grouplist.model;

public class GroupListVO implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String grouplist_no;
	private String gro_no;
	private String gro_name;
	private String mem_id;
	private String mem_name;
	private Integer rating_num;
	private String rating_status;
	private String gro_rating_intro;
	private java.sql.Timestamp join_time;
	
	
	
	public Integer getRating_num() {
		return rating_num;
	}

	public void setRating_num(Integer rating_num) {
		this.rating_num = rating_num;
	}

	public String getGro_rating_intro() {
		return gro_rating_intro;
	}

	public void setGro_rating_intro(String gro_rating_intro) {
		this.gro_rating_intro = gro_rating_intro;
	}

	public String getGrouplist_no() {
		return grouplist_no;
	}
	
	public void setGrouplist_no(String grouplist_no) {
		this.grouplist_no = grouplist_no;
	}

	public String getRating_status() {
		return rating_status;
	}

	public void setRating_status(String rating_status) {
		this.rating_status = rating_status;
	}

	public java.sql.Timestamp getJoin_time() {
		return join_time;
	}

	public void setJoin_time(java.sql.Timestamp join_time) {
		this.join_time = join_time;
	}

	public String getGro_name() {
		return gro_name;
	}

	public void setGro_name(String gro_name) {
		this.gro_name = gro_name;
	}

	public String getMem_name() {
		return mem_name;
	}

	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}

	public GroupListVO() {}

	public String getGro_no() {
		return gro_no;
	}

	public void setGro_no(String gro_no) {
		this.gro_no = gro_no;
	}

	public String getMem_id() {
		return mem_id;
	}

	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}

	@Override
	public String toString() {
		return "GroupListVO [grouplist_no=" + grouplist_no + ", gro_no=" + gro_no + ", gro_name=" + gro_name
				+ ", mem_id=" + mem_id + ", mem_name=" + mem_name + ", rating_status=" + rating_status
				+ ", gro_ratring_intro=" + gro_rating_intro + ", join_time=" + join_time + "]";
	}
}
