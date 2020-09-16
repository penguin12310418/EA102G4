package com.group.controller;

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

@WebServlet("/GroupServlet")
@MultipartConfig()
public class GroupServlet extends HttpServlet implements Serializable{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		res.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		
//		=============================addMessageRes==================================		
		
		if ("addMessageRes".equals(action)) {
			System.out.println("ServletController-addMessageRes in");
			GroupMessageService gmSvc = new GroupMessageService();
			
			String mes_res = req.getParameter("mes_res");
			String gro_no = req.getParameter("gro_no");
			String mem_id = req.getParameter("mem_id");
			String mes_text = req.getParameter("mes_text");
			System.out.println("mes_res = "+mes_res);
			System.out.println("gro_no = "+gro_no);
			System.out.println("mem_id = "+mem_id);
			
			gmSvc.answerMessage(mes_res, gro_no, mem_id, mes_text);
		}
		
//		=============================addMessage==================================		
		
		if ("addMessage".equals(action)) {
			System.out.println("ServletController-addMessage in");
			GroupMessageService gmSvc = new GroupMessageService();
			
			String gro_no = req.getParameter("gro_no");
			String mem_id = req.getParameter("mem_id");
			String mes_text = req.getParameter("mes_text");
			
			gmSvc.addMes(gro_no, mem_id, mes_text);
			
		}
		
//		=============================UpdateScore==================================		
		
		if ("updateScore".equals(action)) {
			System.out.println("ServletController-updateScore in");
			
			GroupService groSvc = new GroupService();
			GroupListService glSvc = new GroupListService();
			
			String gro_no = req.getParameter("gro_no");
			
			Integer databaseRating = groSvc.getOneGroup(gro_no).getGro_rating();
			Integer databaseMem = groSvc.getOneGroup(gro_no).getGro_mem();
			
			String gro_rating_intro = req.getParameter("gro_rating_intro");
			String mem_id = req.getParameter("mem_id");
			Integer gro_rating =Integer.parseInt(req.getParameter("gro_rating"))+databaseRating;
			Integer gro_mem = databaseMem+1;
			Integer rating_num = Integer.parseInt(req.getParameter("gro_rating"));
			
			groSvc.setGroupRatingAndMem(gro_no, gro_rating, gro_mem);
			glSvc.scoreIntroUpdate(gro_rating_intro, gro_no, mem_id, rating_num);
		}
		
//		==============================MEMBER EXIT GROUP=======================================

		if ("memberExitGroup".equals(action)) {
			System.out.println("ServletController-delete in");

			String gro_no = req.getParameter("gro_no");
			String mem_id = req.getParameter("mem_id");

			GroupListService glSvc = new GroupListService();
			glSvc.memberExitGroup(gro_no, mem_id);
			
			List<GroupVO> thisMemberGroup = new ArrayList<GroupVO>();
			List<GroupListVO> getJoinGroupByMember = new GroupListService().getJoinGroupByMember(mem_id);
			
			for(int i = 0;i<getJoinGroupByMember.size();i++) {
				GroupVO getBaseAllGroup = new GroupService().getGroupByGroNo(getJoinGroupByMember.get(i).getGro_no());
				thisMemberGroup.add(getBaseAllGroup);
			}
			session.setAttribute("thisMemberGroup", thisMemberGroup);

			String url = "/front-end/gpt/selectGroupByMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;// 程式中斷
		}
		
//		================================VIEW MY GROUP=====================================		

		if ("viewMyGroup".equals(action)) {
			System.out.println("ServletController-viewMyGroup in");
			String mem_id = ((MemVO)session.getAttribute("memVOLogin")).getMem_id();
			
			List<GroupVO> thisMemberGroup = new ArrayList<GroupVO>();
			List<GroupListVO> getJoinGroupByMember = new GroupListService().getJoinGroupByMember(mem_id);
			
			for(int i = 0;i<getJoinGroupByMember.size();i++) {
				GroupVO getBaseAllGroup = new GroupService().getGroupByGroNo(getJoinGroupByMember.get(i).getGro_no());
				thisMemberGroup.add(getBaseAllGroup);
			}
			session.setAttribute("thisMemberGroup", thisMemberGroup);
			
			String url = "/front-end/gpt/selectGroupByMember.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;// 程式中斷
		}
		
//		=============================getAllGroupTypeJSON==================================		
		
		if ("getAllGroupTypeJSON".equals(action)) {
			PrintWriter out = res.getWriter();
			GroupTypeService groupTypeSvc = new GroupTypeService();
			
			String jsonArray = (new JSONArray(groupTypeSvc.getAll())).toString();
			
			out.print(jsonArray);
		}
		
//		===============================VIEW MY GROUP MEMBER====================================		

		if ("view_My_Member".equals(action)) {
			System.out.println("ServletController-view_My_Member in");
			GroupListService glSvc = new GroupListService();
			JSONArray array = new JSONArray();
			String gro_no = req.getParameter("gro_no");
			System.out.println(gro_no);

			List<GroupListVO> list = glSvc.findGroupMember(gro_no);
			for(GroupListVO glVO : list) {
				JSONObject obj = new JSONObject();
				obj.put("gro_name", glVO.getGro_name());
				obj.put("gro_no", glVO.getGro_no());
				obj.put("grouplist_no", glVO.getGrouplist_no());
				obj.put("join_time", glVO.getJoin_time());
				obj.put("mem_id", glVO.getMem_id());
				obj.put("mem_name", glVO.getMem_name());
				obj.put("rating_status", glVO.getRating_status());
				array.put(obj);
			}
			
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
			return;// 程式中斷
		}

//		=============================KICK MEMBER OUT======================================		

		if ("kickOut".equals(action)) {
			System.out.println("ServletController-kickOut in");
			String grouplist_no = req.getParameter("grouplist_no");
			GroupListService glSvc = new GroupListService();
			
			glSvc.deleteGroupListNo(grouplist_no);
		}
		
//		==============================JOIN GROUP======================================		

		if ("joinGroup".equals(action)) {
			System.out.println("ServletController-joinGroup in");
			GroupListService glSvc = new GroupListService();

			String gro_no = req.getParameter("gro_no");
			String gro_name = req.getParameter("gro_name");
			String mem_id = req.getParameter("mem_id");
			String mem_name = req.getParameter("mem_name");

			glSvc.insert(gro_no, mem_id, gro_name, mem_name);
			return;// 程式中斷
		}

//		=============================CREATE REPORT FOR GROUP=======================================		

		if ("reportAdd".equals(action)) {
			System.out.println("ServletController-reportAdd in");
			
			String gro_no = req.getParameter("gro_no");
			String mem_id = req.getParameter("mem_id");
			String rep_reason = req.getParameter("rep_reason");

			GroupReportService grSvc = new GroupReportService();
			grSvc.add(gro_no, mem_id, rep_reason);

			return;// 程式中斷
		}

//		==============================???????????????????==================================		

		if ("reportGroup".equals(action)) {
			System.out.println("ServletController-reportGroup in");

			String gro_no = req.getParameter("gro_no");
			String gro_name = req.getParameter("gro_name");

			req.setAttribute("gro_no", gro_no);
			req.setAttribute("gro_name", gro_name);

			String url = "/front-end/gpt/groupReportPage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;// 程式中斷
		}

//		==================================CREATE NEW GROUP=======================================		

		if ("ADD".equals(action)) {
			System.out.println("ServletController-add in");
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				byte[] gro_pic = null;

				Part part = req.getPart("gro_pic");
				java.io.InputStream in = part.getInputStream();
				byte[] buf = new byte[in.available()];
				in.read(buf);

				gro_pic = buf;
				String mem_id = ((MemVO) session.getAttribute("memVOLogin")).getMem_id();
				String type_no = req.getParameter("type_no");

				String gro_name = req.getParameter("gro_name");
				if (gro_name == null || gro_name.trim().length() == 0) {
					errorMsgs.add("活動名稱請勿空白");
				}

				String gro_intro = req.getParameter("gro_intro");
				if (gro_intro == null || gro_intro.trim().length() == 0) {
					errorMsgs.add("活動簡介請勿空白");
				}
				
				java.sql.Timestamp gro_start = null;
				try {
					SimpleDateFormat startformat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
					java.util.Date startTime = null;
					startTime = startformat.parse(req.getParameter("gro_start"));
					gro_start = new java.sql.Timestamp(startTime.getTime());
					System.out.println("gro_start = "+gro_start);
				} catch (IllegalArgumentException e) {
					gro_start = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入開始日期.");
				}

				java.sql.Timestamp gro_end = null;
				try {
					SimpleDateFormat endformat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
					java.util.Date endTime = null;
					endTime = endformat.parse(req.getParameter("gro_end"));
					gro_end = new java.sql.Timestamp(endTime.getTime());
					System.out.println("gro_end = "+gro_end);
				} catch (IllegalArgumentException e) {
					gro_end = new java.sql.Timestamp(System.currentTimeMillis());
					errorMsgs.add("請輸入結束日期.");
				}

				String gro_addr = req.getParameter("gro_addr");
				if (gro_addr == null || gro_addr.trim().length() == 0) {
					errorMsgs.add("活動地址請勿空白");
				}

				Integer gro_min = null;
				try {
					gro_min = Integer.parseInt(req.getParameter("gro_min"));
				} catch (NumberFormatException e) {
					gro_min = 0;
					errorMsgs.add("請輸入最小人數.");
				}

				Integer gro_max = null;
				try {
					gro_max = Integer.parseInt(req.getParameter("gro_max"));
				} catch (NumberFormatException e) {
					gro_max = 0;
					errorMsgs.add("請輸入最大人數.");
				}

				GroupVO groupVO = new GroupVO();
				groupVO.setMem_id(mem_id);
				groupVO.setType_no(type_no);
				groupVO.setGro_name(gro_name);
				groupVO.setGro_intro(gro_intro);
				groupVO.setGro_start(gro_start);
				groupVO.setGro_end(gro_end);
				groupVO.setGro_min(gro_min);
				groupVO.setGro_max(gro_max);
				groupVO.setGro_addr(gro_addr);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("groupVO", groupVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gpt/groupAdd.jsp");
					failureView.forward(req, res);
					return;
				}
				
				Double gro_lat = Double.parseDouble(req.getParameter("gro_lat"));
				Double gro_lng = Double.parseDouble(req.getParameter("gro_lng"));


				/*************************** 2.開始新增資料 ***************************************/
				GroupService groupSvc = new GroupService();
				groupVO = groupSvc.add(mem_id, type_no, gro_name, gro_intro, gro_start, gro_end, gro_addr, gro_min,
						gro_max, gro_pic, gro_lat, gro_lng);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				
				List<GroupVO> thisMemberGroup = new ArrayList<GroupVO>();
				List<GroupListVO> getJoinGroupByMember = new GroupListService().getJoinGroupByMember(mem_id);
				
				for(int i = 0;i<getJoinGroupByMember.size();i++) {
					GroupVO getBaseAllGroup = new GroupService().getGroupByGroNo(getJoinGroupByMember.get(i).getGro_no());
					thisMemberGroup.add(getBaseAllGroup);
				}
				session.setAttribute("thisMemberGroup", thisMemberGroup);
				
				String url = "/EA102G4/front-end/gpt/selectGroupByMember.jsp";
				res.sendRedirect(url);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gpt/groupAdd.jsp");
				failureView.forward(req, res);
			}
		}

//		===============================DELETE(change Group status)=======================================

		if ("delete".equals(action)) {
			System.out.println("ServletController-delete in");
			
			String mem_id = ((MemVO) session.getAttribute("memVOLogin")).getMem_id();
			String gro_no = req.getParameter("gro_no");

			GroupService groupSvc = new GroupService();
			groupSvc.deleteOneGroup(gro_no);
			
			
			List<GroupVO> thisMemberGroup = new ArrayList<GroupVO>();
			List<GroupListVO> getJoinGroupByMember = new GroupListService().getJoinGroupByMember(mem_id);
			
			for(int i = 0;i<getJoinGroupByMember.size();i++) {
				GroupVO getBaseAllGroup = new GroupService().getGroupByGroNo(getJoinGroupByMember.get(i).getGro_no());
				thisMemberGroup.add(getBaseAllGroup);
			}
			session.setAttribute("thisMemberGroup", thisMemberGroup);
			
			String url = "/EA102G4/front-end/gpt/selectGroupByMember.jsp";
			res.sendRedirect(url);
			return;// 程式中斷
		}

//		===============================VIEW UPDATE PAGE=====================================

		if ("getOne_For_Update".equals(action)) {
			System.out.println("ServletController-getOneForUpdate in");

			String gro_no = new String(req.getParameter("gro_no"));

			GroupService groupSvc = new GroupService();
			GroupVO groupVO = groupSvc.getOneGroup(gro_no);

			req.setAttribute("groupVO", groupVO);
			String url = "/front-end/gpt/groupUpdate.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;// 程式中斷
		}

//		=====================================UPDATE======================================

		if ("update".equals(action)) {
			System.out.println("ServletController-Update in");

			byte[] gro_pic = null;

			Part part = req.getPart("gro_pic");
			java.io.InputStream in = part.getInputStream();
			if(in.available()==0) {
				gro_pic = ((GroupVO)session.getAttribute("groupVO")).getGro_pic();
			}else {
				byte[] buf = new byte[in.available()];
				in.read(buf);
				gro_pic = buf;
			}

			String gro_no = req.getParameter("gro_no");
			String mem_id = req.getParameter("mem_id");
			String type_no = req.getParameter("type_no");
			String gro_name = req.getParameter("gro_name");
			String gro_intro = req.getParameter("gro_intro");
			String gro_status = req.getParameter("gro_status");
			
			SimpleDateFormat startformat = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
			java.util.Date startTime = null;
			try {
				startTime = startformat.parse(req.getParameter("gro_start"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			java.sql.Timestamp gro_start = new java.sql.Timestamp(startTime.getTime());
			
			SimpleDateFormat endformat = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
			java.util.Date endTime = null;
			try {
				endTime = endformat.parse(req.getParameter("gro_end"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			java.sql.Timestamp gro_end = new java.sql.Timestamp(endTime.getTime());
			
			String gro_addr = req.getParameter("gro_addr");
			Integer gro_min = Integer.parseInt(req.getParameter("gro_min"));
			Integer gro_max = Integer.parseInt(req.getParameter("gro_max"));
			Double gro_lat = Double.parseDouble(req.getParameter("gro_lat"));
			Double gro_lng = Double.parseDouble(req.getParameter("gro_lng"));

			GroupService groupSvc = new GroupService();
			groupSvc.updateGroup(gro_no, mem_id, type_no, gro_name, gro_intro, gro_start, gro_end, gro_addr, gro_min,
					gro_max, gro_pic, gro_status, gro_lat, gro_lng);
			
//------------------------------------------------------------------------------------------------------//
			
			List<GroupVO> thisMemberGroup = new ArrayList<GroupVO>();
			List<GroupListVO> getJoinGroupByMember = new GroupListService().getJoinGroupByMember(mem_id);
			
			for(int i = 0;i<getJoinGroupByMember.size();i++) {
				GroupVO getBaseAllGroup = new GroupService().getGroupByGroNo(getJoinGroupByMember.get(i).getGro_no());
				thisMemberGroup.add(getBaseAllGroup);
			}
			session.setAttribute("thisMemberGroup", thisMemberGroup);
			
			String url = "/EA102G4/front-end/gpt/selectGroupByMember.jsp";
			res.sendRedirect(url);
			return;// 程式中斷
		}
		
//		==================================SEARCH BY WORD=============================================

		if ("getWord_For_Search".equals(action)) {
			System.out.println("ServletController-getWordForSearch in");

			String wordScope = req.getParameter("search");
			System.out.println("word = "+wordScope);
			session.setAttribute("wordScope", wordScope);

			String url = "/front-end/gpt/selectGroupWord.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;// 程式中斷
		}

//		==================================SEARCH BY CITY=============================================

		if ("getCity_For_Search".equals(action)) {
			System.out.println("ServletController-getCityForSearch in");

			String cityScope = req.getParameter("cityScope");
			System.out.println("cityScope = "+cityScope);
			session.setAttribute("cityScope", cityScope);

			String url = "/front-end/gpt/selectGroupCity.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;// 程式中斷
		}

//		====================================SEARCH BY TYPE============================================

		if ("getType_For_Search".equals(action)) {
			System.out.println("ServletController-getTypeForSearch in");

			String typeScope = req.getParameter("typeScope");
			session.setAttribute("typeScope", typeScope);

			String url = "/front-end/gpt/selectGroupType.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;// 程式中斷
		}
	}

	public String fileNameGet(Part part) {
		String getPart = part.getHeader("content-disposition");
		String filename = null;

		filename = new File(getPart.substring(getPart.lastIndexOf("=") + 2, getPart.lastIndexOf("\""))).getName();

		return filename;
	}

}
