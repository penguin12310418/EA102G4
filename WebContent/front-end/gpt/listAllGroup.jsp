<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.group.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.individualClass.model.*"%>

<%
	int[] colBox = {4, 8, 8, 4, 4, 8};
	int[] modelBox = {1, 2, 2, 1, 1, 2};
	
	GroupService groupSvc = new GroupService();
	List<GroupVO> list = groupSvc.getAll();
	
	pageContext.setAttribute("list", list);
	pageContext.setAttribute("colBox", colBox);
	pageContext.setAttribute("modelBox", modelBox);
	MemVO memVOLogin = (MemVO) session.getAttribute("memVOLogin");
	
	IndividualClassService IndividualClassSvc = new IndividualClassService();
	List<IndividualClassVO> listClass = IndividualClassSvc.getAll();
	Set<IndividualClassVO> set = new HashSet<IndividualClassVO>(listClass);
	pageContext.setAttribute("set", set);
	pageContext.setAttribute("memVOLogin", memVOLogin);
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>listAllGroup</title>

<%@ include file="include/css_link"%>


<style>
@font-face {
	font-family: 'Material Icons';
	font-style: normal;
	font-weight: 400;
	src: url(https://example.com/MaterialIcons-Regular.eot);
	/* For IE6-8 */
	src: local('Material Icons'), local('MaterialIcons-Regular'),
		url(https://example.com/MaterialIcons-Regular.woff2) format('woff2'),
		url(https://example.com/MaterialIcons-Regular.woff) format('woff'),
		url(https://example.com/MaterialIcons-Regular.ttf) format('truetype');
}

.material-icons {
	font-family: 'Material Icons';
	font-weight: normal;
	font-style: normal;
	font-size: 24px; /* Preferred icon size */
	display: inline-block;
	line-height: 1;
	text-transform: none;
	letter-spacing: normal;
	word-wrap: normal;
	white-space: nowrap;
	direction: ltr;
	/* Support for all WebKit browsers. */
	-webkit-font-smoothing: antialiased;
	/* Support for Safari and Chrome. */
	text-rendering: optimizeLegibility;
}

.intro {
	overflow: hidden;
	text-overflow: ellipsis;
	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-line-clamp: 2;
}

.nice-select {
	color: black;
}

.list::-webkit-scrollbar {
	width: 8px;
	background-color: transparent;
}

.list::-webkit-scrollbar-thumb {
	border-radius: 10px;
	background-color: #ececec;
}

.list::-webkit-scrollbar-track {
	border-radius: 10px;
	background-color: transparent;
}

.nice-select .list {
	height: 40px;
	overflow: auto;
	text-align: center;
}

.nice-select {
	text-align: center;
}

.list-group-item.active {
	color: black;
	background-color: white;
	border-color: #e8e8e8;
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

#smallImg{
	background-repeat: no-repeat;
	background-position: center;
	height: 366.125px;
	background-size: cover;
}

#bigImg{
	background-repeat: no-repeat;
	background-position: center;
	height: 435.434px;
	background-size: cover;
}

.blog-breadcrumb-overlay {
    background: -webkit-linear-gradient(left,rgb(0 0 0 / 0.4),rgb(0 0 0 / 0.4)), url(img/1244995.jpg);
    background-position: 97px -340px;
    background-size: 90%;
}

span.groStart {
    position: relative;
    top: -5px;
}

span.typeName {
    position: relative;
    top: -5px;
    font-family:microsoft JhengHei;
}

sub {
    font-family: microsoft JhengHei;
    color: #BCBCBC;
    font-size: 15px;
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

.img-part{
	width:134px;
	height:122px;
	background-repeat: no-repeat;
	background-position: center;
	background-size: cover;
}

.share-img-item .content-part {
    padding-top: 0px;
}

.col-lg-4 {
    z-index: 3;
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
			<h1 class="page-title">Activity List</h1>
			<ul class="page-list margin-bottom-10">
				<li style='display:${memVOLogin == null ?"none":""};'><a href="<%=request.getContextPath()%>/front-end/gpt/group.do?action=viewMyGroup&mem_id=${memVOLogin.mem_id }">My Activity</a></li>
				<li style='display:${memVOLogin == null ?"none":""};'><a href="<%=request.getContextPath()%>/front-end/gpt/groupAdd.jsp">Create Activity</a></li>
			</ul>
		</div>
	</div>
	<!-- breadcrumb area end -->

	<!-- home blog start -->
	<div class="blog-details-area margin-top-100">
		<div class="container">
			<div class="row">
				<div class="col-lg-8">
					<div class="row">

						<div class="col-lg-12">
							<div class="row d-flex justify-content-lg-between flex-column flex-md-row align-items-center text-center">
								<div class="form-field margin-top-10 margin-bottom-10 ">
									<select class="select-form country_select" id="scopeItem">
										<option value='search' selected>Search by categories</option>
									</select>
								</div>
								<div class="search-menu">
									<ul id="scopeArea">
										<a href="listAllGroup.jsp"><li><button type="button" class="list-group-item list-group-item-action">All</button></li></a>
										<li><button type="button" class="list-group-item list-group-item-action">CityScope</button></li>
										<li><button type="button" class="list-group-item list-group-item-action">TypeScope</button></li>
									</ul>
								</div>
							</div>
						</div>

						<div class="col-lg-12">
							<div class="blog-single-item">
								<div class="row">
									<!------------------------------------------------內容範圍----------------------------------------------->
									<%@ include file="include/newPage1.file"%>
									<jsp:useBean id="gtSvc" scope="page" class="com.grouptype.model.GroupTypeService" />
									<c:forEach var="groupVO" items="${list}" varStatus="listIndex" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
										<c:if test="${modelBox[(listIndex.index%6)]==1}">
											<div class="col-lg-${colBox[(listIndex.index%6)]} %>" style="padding-left: 10px; padding-right: 10px;">
												<div class="blog-item-inner">
													<div class="blog-detail">
														<div class="blog-meta">
															<h6>
																<span class="material-icons">book_online</span>&nbsp;<span class="groStart" style="font-weight:600;"><fmt:formatDate value="${groupVO.gro_start}" pattern="yyyy/MM/dd HH:mm" /></span> 
																<span class="float-right text-right"> 
																	<span class="material-icons">sports_handball</span>&nbsp;<span class="typeName" style="font-weight:bold;">${gtSvc.getTypeName(groupVO.type_no).type_name}</span>
																</span>
															</h6>
														</div>
														<h5>
															<a href="oneGroupDetail.jsp?gro_no=${groupVO.gro_no}" style="color: black;">${groupVO.gro_name}</a><br><sub>(${groupVO.gro_addr.substring(0,3)})</sub>
														</h5>
														<p class="intro">${groupVO.gro_intro}</p>
														<a href="oneGroupDetail.jsp?gro_no=${groupVO.gro_no}" style="color: black;">Read More</a> <span
															class="float-right text-right" style='color: black; display:${(memVOLogin.mem_id == groupVO.mem_id)?"":"none" };'>
															<a href="groupUpdate.jsp?gro_no=${groupVO.gro_no}">Edit</a>
														</span>
													</div>
													<div class="blog-img" id="smallImg" style="background-image: url(pictool.do?gro_no=${groupVO.gro_no});">
													</div>
												</div>
											</div>
										</c:if>
										<c:if test="${modelBox[(listIndex.index%6)]==2}">
											<div class="col-lg-${colBox[(listIndex.index%6)]} %>">
												<div class="blog-item-inner">
													<div class="blog-img" id="bigImg" style="background-image: url(pictool.do?gro_no=${groupVO.gro_no});">
													</div>
													<div class="blog-detail">
														<div class="blog-meta">
															<h6>
																<span class="material-icons">book_online</span>&nbsp;<span class="groStart" style="font-weight:bold;"><fmt:formatDate value="${groupVO.gro_start}" pattern="yyyy/MM/dd HH:mm" /></span> 
																<span class="float-right text-right"> 
																	<span class="material-icons">sports_handball</span>&nbsp;<span class="typeName" style="font-weight:600;">${gtSvc.getTypeName(groupVO.type_no).type_name}</span>
																</span>
															</h6>
														</div>
														<h5>
															<a href="oneGroupDetail.jsp?gro_no=${groupVO.gro_no}" style="color: black;">${groupVO.gro_name}</a>&nbsp;&nbsp;<sub>(${groupVO.gro_addr.substring(0,3)})</sub>
														</h5>
														<p class="intro">${groupVO.gro_intro}</p>
														<a href="oneGroupDetail.jsp?gro_no=${groupVO.gro_no}" style="color: black;">Read More</a> <span
															class="float-right text-right" style='color: black; display:${(memVOLogin.mem_id == groupVO.mem_id)?"":"none" };'>
															<a href="groupUpdate.jsp?gro_no=${groupVO.gro_no}">Edit</a>
														</span>
													</div>
												</div>
											</div>
										</c:if>
									</c:forEach>
									<!------------------------------------------------內容範圍----------------------------------------------->
								</div>
							</div>
						</div>
						<%@ include file="include/pageINGYM.txt"%>
					</div>
				</div>
				<div class="col-lg-4">
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
								<div class="img-part" style="background-image: url(<%=request.getContextPath() %>/front-end/ProAndClass/showPhotos.do?type=iClassPhoto&pic_no=${iClassVO.i_class_no});">
								</div>
								<div class="content-part">
									<h4><a href="/EA102G4/front-end/ProAndClass/IndividualClassServlet.do?i_class_no=${iClassVO.i_class_no}&action=getOne_For_Display">${iClassVO.c_name}</a></h4>
									<span class="text">Course Price : ${iClassVO.p_coin}</span>
								</div>
							</div>
							</c:forEach>
						</div>
						<div class="widget">
							<div class="thumb only-thumb" >
								<img src="img/col-4.jpg" alt="">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

<%@ include file="include/pageFooter.file"%>

<!-- 	<!-- back to top area start --> -->
<!-- 	<div class="back-to-top"> -->
<!-- 		<span class="back-top"><i class="fa fa-angle-up"></i></span> -->
<!-- 	</div> -->
<!-- 	<!-- back to top area end --> -->
	
<%@ include file="include/chatBoxBody.file"%>
	<!------------------------------------------------------------------------------------------------------------------------->
	<%@ include file="include/js_src"%>
	<script>
		$(document).ready(function() {
			$.ajax({
				url : "group.do",
				type : "post",
				data : {action : "getAllGroupTypeJSON"},
				success : function(getAllGroupTypeJSON) {
					jsonGroupTypeData = JSON.parse(getAllGroupTypeJSON);
				}
			});
	
			var jsonGroupTypeData;
			$("#scopeArea>li").click(function() {
				if ($(this).text() === 'CityScope') {
					currentScope = $(this).text();
					var city = [ '基隆市', '台北市', '新北市', '桃園市', '新竹市', '新竹縣', '苗栗縣', '台中市', '彰化縣', '南投縣',
								 '雲林縣', '嘉義市', '嘉義縣', '台南市', '高雄市', '屏東縣', '台東縣', '花蓮縣', '宜蘭縣' ];
					var selectCity = '<li data-value="search" class="option selected focus" value="search" selected>Search by categories</li>'
					for (i in city) {
						selectCity += "<a href='/EA102G4/front-end/gpt/group.do?action=getCity_For_Search&cityScope="+ city[i]+ "'>"
								+ "<li data-value='search' class='option selected focus' value='" + city[i] +"'>"
								+ city[i]+ "</li>" + "</a>";
					}
					$(".nice-select .list").css("height", "240px")
					$(".list").html(selectCity);
				} else if ($(this).text() === 'TypeScope') {
					currentScope = $(this)
							.text();
					var selectType = '<li data-value="search" class="option selected focus" value="search" selected>Search by categories</li>'
					for (i in jsonGroupTypeData) {
						selectType += "<a href='/EA102G4/front-end/gpt/group.do?action=getType_For_Search&typeScope=" + jsonGroupTypeData[i].type_no
								+ "'>" + "<li data-value='search' class='option selected focus' value='" + jsonGroupTypeData[i].type_no+"'>"
								+ jsonGroupTypeData[i].type_name + "</li>" + "</a>";
					}
					$(".nice-select .list").css("height", "240px")
					$(".list").html(selectType);
				} else {
					$(".nice-select .list").css("height", "40px")
					var selectCity = '<li data-value="search" class="option selected focus" value="search" selected>Search by categories</option>'
					$(".list").html(selectCity);
				}
	
				$(".list").unbind('click').on('click', 'li', function() {
						console.log($(this).attr("value"));
					});
				});
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
				console.log("成功接收加入訊息!!!!!!!!!");
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
		var message = '${memVOLogin.mem_name}加入你的${groupVO.gro_name}揪團囉!';
			var jsonObj = {
				"type" : "notice",
				"sender" : "${memVOLogin.mem_id}",
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
    
   <!-- chatBox -->
<%@ include file="include/chatBox.file"%>
</body>
</html>