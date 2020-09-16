<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.group.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.groupmessage.model.*"%>
<%@ page import="weatherTool.*"%>
<%@ page import="com.grouplist.model.*"%>
<%@ page import="com.groupreport.model.*"%>
<%@ page import="com.individualClass.model.*"%>
<%@ page import="java.util.stream.Collectors"%>

<!DOCTYPE html>
<html lang="en">
<head>
<%	
	String gro_no = request.getParameter("gro_no");
	GroupVO groupVO = new GroupService().getOneGroup(gro_no);
	pageContext.setAttribute("groupVO", groupVO);
	MemVO memVO = new MemService().getOneMem(groupVO.getMem_id());
	MemVO memVOLogin = (MemVO) session.getAttribute("memVOLogin");
	
	GroupMessageService gmSvc = new GroupMessageService();
	List<GroupMessageVO> listMessage = gmSvc.findMessageByGroNo(groupVO.getGro_no());
	pageContext.setAttribute("listMessage", listMessage);
	
	GroupListService glSvc = new GroupListService();
	pageContext.setAttribute("glSvc",glSvc);
	
	GroupReportService grSvc = new GroupReportService();
	pageContext.setAttribute("grSvc",grSvc);
	
%>
	
<%	
	String city = groupVO.getGro_addr().substring(0, 3);
	if(city.equals("台北市")){
		city = "臺北市";
	}
	WeatherCrawler wC = new WeatherCrawler();
	Map<String,List<WeatherVO>> wM = wC.Crawler();
	List<WeatherVO> listWeather = wM.get(city);
	pageContext.setAttribute("listWeather", listWeather);
	
	IndividualClassService IndividualClassSvc = new IndividualClassService();
	List<IndividualClassVO> listClass = IndividualClassSvc.getAll();
	Set<IndividualClassVO> set = new HashSet<IndividualClassVO>(listClass);
	pageContext.setAttribute("set", set);
%>

<%
	List<GroupListVO> groMember = glSvc.findGroupMember(gro_no);
	List memIdList;
	memIdList = groMember.stream()
					.map(e -> e.getMem_id())
					.collect(Collectors.toList());
	
	pageContext.setAttribute("memIdList", memIdList);
%>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>oneGroupDetail</title>

<%@ include file="include/css_link"%>
<script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBw1IB_wF8J22LKiqPxdO8wbfWgFIpoDTo&callback=initMap&libraries=&v=weekly" defer></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js" type="text/javascript"></script>

<!-- Include a polyfill for ES6 Promises (optional) for IE11 and Android browser -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/core-js/2.4.1/core.js"></script>

<style type="text/css">
#map {
	height: 500px;
	width: 100%;
	
	background: #CCC;
	display: inline-block;
	color: black;
}

#googleMapArea{
	border: 1px solid #D9D9D9;
}
/* Optional: Makes the sample page fill the window. */
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}

#right-panel select, #right-panel input {
	font-size: 15px;
}

#right-panel select {
	width: 100%;
}

#right-panel i {
	font-size: 12px;
	font-family: microsoft JhengHei;
}

#right-panel {
	line-height: 30px;
	padding-left: 10px;
	height: 500px;
	width: 100%;
	overflow: auto;
}

#right-panel::-webkit-scrollbar {
	width: 8px;
	background-color: transparent;
}

#right-panel::-webkit-scrollbar-thumb {
	border-radius: 10px;
	background-color: #ececec;
}

#right-panel::-webkit-scrollbar-track {
	border-radius: 10px;
	background-color: transparent;
}

#comment::-webkit-scrollbar {
	width: 8px;
	background-color: transparent;
}

#comment::-webkit-scrollbar-thumb {
	border-radius: 10px;
	background-color: #ececec;
}

#comment::-webkit-scrollbar-track {
	border-radius: 10px;
	background-color: transparent;
}

#hi_mem {
	font-family: Microsoft JhengHei;
}

@media screen and (max-width: 1400px) {
	#hi_mem {
		display: none;
	}
}

#logout {
	border-style: none;
	background: none;
	transition: all .3s;
	color: rgba(255, 255, 255, 0.5);
}

#logout:hover {
	text-decoration: none;
	outline: none;
	color: inherit;
}

.comments-area{
	margin-top: 0px;
}

#menu2{
	word-wrap:break-word; 
	word-break:break-all; 
	overflow: hidden;
}

img.avatar {
    max-width: inherit !important;
}

#menu1 p{
	font-size: 16px;
    font-family: microsoft JhengHei;
    font-weight: 800;
}

#menu1 img{
	height:100px;
	width:100px;
}

.jq-toast-single { 
    font-size: 20px; 
    font-family: microsoft JhengHei !important; 
    line-height: 25px; 
    width:400px;
} 
h2.jq-toast-heading {
	font-size: 30px; 
}

.blog-img{
	background-repeat: no-repeat;
	background-position: center;
	height: 366.125px;
	background-size: cover;
}

 .blog-breadcrumb-overlay {
    background: -webkit-linear-gradient(left,rgb(0 0 0 / 0.4),rgb(0 0 0 / 0.4)), url(img/1244995.jpg);
    background-position: 97px -340px;
    background-size: 90%;
}

.wrapper {
	margin-bottom: 0;
    padding-bottom: 0px;
    padding-top: 0px;
    float: right;
}

span.material-icons {
    position: relative;
    top: 6px;
}

.img-part{
	width:134px;
	height:122px;
	background-repeat: no-repeat;
	background-position: center;
	background-size: cover;
}

.modal-body {
    font-size: 19px;
    font-weight: bold;
    padding-top: 0px;
}

h5#exampleModalLabel {
    font-size: 33px;
    margin-bottom: 22px;
}

#reportIcon{
	font-size: 40px;
}

img.mr-3 {
    filter: drop-shadow(16px 16px 20px gray) invert(0%);

}

.media-body {
    position: relative;
    top: 23px;
}

.mr-3, .mx-3 {
    margin-right: 2rem!important;
}

.form-group {
    margin-bottom: 0;
}

.blog-details-area .blog-details h2{
	margin-top: 0;
}

.adp-step, .adp-text {
    width: 100%;
    font-size: 20px;
    font-family: microsoft JhengHei;
    font-weight: 900;
}

.adp-summary {
    font-size: 15px;
    font-weight: 600;
}

table.adp-directions {
	font-size: 15px;
    font-family: microsoft JhengHei;
    font-weight: 800;
    width: 821px;
}

.warnbox-content {
    font-weight: 900;
    font-family: microsoft JhengHei;
}

.adp-agencies {
    font-weight: 900;
}

td.adp-substep {
    font-weight: 900;
    font-size: 16px;
}

#right-panel img {
    width: 20px;
    height: 20px;
    position: relative;
    top: -2px;
}

.share-img-item .content-part h4{
	margin-top: 29px;
}

.share-img-item .content-part{
	padding-top: 0px;
}

.form-control.w-100{
	color:black;
	font-weight: 600;
}

#mesText{
    font-weight: 600;
}

.nav.nav-pills li {
    font-size: 20px;
    font-weight: 500;
}

span#magicbuttom {
    position: relative;
    top: -40px;
    right: -279px;
}

</style>

</head>
<body onload="connect(); connectChat();" onunload="disconnect(); disconnectChat();">

	<!-- preloader area start -->
	<div class="preloader" id="preloader">
		<div class="preloader-inner">
			<div class="spinner">
				<div class="dot1"></div>
				<div class="dot2"></div>
			</div>
		</div>
	</div>
	<!-- preloader area end -->

	<!-- search Popup -->
	<div class="body-overlay" id="body-overlay"></div>
	<div class="search-popup" id="search-popup">
		<form action="index.html" class="search-form">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Search.....">
			</div>
			<button type="submit" class="submit-btn">
				<i class="fa fa-search"></i>
			</button>
		</form>
	</div>
	<!-- //. search Popup -->

	<%@ include file="include/pageNavbar.file"%>

	<!-- breadcrumb area -->
	<div class="breadcrumb-style-1 blog-breadcrumb-overlay">
		<div class="breadcrumb-inner">
			<h1 class="page-title">Activity Details</h1>
			<ul class="page-list margin-bottom-4">
				<li><a href="<%=request.getContextPath()%>/front-end/gpt/listAllGroup.jsp">Activity List</a></li>
				<li style='display:${memVOLogin == null ?"none":""};'><a href="<%=request.getContextPath()%>/front-end/gpt/groupAdd.jsp">Create Activity</a></li>
			</ul>
		</div>
	</div>
	<!-- breadcrumb area end -->

	<!-- home blog start -->
	<div class="blog-details-area margin-top-100">
		<main id="main" class="site-main">
			<div class="container">
				<div class="row">
					<div class="col-lg-8">
						<article class="blog-details">
							<div class="blog-img" style="background-image: url(pictool.do?gro_no=<%=groupVO.getGro_no()%>);">
							</div>
							<div class="blog-details-content" style="padding-bottom: 0px; padding-top: 15px;">
								<h6>
									<a href="#"><span class="material-icons" style="margin-left: 0px;">book_online</span>&nbsp;&nbsp;<span class="groupStatus" style="margin-left: 0px; font-size: 18px; font-weight:600;"><fmt:formatDate value="${groupVO.gro_start}" pattern="yyyy/MM/dd HH:mm" />&nbsp;&nbsp;-&nbsp;&nbsp;<fmt:formatDate value="${groupVO.gro_end}" pattern="yyyy/MM/dd HH:mm" /></span></a> 
									<a href="#"><span class="material-icons" >groups</span>&nbsp;&nbsp;<span class="groupStatus" style="margin-left: 0px; font-size: 18px; font-weight:600;">${glSvc.findGroupMember(groupVO.gro_no).size()} / ${groupVO.gro_max}</span></a>
									<a href="#"><span class="material-icons" >place</span>&nbsp;&nbsp;<span class="groupStatus" style="margin-left: 0px; font-size: 18px; font-family:microsoft JhengHei; font-weight:bold;">${groupVO.gro_addr.substring(0,3)}</span></a>
									<a href="<%=request.getContextPath()%>/front-end/mem/memDetail.jsp?mem_id=${groupVO.mem_id}"><span class="material-icons" >account_circle</span>&nbsp;&nbsp;<span class="groupStatus" style="margin-left: 0px; font-size: 18px; font-family:microsoft JhengHei; font-weight:bold;"><%=memVO.getMem_name()%></span></a>
								</h6>
								<h1 style="margin-bottom: 10px;margin-top: 0px;display: inline-block;font-weight: 500;"><%=groupVO.getGro_name()%></h1>
								<pre style="text-align: justify; white-space:pre-wrap; font-size: 18px; line-height: 25px; font-family: microsoft JhengHei; background-color: #f9f9fa; border: 0px;"><%=groupVO.getGro_intro()%></pre>
							</div>
						</article>
						<div class="biography-area margin-top-60">
							<div class="biography-content">
								<ul class="nav nav-pills">
									<li><a data-toggle="pill" href="#home" class="active">Map</a></li>
									<li><a data-toggle="pill" href="#menu1">Weather</a></li>
									<li><a data-toggle="pill" href="#menu2">Review</a></li>
								</ul>
								<div class="tab-content">
									<div id="home" class="tab-pane fade in active show">
										<div class="tab-content-description">
											<div id="comments" class="comments-area comments-area-wrapper" style="margin-top: 0px;">
												<div id="floating-panel">
													<div class="bd-example tooltip-demo">
														<button type="button" class="btn btn-secondary" data-toggle="tooltip" data-placement="left" title="Tooltip on left"
															value="TRANSIT" id="Transit">Transit</button>
														<button type="button" class="btn btn-secondary" data-toggle="tooltip" data-placement="top" title="Tooltip on top"
															value="DRIVING" id="Driving">Driving</button>
														<button type="button" class="btn btn-secondary" data-toggle="tooltip" data-placement="right" title="Tooltip on right"
															value="WALKING" id="Walking">Walking</button>
														<button type="button" class="btn btn-secondary" data-toggle="tooltip" data-placement="bottom"
															title="Tooltip on bottom" value="BICYCLING" id="Bicycling">Bicycling</button>
													</div>
												</div>
												<div id="googleMapArea" style="margin-top: 10px;">
													<div id="map"></div>
													<div id="right-panel"></div>
												</div>
											</div>
										</div>
									</div>
									<div id="menu1" class="tab-pane fade">
										<div class="tab-content-description">
											<div class="wrapper">
											    <div class="toggle">
											      <input class="toggle-input" type="checkbox" />
											      <div class="toggle-bg"></div>
											      <div class="toggle-switch">
											        <div class="toggle-switch-figure"></div>
											        <div class="toggle-switch-figureAlt"></div>
											      </div>
											    </div>
											  </div>										
											<div class="day">
												<div class="media" style="margin-bottom: 10px;">
												  <img src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/${listWeather.get(0).getWeatherType() }.svg" class="mr-3" alt="..." >
												  <div class="media-body">
												    <h5 class="mt-0">&nbsp;&nbsp;${listWeather.get(0).getTime() }</h5>
												    <p style="margin-bottom: 0px;">&nbsp;&nbsp;${listWeather.get(0).getWeatherDescribe() }</p> 
												  </div>
												</div>
												<div class="media" style="margin-bottom: 10px;">
												  <img src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/${listWeather.get(2).getWeatherType() }.svg" class="mr-3" alt="...">
												  <div class="media-body">
												    <h5 class="mt-0">&nbsp;&nbsp;${listWeather.get(2).getTime() }</h5>
												    <p style="margin-bottom: 0px;">&nbsp;&nbsp;${listWeather.get(2).getWeatherDescribe() }</p> 
												  </div>
												</div>
												<div class="media" style="margin-bottom: 10px;">
												  <img src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/${listWeather.get(4).getWeatherType() }.svg" class="mr-3" alt="...">
												  <div class="media-body">
												    <h5 class="mt-0">&nbsp;&nbsp;${listWeather.get(4).getTime() }</h5>
												    <p style="margin-bottom: 0px;">&nbsp;&nbsp;${listWeather.get(4).getWeatherDescribe() }</p> 
												  </div>
												</div>
												<div class="media" style="margin-bottom: 10px;">
												  <img src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/${listWeather.get(6).getWeatherType() }.svg" class="mr-3" alt="...">
												  <div class="media-body">
												    <h5 class="mt-0">&nbsp;&nbsp;${listWeather.get(6).getTime() }</h5>
												    <p style="margin-bottom: 0px;">&nbsp;&nbsp;${listWeather.get(6).getWeatherDescribe() }</p> 
												  </div>
												</div>
												<div class="media" style="margin-bottom: 10px;">
												  <img src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/${listWeather.get(8).getWeatherType() }.svg" class="mr-3" alt="...">
												  <div class="media-body">
												    <h5 class="mt-0">&nbsp;&nbsp;${listWeather.get(8).getTime() }</h5>
												    <p style="margin-bottom: 0px;">&nbsp;&nbsp;${listWeather.get(8).getWeatherDescribe() }</p> 
												  </div>
												</div>
												<div class="media" style="margin-bottom: 10px;">
												  <img src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/${listWeather.get(10).getWeatherType() }.svg" class="mr-3" alt="...">
												  <div class="media-body">
												    <h5 class="mt-0">&nbsp;&nbsp;${listWeather.get(10).getTime() }</h5>
												    <p style="margin-bottom: 0px;">&nbsp;&nbsp;${listWeather.get(10).getWeatherDescribe() }</p> 
												  </div>
												</div>
												<div class="media" style="margin-bottom: 10px;">
												  <img src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/day/${listWeather.get(12).getWeatherType() }.svg" class="mr-3" alt="...">
												  <div class="media-body">
												    <h5 class="mt-0">&nbsp;&nbsp;${listWeather.get(12).getTime() }</h5>
												    <p style="margin-bottom: 0px;">&nbsp;&nbsp;${listWeather.get(12).getWeatherDescribe() }</p> 
												  </div>
												</div>
											</div>
												
											<div class="night" style="display:none;">
												<div class="media" style="margin-bottom: 10px;">
												  <img src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/night/${listWeather.get(1).getWeatherType() }.svg" class="mr-3" alt="...">
												  <div class="media-body">
												    <h5 class="mt-0">&nbsp;&nbsp;${listWeather.get(0).getTime() }</h5>
												    <p style="margin-bottom: 0px;">&nbsp;&nbsp;${listWeather.get(1).getWeatherDescribe() }</p> 
												  </div>
												</div>
												<div class="media" style="margin-bottom: 10px;">
												  <img src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/night/${listWeather.get(3).getWeatherType() }.svg" class="mr-3" alt="...">
												  <div class="media-body">
												    <h5 class="mt-0">&nbsp;&nbsp;${listWeather.get(2).getTime() }</h5>
												    <p style="margin-bottom: 0px;">&nbsp;&nbsp;${listWeather.get(3).getWeatherDescribe() }</p> 
												  </div>
												</div>
												<div class="media" style="margin-bottom: 10px;">
												  <img src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/night/${listWeather.get(5).getWeatherType() }.svg" class="mr-3" alt="...">
												  <div class="media-body">
												    <h5 class="mt-0">&nbsp;&nbsp;${listWeather.get(4).getTime() }</h5>
												    <p style="margin-bottom: 0px;">&nbsp;&nbsp;${listWeather.get(5).getWeatherDescribe() }</p> 
												  </div>
												</div>
												<div class="media" style="margin-bottom: 10px;">
												  <img src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/night/${listWeather.get(7).getWeatherType() }.svg" class="mr-3" alt="...">
												  <div class="media-body">
												    <h5 class="mt-0">&nbsp;&nbsp;${listWeather.get(6).getTime() }</h5>
												    <p style="margin-bottom: 0px;">&nbsp;&nbsp;${listWeather.get(7).getWeatherDescribe() }</p> 
												  </div>
												</div>
												<div class="media" style="margin-bottom: 10px;">
												  <img src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/night/${listWeather.get(9).getWeatherType() }.svg" class="mr-3" alt="...">
												  <div class="media-body">
												    <h5 class="mt-0">&nbsp;&nbsp;${listWeather.get(8).getTime() }</h5>
												    <p style="margin-bottom: 0px;">&nbsp;&nbsp;${listWeather.get(9).getWeatherDescribe() }</p> 
												  </div>
												</div>
												<div class="media" style="margin-bottom: 10px;">
												  <img src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/night/${listWeather.get(11).getWeatherType() }.svg" class="mr-3" alt="...">
												  <div class="media-body">
												    <h5 class="mt-0">&nbsp;&nbsp;${listWeather.get(10).getTime() }</h5>
												    <p style="margin-bottom: 0px;">&nbsp;&nbsp;${listWeather.get(11).getWeatherDescribe() }</p> 
												  </div>
												</div>
												<div class="media" style="margin-bottom: 10px;">
												  <img src="https://www.cwb.gov.tw/V8/assets/img/weather_icons/weathers/svg_icon/night/${listWeather.get(13).getWeatherType() }.svg" class="mr-3" alt="...">
												  <div class="media-body">
												    <h5 class="mt-0">&nbsp;&nbsp;${listWeather.get(12).getTime() }</h5>
												    <p style="margin-bottom: 0px;">&nbsp;&nbsp;${listWeather.get(13).getWeatherDescribe() }</p> 
												  </div>
												</div>
											</div>
										</div>
									</div>
									
									<div id="menu2" class="tab-pane fade">
										<div class="tab-content-description">
											<div id="comments" class="comments-area comments-area-wrapper">
											         <!-- 留言串 -->
                                                <ul class="comment-list" style="margin-top: 0px;"> 
                                                    <!-- 留言1 -->
													<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />
													<c:forEach var="groupMessageVO" items="${listMessage}">
														<li class="comment">
															<div class="single-comment justify-content-between d-flex">
																<div class="user justify-content-between d-flex">
																	<div class="thumb">
																		<img alt="" src="/EA102G4/front-end/mem/headphoto.do?mem_id=${groupMessageVO.mem_id}" class="avatar" style="width: 100px; height: 100px;">
																	</div>
																	<div class="desc">
																		<div class="d-flex justify-content-between comment_title">
																			<div class="d-flex align-items-center">
																				<h5 style="font-size:25px;margin-bottom: 10px;">${memSvc.getOneMem(groupMessageVO.mem_id).mem_name}</h5>
																			</div>
																		</div>
																		<div class="comment-content">
																			<p id="mesText">${groupMessageVO.mes_text }</p>
																		</div>
																		<c:if test="${groupMessageVO.mes_res == null && groupVO.mem_id == memVOLogin.mem_id}">
																			<div class="reply-btn">
	                               												<a class="comment-reply-link"><span id="messageMemberId" value="${groupMessageVO.mem_id}">Reply</span></a>
	                           												</div>
	                           											</c:if>
																	</div>
																</div>
															</div>
															
															<c:if test='${(groupMessageVO.mes_res != null)}'>
															<ul class="children" style="margin-left: 50px; margin-top: 0px;">
																<li class="comment">
																	<div class="single-comment justify-content-between d-flex">
																		<div class="user justify-content-between d-flex">
																			<div class="thumb">
																				<img alt="" src="/EA102G4/front-end/mem/headphoto.do?mem_id=${groupVO.mem_id}" class="avatar" style="width: 80px; height: 80px;">
																			</div>
																			<div class="desc">
																				<div class="d-flex justify-content-between comment_title">
																					<div class="d-flex align-items-center">
																						<h5><%=memVO.getMem_name()%></h5>
																					</div>
																				</div>
																				<div class="comment-content">
																					<p>${groupMessageVO.mes_res}</p>
																				</div>
																			</div>
																		</div>
																	</div>
																</li>
															</ul>
															</c:if>
														</li>
													</c:forEach>
												</ul>
                                                <div id="respond" class="comment-respond">
                                                    <h3 class="comment-reply-title">Leave a review</h3>			
                                                    
                                                        <div class="form-group margin-bottom-30">
                                                            <textarea name="comment" id="comment" class="form-control w-100" cols="30" rows="9"></textarea>
                                                        </div>
                                                        <div class="row margin-bottom-13">
                                                            <div class="col-sm-4" style='display:${(memVOLogin.mem_id == null || memVOLogin.mem_id == groupVO.mem_id)?"none":"" };'>
                                                                <p class="form-submit">
                                                                    <button name="submit" id="submit" value="Submit Comment">Submit Comment</button>
                                                                    <span id="magicbuttom" class="material-icons">control_point</span>
                                                                </p>
                                                            </div>
                                                        </div>		
                                                </div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>


						<div id="respond" class="comment-respond" style='display:${(memVOLogin.mem_id == null)?"none":"" };'>
							<div class="container">
								<div class="row">

									<div class="col-sm-6">
										<p class="form-submit">
										<div class="edit" style='display:${(memVOLogin.mem_id == groupVO.mem_id)?"":"none" };'>

											<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/gpt/group.do" style="margin-bottom: 0px;">
												<input type="submit" value="Edit" id="submit"> 
												<input type="hidden" name="gro_no" value="${groupVO.gro_no}">
												<input type="hidden" name="action" value="getOne_For_Update">
											</FORM>
										</div>
										<c:if test="${!memIdList.contains(memVOLogin.mem_id)}">
											<div class="join_exit" style='display:${(memVOLogin.mem_id == groupVO.mem_id)?"none":"" };'>
													<button class="joinButton join" id="submit" onclick="sendMessage();">Join</button>
											</div>
										</c:if>
										<c:if test="${memIdList.contains(memVOLogin.mem_id)}">
											<div class="join_exit" style='display:${(memVOLogin.mem_id == groupVO.mem_id)?"none":"" };'>
													<button value="${groupVO_G0.gro_no}" class="joinButton exit" id="submit">Exit</button>
											</div>
										</c:if>
										</p>
									</div>

									<div class="col-sm-6">
										<p class="form-submit">
											<div class="delete" style='display:${(memVOLogin.mem_id == groupVO.mem_id)?"":"none" };'>
												<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/gpt/group.do" style="margin-bottom: 0px;">
													<input type="submit" value="Delete" id="submit"> 
													<input type="hidden" name="gro_no" value="${groupVO.gro_no}">
													<input type="hidden" name="action" value="delete">
												</FORM>
											</div>
											<div class="report" style='display:${(memVOLogin.mem_id == groupVO.mem_id)?"none":"" };'>
												<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/gpt/group.do" style="margin-bottom: 0px;">
													<input type="button" value="Report" id="submit" data-toggle="modal" data-target="#myModal" ${grSvc.getReportByMemGroNo(memVOLogin.mem_id, groupVO.gro_no) == null ? "":"disabled"}> 
													<input type="hidden" name="gro_no" value="${groupVO.gro_no}"> 
													<input type="hidden" name="gro_name" value="${groupVO.gro_name}">
													<input type="hidden" name="action" value="reportGroup">
												</FORM>
											</div>
										</p>
									</div>

									<form action="group.do" method="post" enctype="multipart/form-data">
										<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" style="color: black; font-family: microsoft JhengHei;">
											<div class="modal-dialog" role="document">
												<div class="modal-content">
													<div class="modal-header">
														<h5 class="modal-title" id="exampleModalLabel"><span class="material-icons" id="reportIcon">mood_bad</span>&nbsp;&nbsp;Activity Report</h5>
														<button type="button" class="close" data-dismiss="modal" aria-label="Close">
															<span aria-hidden="true">&times;</span>
														</button>
													</div>
													<div class="modal-body">

														<div class="form-group">
															<label for="recipient-name" class="col-form-label" id="groupName">被檢舉揪團 :</label><%=groupVO.getGro_name()%>
														</div>
														<div class="form-group">
															<label for="recipient-name" class="col-form-label" id="reportMember">檢舉人 :</label>${memVOLogin.mem_name }
														</div>
														<div class="form-group">
															<label for="message-text" class="col-form-label">Message:</label>
															<textarea class="form-control" style="color: black;" id="message-text" rows="3" cols="30" id="rep_reason" name="rep_reason" placeholder="請輸入檢舉原因" style="resize:none;" onchange="getStr()"></textarea>
														</div>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
														<button type="button" class="btn btn-secondary" data-dismiss="modal" id="Confirm">Confirm</button>
													</div>
												</div>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-4" style=" z-index: 5;">
						<div class="blog-right-content widget-area sidebar">
							<div class="widget widget_search">
								<form class="search-form" action="group.do" method="post" enctype="multipart/form-data">
								<div class="form-group">
									<input type="text" name="search" class="form-control" placeholder="Search here">
									<input type="hidden" name="action" value="getWord_For_Search">
								</div>
								<button class="submit-btn" type="submit">
									<i class="fa fa-search"></i>
								</button>
							</form>
							</div>
							<div class="widget latest-post-widget">
								<div class="eidget-title">
									<h1>Class posts</h1>
								</div>
								<c:forEach var="iClassVO" items="${set}" begin="0" end="3">
									<div class="share-img-item">
										<div class="img-part"
											style="background-image: url(<%=request.getContextPath() %>/front-end/ProAndClass/showPhotos.do?type=iClassPhoto&pic_no=${iClassVO.i_class_no});">
										</div>
										<div class="content-part">
											<h4>
												<a href="IndividualClassServlet.do?i_class_no=${iClassVO.i_class_no}&action=getOne_For_Display">${iClassVO.c_name}</a>
											</h4>
											<span class="text">Course Price : ${iClassVO.p_coin}</span>
										</div>
									</div>
								</c:forEach>
							</div>
							<div class="widget">
								<div class="thumb only-thumb">
									<img src="img/col-4.jpg" alt="">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</main>
	</div>

	 <%@ include file="include/pageFooter.file"%>

<!-- 	<!-- back to top area start --> -->
<!-- 	<div class="back-to-top"> -->
<!-- 		<span class="back-top"><i class="fa fa-angle-up"></i></span> -->
<!-- 	</div> -->
<!-- 	<!-- back to top area end --> -->
	
<%@ include file="include/chatBoxBody.file"%>
	
	
	<script>
    var map;
    var transitOptions;
    var markers = [];
    var infowindows = [];
    let zoom = 15;

    function initMap() {
        var directionsService = new google.maps.DirectionsService();
        var directionsRenderer = new google.maps.DirectionsRenderer();
        let selectedMode = document.getElementById("Transit").value;

        map = new google.maps.Map(document.getElementById('map'), {
            zoom: zoom,
            center: { lat: 24.9680014, lng: 121.1900142 }
        });

           directionsRenderer.setMap(map);
        directionsRenderer.setPanel(document.getElementById("right-panel"));
        calculateAndDisplayRoute(directionsService, directionsRenderer, selectedMode);

        document.getElementById("Driving").addEventListener("click", () => {
            selectedMode = document.getElementById("Driving").value;
            calculateAndDisplayRoute(directionsService, directionsRenderer, selectedMode);
        })
         document.getElementById("Walking").addEventListener("click", () => {
         	selectedMode = document.getElementById("Walking").value;
            calculateAndDisplayRoute(directionsService, directionsRenderer, selectedMode);
        })
          document.getElementById("Bicycling").addEventListener("click", () => {
          	selectedMode = document.getElementById("Bicycling").value;
            calculateAndDisplayRoute(directionsService, directionsRenderer, selectedMode);
        })
           document.getElementById("Transit").addEventListener("click", () => {
           	selectedMode = document.getElementById("Transit").value;
            calculateAndDisplayRoute(directionsService, directionsRenderer, selectedMode);
        })
    }

    function calculateAndDisplayRoute(directionsService, directionsRenderer, selectedMode) {
    	selectedMode = selectedMode;
        if (selectedMode === 'TRANSIT') {
            transitOptions = { modes: ['BUS'] };
        }

        console.log(selectedMode);
        console.log(transitOptions);

        map = new google.maps.Map(document.getElementById('map'), {
            zoom: zoom,
            center: { lat: 24.9680014, lng: 121.1900142 }
        });

        directionsRenderer.setMap(map);

        var request = {
            origin: { lat: 24.9680014, lng: 121.1900142 },
            destination: { lat: ${(groupVO.gro_lat != "")?groupVO.gro_lat:24.9886541}, lng: ${(groupVO.gro_lng != "")?groupVO.gro_lng:121.3142952} },
            travelMode: selectedMode,
            transitOptions: transitOptions
        };

        directionsService.route(request, function(result, status) {
            if (status == 'OK') {
                console.log(result.routes[0].legs[0].steps);
                directionsRenderer.setDirections(result);
            } else {
                console.log(status);
            }
        });

        directionsService.route(request, function(result, status) {
            if (status == 'OK') {
                var steps = result.routes[0].legs[0].steps;
                steps.forEach((e, i) => {
                    console.log(steps);
                    markers[i] = new google.maps.Marker({
                        position: { lat: e.start_location.lat(), lng: e.start_location.lng() },
                        map: map,
                        label: { text: i + '', color: "#fff" }
                    });
                    infowindows[i] = new google.maps.InfoWindow({
                        content: e.instructions
                    });
                    markers[i].addListener('click', function() {
                        if (infowindows[i].anchor) {
                            infowindows[i].close();
                        } else {
                            infowindows[i].open(map, markers[i]);
                        }
                    });
                });
                directionsRenderer.setDirections(result);
            } else {
                console.log(status);
            }
        });
    }
 
    </script>
	<%@ include file="include/js_src"%>
	<script>
	var str;
	let data = {};
	
	// JOIN 按鈕
	$('.join_exit').on("click", ".joinButton.join", function(){
		data = {};
		data.action = "joinGroup";
		data.gro_no = '${groupVO.gro_no}';
		data.mem_id = '${memVOLogin.mem_id}';
		data.mem_name = '${memVOLogin.mem_name}';
		data.gro_name = '${groupVO.gro_name}';
		
		$.ajax({
			type : "GET",
			url : "<%=request.getContextPath()%>/front-end/gpt/group.do",
			data : data,
			dataType : "json",
			success : function(resScoreConfirm) {
			}
		});
		
		swal('Success！', '', 'success')
		
		$('.join_exit').empty();
		var exitHTML = '<button value="${groupVO_G0.gro_no}" class="joinButton exit" id="submit">Exit</button>';
		$('.join_exit').append(exitHTML);
		
	});
	
	// exit 按鈕
	$('.join_exit').on("click",".joinButton.exit", function(){
		data = {};
		data.action = "memberExitGroup";
		data.gro_no = '${groupVO.gro_no}';
		data.mem_id = '${memVOLogin.mem_id}';
		
		$.ajax({
			type : "GET",
			url : "<%=request.getContextPath()%>/front-end/gpt/group.do",
			data : data,
			dataType : "json",
			success : function(resReportConfirm) {
			}
		});
		
		swal('Success！', '', 'success')
		
		$('.join_exit').empty();
		var joinHTML = '<button class="joinButton join" id="submit" onclick="sendMessage();">Join</button>';
		$('.join_exit').append(joinHTML);
	});
	
	$(document).ready(function(){ 
		$('#Confirm').click(function(){
			if($('#message-text').val().length>0){	
				$.ajax({
						 type: "GET",
						 url: "group.do",
						 data: creatQueryString(str),
						 dataType: "json",
						 success: function (data){
			         }
				 });
				swal('Success！', '', 'success')
				$('[value="Report"]#submit').prop('disabled', true);
			}else{
				swal('Please enter Reason！', '', 'error');
				e.preventdefault();
			}
		});
	});
	
		function creatQueryString(rep_reason){
			var queryString= {"action":"reportAdd", "gro_no":'<%=groupVO.getGro_no()%>', "mem_id":'${memVOLogin.mem_id }', "rep_reason": rep_reason };
			console.log(queryString);
			return queryString;
		}
		
		function getStr(){
			var intro=document.getElementById("message-text");
			str = intro.value;
		}
	</script>
	
	<!-- REVIEW -->
	<script>
	let currentMemId;
	let currentmesText;
      // Reply button
      var resArea = 
        "<div class='form-group margin-bottom-30'>"+
            "<textarea name='comment' id='comment' class='form-control w-100' cols='100' rows='4'></textarea>"+
        "<div class='row margin-bottom-13'>"+
                "<div class='col-sm-4' style='padding-top: 15px;'><p class='form-submit comment-respond' style='width: 180px;height: 64px;'>"+
                    "<button name='submit' id='submit' value='Submit Comment'>Submit Comment</button>"+
                    "</p>"+
                "</div>"+
            "</div>"+
        "</div>";

      $('#menu2').on("click","#messageMemberId",function(){
            $(this).parent().append(resArea);
            currentMemId = $(this).attr("value");
            currentmesText = $(this).parent().parent().prev().find('#mesText').text();
            
      });

      // Replay submit button
      $('#menu2').on("click",".reply-btn #submit",function(){ 
            console.log("submit button test");
            var res = 
            '<ul class="children" style="margin-left: 50px; margin-top: 0px;">'+
                '<li class="comment"><div class="single-comment justify-content-between d-flex">'+
                    '<div class="user justify-content-between d-flex">'+
                        '<div class="thumb">'+
                            '<img alt="" src="/EA102G4/front-end/mem/headphoto.do?mem_id="${memVOLogin.mem_id}" class="avatar" style="width:80px;">'+
                        '</div>'+
                            '<div class="desc">'+
                                '<div class="d-flex justify-content-between comment_title">'+
                                            '<div class="d-flex align-items-center">'+
                                                '<h5>${memVOLogin.mem_name}</h5>'+
                                            '</div>'+
                                    '</div>'+
                                    '<div class="comment-content">'+
                                        '<p>'+$('#comment').val()+'</p>'+
                                    '</div>'+
                                '</div>'+
                            '</div>'+
                    '</div>'+
                '</li>'+
            '</ul>';
            console.log($('#comment').val().trim()!=null);
            if($('#comment').val().trim()!=''){
                console.log($(this).parents('.comment'));
                $(this).parents('.comment').append(res);
                console.log("$('#comment').val() = "+$('#comment').val());
                
                
                data = {};
    			data.action = "addMessageRes";
    			data.mes_res = $('#comment').val();
    			data.gro_no = "${groupVO.gro_no}";
    			data.mem_id = currentMemId;
    			data.mes_text = currentmesText;
    			
    			$.ajax({
    				type : "GET",
    				url : "<%=request.getContextPath()%>/front-end/gpt/group.do",
    				data : data,
    				dataType : "json",
    				success : function(resScoreConfirm) {
    				}
    			});
    			$(this).parents('.reply-btn').remove();
            }
      });

      // Review submit button
      $('#menu2 #submit').click(function(){
        var review = 
            '<li class="comment">'+
                '<div class="single-comment justify-content-between d-flex">'+
                    '<div class="user justify-content-between d-flex">'+
                        '<div class="thumb">'+
                            '<img alt="" src="/EA102G4/front-end/mem/headphoto.do?mem_id=${memVOLogin.mem_id}" class="avatar" style="width: 100px;height: 100px;">'+
                        '</div>'+
                        '<div class="desc">'+
                            '<div class="d-flex justify-content-between comment_title">'+
                                '<div class="d-flex align-items-center">'+
                                    '<h5 style="font-size:25px;margin-bottom: 10px;">${memVOLogin.mem_name}</h5>'+                  
                                '</div>'+
                            '</div>'+
                            '<div class="comment-content">'+
                                '<p id="mesText">'+
                                $('.comment-respond #comment').val()+
                                '</p>'+
                            '</div>'+
                            '<c:if test="${groupMessageVO.mes_res == null && groupVO.mem_id == memVOLogin.mem_id}">'+
                            '<div class="reply-btn">'+
                                '<a class="comment-reply-link">Reply</a>'+
                            '</div>'+
                            '</c:if>'+
                        '</div>'+
                    '</div>'+
                '</div>'+
            '</li>';
        $('#menu2 .comment-list').append(review);
        
	        data = {};
			data.action = "addMessage";
			data.gro_no = '<%=groupVO.getGro_no()%>';
			data.mem_id = '${memVOLogin.mem_id}';
			data.mes_text = $('.comment-respond #comment').val();
			$.ajax({
				type : "GET",
				url : "<%=request.getContextPath()%>/front-end/gpt/group.do",
				data : data,
				dataType : "json",
				success : function(resScoreConfirm) {
				}
			});
			swal('Success！', '', 'success');
			console.log("review");
			console.log(data);
      });
    </script>
    
    <script type="text/javascript">
	   	$(document).ready(function(){
	   		$('.toggle-input').removeAttr('checked');
	   	});	
    
	    const toggle = document.querySelector('.toggle-input');
	    const initialState = localStorage.getItem('toggleState') == 'true';
	    toggle.checked = initialState;
	
	    toggle.addEventListener('change', function () {
	      localStorage.setItem('toggleState', toggle.checked);
	    });
	    
	    $('.toggle-input').click(function(){
	    	$('.day').toggle();
	    	$('.night').toggle();
	    });
  	</script>
    
    <script>
    
    var MyPoint = "/MembersCenter/${memVOLogin.mem_id}";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
	
	var webSocket;
	function connect() {
		webSocket = new WebSocket(endPointURL);
		webSocket.onopen = function(event) {
			console.log("${memVOLogin.mem_id} Connect Success!");
		};
		
		webSocket.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			if ("notice" === jsonObj.type) {
				console.log("成功接收訊息!!!!!!!!!");
            var h = '入團通知' || false,
                   s = jsonObj.message,
                   c = $(this).data('color') || '#CD0505',
                   t = 10000;
            triggerToast(h, s, c, t);
			}else if("statusCheck" === jsonObj.type){
				console.log("成功接收揪團訊息!!!!!!!!!");
	               var h = '揪團狀態' || false,
	                   s = jsonObj.message,
	                   c = $(this).data('color') || '#CD0505',
	                   t = 10000;
	        triggerToast(h, s, c, t);
			}else if("finalStatus" === jsonObj.type){
				console.log("成功接收揪團訊息!!!!!!!!!");
	               var h = '揪團狀態' || false,
	                   s = jsonObj.message,
	                   c = $(this).data('color') || '#CD0505',
	                   t = 10000;
	        triggerToast(h, s, c, t);
			}
		}
		
		webSocket.onclose = function(event) {
			console.log("Disconnected!");
		};
	}
	
	function sendMessage() {
		console.log("sendMessage")
		$('.join form').submit();
		var message = '${memVOLogin.mem_name}加入你的${groupVO.gro_name}揪團囉!';
			var jsonObj = {
				"type" : "notice",
				"sender" : "${groupVO.gro_no}",
				"receiver" : "${groupVO.mem_id}",
				"message" : message
			};
			webSocket.send(JSON.stringify(jsonObj));
	}
	 function triggerToast(head, strings, color, time) {
	            $.toast({
	                heading: head,
	                text: strings,
	                loaderBg: color,
	                hideAfter: Number(time),
	                position: 'bottom-left'
	            });
	        }
    </script>
    
   	<script>
		//magicbuttom
		$('#magicbuttom ').click(function(){
			$('#comment').val('怎麼可以亂打人呢!!?我一定要檢舉你');
		});
	</script>
   <!-- chatBox -->
   <%@ include file="include/chatBox.file"%>
</body>
</html>