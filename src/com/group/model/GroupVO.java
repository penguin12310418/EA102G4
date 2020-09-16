package com.group.model;

import java.util.Arrays;

public class GroupVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String gro_no;
	private String mem_id;
	private String type_no;
	private String gro_name;
	private String gro_intro;
	private java.sql.Timestamp gro_start;
	private java.sql.Timestamp gro_end;
	private String gro_addr;
	private Integer gro_min;
	private Integer gro_max;
	private byte[] gro_pic;
	private java.sql.Timestamp gro_time;
	private Integer gro_mem;
	private Integer gro_rating;
	private String gro_status;
	private Double gro_lat;
	private Double gro_lng;

	public GroupVO() {
	}

	public Double getGro_lat() {
		return gro_lat;
	}

	public void setGro_lat(Double gro_lat) {
		this.gro_lat = gro_lat;
	}

	public Double getGro_lng() {
		return gro_lng;
	}

	public void setGro_lng(Double gro_lng) {
		this.gro_lng = gro_lng;
	}

	public String getGro_status() {
		return gro_status;
	}

	public void setGro_status(String gro_status) {
		this.gro_status = gro_status;
	}

	public String getGro_no() {
		return gro_no;
	}

	public Integer getGro_min() {
		return gro_min;
	}

	public void setGro_min(Integer gro_min) {
		this.gro_min = gro_min;
	}

	public Integer getGro_mem() {
		return gro_mem;
	}

	public void setGro_mem(Integer gro_mem) {
		this.gro_mem = gro_mem;
	}

	public Integer getGro_rating() {
		return gro_rating;
	}

	public void setGro_rating(Integer gro_rating) {
		this.gro_rating = gro_rating;
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

	public String getType_no() {
		return type_no;
	}

	public void setType_no(String type_no) {
		this.type_no = type_no;
	}

	public String getGro_name() {
		return gro_name;
	}

	public void setGro_name(String gro_name) {
		this.gro_name = gro_name;
	}

	public String getGro_intro() {
		return gro_intro;
	}

	public void setGro_intro(String gro_intro) {
		this.gro_intro = gro_intro;
	}

	public java.sql.Timestamp getGro_start() {
		return gro_start;
	}

	public void setGro_start(java.sql.Timestamp gro_start) {
		this.gro_start = gro_start;
	}

	public java.sql.Timestamp getGro_end() {
		return gro_end;
	}

	public void setGro_end(java.sql.Timestamp gro_end) {
		this.gro_end = gro_end;
	}

	public String getGro_addr() {
		return gro_addr;
	}

	public void setGro_addr(String gro_addr) {
		this.gro_addr = gro_addr;
	}

	public Integer getGro_max() {
		return gro_max;
	}

	public void setGro_max(Integer gro_max) {
		this.gro_max = gro_max;
	}

	public byte[] getGro_pic() {
		return gro_pic;
	}

	public void setGro_pic(byte[] gro_pic) {
		this.gro_pic = gro_pic;
	}

	public java.sql.Timestamp getGro_time() {
		return gro_time;
	}

	public void setGro_time(java.sql.Timestamp gro_time) {
		this.gro_time = gro_time;
	}

	@Override
	public String toString() {
		return "GroupVO [gro_no=" + gro_no + ", mem_id=" + mem_id + ", type_no=" + type_no + ", gro_name=" + gro_name
				+ ", gro_intro=" + gro_intro + ", gro_start=" + gro_start + ", gro_end=" + gro_end + ", gro_addr="
				+ gro_addr + ", gro_min=" + gro_min + ", gro_max=" + gro_max 
				+ ", gro_time=" + gro_time + ", gro_mem=" + gro_mem + ", gro_rating=" + gro_rating + ", gro_status="
				+ gro_status + ", gro_lat=" + gro_lat + ", gro_lng=" + gro_lng + "]";
	}

	
}
