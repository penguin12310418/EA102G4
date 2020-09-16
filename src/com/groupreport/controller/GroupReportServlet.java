package com.groupreport.controller;

import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.group.model.*;
import com.grouplist.model.*;
import com.groupmessage.model.GroupMessageService;
import com.groupmessage.model.GroupMessageVO;
import com.groupreport.model.*;
import com.grouptype.model.GroupTypeService;
import com.mem.model.*;

@MultipartConfig()
public class GroupReportServlet extends HttpServlet implements Serializable{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		res.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();

//		=============================UpdateReport==================================		
		
		if ("update".equals(action)) {
			System.out.println("ServletController-updateReport in");
			
			GroupReportService groReSvc = new GroupReportService();
			GroupService groSvc = new GroupService();
			
			String rep_no = req.getParameter("rep_no");
			String rep_status = req.getParameter("rep_status");
			String gro_no = req.getParameter("gro_no");
			
			if (rep_status.equals("R1")) {
			GroupVO groVO = groSvc.getOneGroup(gro_no);
			groVO.setGro_status("G1");
			
			groSvc.updateGroup(groVO.getGro_no(), 
							   groVO.getMem_id(), 
							   groVO.getType_no(), 
							   groVO.getGro_name(), 
							   groVO.getGro_intro(), 
							   groVO.getGro_start(), 
							   groVO.getGro_end(), 
							   groVO.getGro_addr(), 
							   groVO.getGro_min(), 
							   groVO.getGro_max(), 
							   groVO.getGro_pic(),
							   groVO.getGro_status(),
							   groVO.getGro_lat(),
							   groVO.getGro_lng());
							   
			}
			
			
			groReSvc.update(rep_no, rep_status);
			
			String url = "/back-end/report/listAllReport.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
//		===============================GetOne for update=====================================

		if ("getOne_For_Update".equals(action)) {
			System.out.println("ServletController-getOneForUpdate in");

			String mem_id = new String(req.getParameter("mem_id"));
			String gro_no = new String(req.getParameter("gro_no"));

			GroupReportService groReSvc = new GroupReportService();
			GroupReportVO groupreportVO = groReSvc.getReportByMemGroNo(mem_id, gro_no);

			req.setAttribute("groupreportVO", groupreportVO);
			String url = "/back-end/report/update_groupreport_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;// µ{¦¡¤¤Â_
		}

	}
}
