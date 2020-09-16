<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.group.model.*"%>
<%@ page import="com.grouplist.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.groupreport.model.*"%>
<%@ page import="java.util.stream.Collectors"%>

<%
	MemVO memVOLogin = (MemVO) session.getAttribute("memVOLogin");
	GroupService groupSvc = new GroupService();
	GroupListService glSvc = new GroupListService();
	GroupReportService grSvc = new GroupReportService();
	List<GroupVO> memVOLoginlist = groupSvc.getGroupByMember(((MemVO) session.getAttribute("memVOLogin")).getMem_id());
	List<GroupVO> list = new ArrayList<GroupVO>();

	if(memVOLoginlist.size() != 0){
		list = memVOLoginlist.stream().filter(p -> (p.getGro_status().equals("G0") || p.getGro_status().equals("G2")))
				.collect(Collectors.toList());
	}

	pageContext.setAttribute("list", list);
	pageContext.setAttribute("glSvc", glSvc);
	pageContext.setAttribute("grSvc", grSvc);
%>

<%
	int G02 = 0, G3 = 0;
	List<GroupVO> thisMemberGroup = (List<GroupVO>)session.getAttribute("thisMemberGroup");
	if(thisMemberGroup.size() != 0){
		for(int i = 0; i<thisMemberGroup.size(); i++){
			if(thisMemberGroup.get(i).getGro_status().equals("G2") || thisMemberGroup.get(i).getGro_status().equals("G0")){
				G02++;
			}else if(thisMemberGroup.get(i).getGro_status().equals("G3")){
				G3++;
			}
		}
		pageContext.setAttribute("G02",G02);
		pageContext.setAttribute("G3",G3);
	}
	pageContext.setAttribute("G02",G02);
	pageContext.setAttribute("G3",G3);

%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>selectGroupByMember.jsp</title>

<%@ include file="include/css_link"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js" type="text/javascript"></script>

<style>
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

.cart-content-area .cart-title h2 {
	margin-top: 50px;
}

.modal-dialog{
	overflow-y: initial !important
}

.modal-body.memberlist{
	height: 600px;
	overflow-y: auto;
}

.modal-body.memberlist::-webkit-scrollbar {
	width: 8px;
	background-color: transparent;
}

.modal-body.memberlist::-webkit-scrollbar-thumb {
	border-radius: 10px;
	background-color: #ececec;
}

.modal-body.memberlist::-webkit-scrollbar-track {
	border-radius: 10px;
	background-color: transparent;
}

img.square {
	width: 100px;
	border-radius: 50%;
	border: 1px solid rgba(255,0,0,1.00);
}
table,td{
	vertical-align: middle !important;
 	text-align: center !important;
}
.btn.btn-primary.btn-sm{
	font-size:20px;
}

.blog-breadcrumb-overlay {
    background: -webkit-linear-gradient(left,rgb(0 0 0 / 0.4),rgb(0 0 0 / 0.4)), url(img/1244995.jpg);
    background-position: 97px -340px;
    background-size: 90%;
}
p {
    font-family: microsoft JhengHei;
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

span.material-icons {
    position: relative;
    font-size: 40px;
    top: 8px;
}
</style>

<style>
.rating-stars {
    width: 100%;
    text-align: center;
}

.rating-stars .rating-stars-container {
    font-size: 0px;
}

.rating-stars .rating-stars-container .rating-star {
    display: inline-block;
    font-size: 32px;
    color: #555555;
    cursor: pointer;
    padding: 5px 10px;
}

.rating-stars .rating-stars-container .rating-star.is--active,
.rating-stars .rating-stars-container .rating-star.is--hover {
    color: #f1c40f;
}

.rating-stars .rating-stars-container .rating-star.is--no-hover {
    color: #555555;
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

p {
    margin: 0 0 7px;
}

.nav.nav-pills li {
    font-size: 20px;
    font-weight: 500;
}

p#historyTime {
    top: -62.5px;
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
	<div class="breadcrumb-style-1 cart-breadcrumb-overlay blog-breadcrumb-overlay">
		<div class="breadcrumb-inner">
			<h1 class="page-title">My Activity</h1>
			<ul class="page-list margin-bottom-0 margin-top-10">
				<li><a href="<%=request.getContextPath()%>/front-end/gpt/listAllGroup.jsp">Activity List</a></li>
				<li style='display:${memVOLogin == null ?"none":""};'><a href="<%=request.getContextPath()%>/front-end/gpt/groupAdd.jsp">Create Activity</a></li>
			</ul>
		</div>
	</div>
	<!-- breadcrumb area end -->

	<!-- cart content start -->
	<div class="class-details-area">
		<main id="main" class="site-main">
			<div class="biography-area margin-top-60">
				
					<div class="container">
						<div class="row">
							<div class="col-lg-12">
							<div class="biography-content">
								<ul class="nav nav-pills">
									<li><a data-toggle="pill" href="#home" class="active">My Activity</a></li>
									<li><a data-toggle="pill" href="#menu1">My Join Activity</a></li>
									<li><a data-toggle="pill" href="#menu2">History Activity</a></li>
								</ul>
								<div class="tab-content">
									<div id="home" class="tab-pane fade in active show">
										<table class="table table-bordered">
											<tbody>
												<jsp:useBean id="grouptypeSvc" scope="page" class="com.grouptype.model.GroupTypeService" />
												<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />
												<c:if test="${list.size() == 0}">
													<tr><td style="font-size: 18px; font-weight: 900;">尚無建立揪團</td></tr>
												</c:if>
												<c:forEach var="groupVO" items="${list}" varStatus="groupVOIndex">
													<c:if test="${groupVO.gro_status == 'G0' || groupVO.gro_status == 'G2'}">
														<tr id="tr_${groupVO.gro_no}">
															<td><a href="oneGroupDetail.jsp?gro_no=${groupVO.gro_no}"><img src="pictool.do?gro_no=${groupVO.gro_no}"
																	alt="" style="width: 200px"></a></td>
															<td>
																<p class="font-semibold">團主 :</p>
																<p class="font-semibold" id="groupOwner">${memSvc.getOneMem(groupVO.mem_id).mem_name}</p>
															</td>
															<td>
																<p class="font-semibold">揪團名稱 :</p>
																<p class="font-semibold" id="groupName">${groupVO.gro_name}</p>
															</td>
															<td>
																<p class="font-semibold">揪團狀態 :</p>
																<c:if test="${groupVO.gro_status == 'G0'}">
																<p class="font-semibold" id="groupStatus">招募中</p>
																</c:if>
																<c:if test="${groupVO.gro_status == 'G2'}">
																<p class="font-semibold" id="groupStatus">活動進行中</p>
																</c:if>
															</td>
															<td>
																<p class="font-semibold">開始時間 :</p>
																<p class="font-semibold"><fmt:formatDate value="${groupVO.gro_start}" pattern="yyyy/MM/dd HH:mm" /></p>
																<p class="font-semibold">結束時間 :</p>
																<p class="font-semibold"><fmt:formatDate value="${groupVO.gro_end}" pattern="yyyy/MM/dd HH:mm" /></p>
															</td>
															<td>
																<p class="font-semibold">人數 :</p>
																<p class="font-semibold">
																	<span id="${groupVO.gro_no}">${glSvc.findGroupMember(groupVO.gro_no).size()}</span> / ${groupVO.gro_max}
																</p>
															</td>
															<td>
																<button id="memberScope" value="${groupVO.gro_no}" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#exampleModal">Members</button> 
																<input type="hidden" name="action" value="view_My_Member">
															</td>
															<td>
																<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/gpt/group.do" style="margin-bottom: 0px;">
																	<input type="submit" value="Edit" class="btn btn-primary btn-sm"> <input type="hidden" name="gro_no"
																		value="${groupVO.gro_no}"> <input type="hidden" name="action" value="getOne_For_Update">
																</FORM>
															</td>
															<td>
																<button value="${groupVO.gro_no}" class="btn btn-primary btn-sm" id="delete">Delete</button>
															</td>
														</tr>
													</c:if>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div id="menu1" class="tab-pane fade">
										<table class="table table-bordered">
											<tbody>
												<c:if test="${G02 == 0}">
													<tr><td style="font-size: 18px; font-weight: 900;">尚無加入揪團</td></tr>
												</c:if>
												<c:forEach var="groupVO_G0" items="${thisMemberGroup}">
													<c:if test="${groupVO_G0.gro_status == 'G0' || groupVO_G0.gro_status == 'G2'}">
														<tr id="tr_${groupVO_G0.gro_no}">
															<td><a href="oneGroupDetail.jsp?gro_no=${groupVO_G0.gro_no}"><img
																	src="pictool.do?gro_no=${groupVO_G0.gro_no}" alt="" style="width: 200px"></a></td>
															<td>
																<p class="font-semibold">團主 :</p>
																<p class="font-semibold" id="groupOwner">${memSvc.getOneMem(groupVO_G0.mem_id).mem_name}</p>
															</td>
															<td>
																<p class="font-semibold">揪團名稱 :</p>
																<p class="font-semibold" id="groupName">${groupVO_G0.gro_name}</p>
															</td>
															<td>
																<p class="font-semibold">揪團狀態 :</p>
																<c:if test="${groupVO_G0.gro_status == 'G0'}">
																<p class="font-semibold" id="groupStatus">招募中</p>
																</c:if>
																<c:if test="${groupVO_G0.gro_status == 'G2'}">
																<p class="font-semibold">活動進行中</p>
																</c:if>
															</td>
															<td>
																<p class="font-semibold">開始時間 :</p>
																<p class="font-semibold"><fmt:formatDate value="${groupVO_G0.gro_start}" pattern="yyyy/MM/dd HH:mm" /></p>
																<p class="font-semibold">結束時間 :</p>
																<p class="font-semibold"><fmt:formatDate value="${groupVO_G0.gro_end}" pattern="yyyy/MM/dd HH:mm" /></p>
															</td>
															<td>
																<p class="font-semibold">人數 :</p>
																<p class="font-semibold">${glSvc.findGroupMember(groupVO_G0.gro_no).size()}/${groupVO_G0.gro_max}</p>
															</td>

															<td>
																<button id="memberScope" value="${groupVO_G0.gro_no}" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#exampleModal">Members</button> 
																<input type="hidden" name="action" value="view_My_Member">
															</td>
															<td>
																<button value="${groupVO_G0.gro_no}" class="btn btn-primary btn-sm" id="exit">Exit</button>
															</td>
															<td>
																<button value="${groupVO_G0.gro_no}" id="reportTarget" 
																	name="${groupVO_G0.gro_name}"
																	data-gro_no="${groupVO_G0.gro_no}"
																	class="btn btn-primary btn-sm" 
																	data-toggle="modal" 
																	data-target="#myModal" ${grSvc.getReportByMemGroNo(memVOLogin.mem_id, groupVO_G0.gro_no) == null ? "":"disabled"}>Report</button> 
																<input type="hidden" name="gro_name" value="${groupVO_G0.gro_name}"> 
																<input type="hidden" name="action" value="reportGroup">
															</td>
														</tr>
													</c:if>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div id="menu2" class="tab-pane fade">
										<table class="table table-bordered">
											<tbody>
												<c:if test="${G3 == 0}">
													<tr><td style="font-size: 18px; font-weight: 900;">尚無歷史揪團</td></tr>
												</c:if>
												<c:forEach var="groupVO_G3" items="${thisMemberGroup}">
													<c:if test="${groupVO_G3.gro_status == 'G3' }">
														<tr id="tr_${groupVO_G3.gro_no}">
															<td><a href="oneGroupDetail.jsp?gro_no=${groupVO_G3.gro_no}"><img
																	src="pictool.do?gro_no=${groupVO_G3.gro_no}" alt="" style="width: 200px"></a></td>
															<td>
																<p class="font-semibold">團主 :</p>
																<p class="font-semibold" id="groupOwner">${memSvc.getOneMem(groupVO_G3.mem_id).mem_name}</p>
															</td>
															<td>
																<p class="font-semibold">揪團名稱 :</p>
																<p class="font-semibold" id="groupName">${groupVO_G3.gro_name}</p>
															</td>
															<td>
																<p class="font-semibold">揪團狀態 :</p>
																<p class="font-semibold" id="groupStatus">活動結束</p>
															</td>
															<td>
																<p class="font-semibold">開始時間 :</p>
																<p class="font-semibold"><fmt:formatDate value="${groupVO_G3.gro_start}" pattern="yyyy/MM/dd HH:mm" /></p>
																<p class="font-semibold">結束時間 :</p>
																<p class="font-semibold"><fmt:formatDate value="${groupVO_G3.gro_end}" pattern="yyyy/MM/dd HH:mm" /></p>
															</td>
															<td>
																<p class="font-semibold">人數 :</p>
																<p class="font-semibold">${glSvc.findGroupMember(groupVO_G3.gro_no).size()}/${groupVO_G3.gro_max}</p>
																
															</td>
															<td>
																<button value="${groupVO_G3.gro_no}" id="scoreTarget" name="${groupVO_G3.gro_name}"
																	class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModalHistory" ${glSvc.getOneGroupListDetail(groupVO_G3.gro_no, memVOLogin.mem_id).rating_status == "GR1" ? "disabled":""}>Score</button> 
																<input type="hidden" name="gro_name" value="${groupVO_G3.gro_name}"> 
																<input type="hidden" name="action" value="scoreGroup">
															</td>
														</tr>
													</c:if>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<!-- biography end -->
					</div>
				</div>
			</div>
		</main>
	</div>

	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel"><span class="material-icons">groups</span>&nbsp;&nbsp;Group Members</h5>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body memberlist">
						<table class="table table-bordered">
							<tbody id="listMemberBody">
								<tr>
									<td>No Members</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal" id="modalClose">Close</button>
					</div>
				</div>
			</div>
		</div>

		<form action="group.do" method="post" enctype="multipart/form-data">
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true"
				style="color: black; font-family: microsoft JhengHei;">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel"><span class="material-icons">mood_bad</span>&nbsp;&nbsp;Activity Report</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">

							<div class="form-group">
								<label for="recipient-name" class="col-form-label" id="groupName">被檢舉揪團 : </label>
							</div>
							<div class="form-group">
								<label for="recipient-name" class="col-form-label" id="reportMember">檢舉人 : ${memVOLogin.mem_name}</label>
							</div>
							<div class="form-group">
								<label for="message-text" class="col-form-label">Message:</label>
								<textarea class="form-control" style="color: black;" id="message-text" rows="3" cols="30" id="rep_reason" name="rep_reason"
									placeholder="請輸入檢舉原因" style="resize:none;"></textarea>
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
		
		<form action="group.do" method="post" enctype="multipart/form-data">
			<div class="modal fade" id="myModalHistory" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true"
				style="color: black; font-family: microsoft JhengHei;">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel"><span class="material-icons">thumbs_up_down</span>&nbsp;&nbsp;Group Score</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">

							<div class="form-group">
								<label for="recipient-name" class="col-form-label" id="scoreGroupName">被評價揪團 : </label>
							</div>
							<div class="form-group">
								<label for="recipient-name" class="col-form-label" id="scoreMember">評價人 : ${memVOLogin.mem_name}</label>
							</div>
							
						<div class="rating-stars block">
							<div class="rating-stars-container">
								<div class="rating-star">
									<i class="fa fa-star"></i>
								</div>
								<div class="rating-star">
									<i class="fa fa-star"></i>
								</div>
								<div class="rating-star">
									<i class="fa fa-star"></i>
								</div>
								<div class="rating-star">
									<i class="fa fa-star"></i>
								</div>
								<div class="rating-star">
									<i class="fa fa-star"></i>
								</div>
							</div>
						</div>
						<div style="text-align: center;">Score : <span id="ratingChanged">0</span></div>
						
						
						<div class="form-group">
								<label for="message-text" class="col-form-label">Message:</label>
								<textarea class="form-control" style="color: black;" id="scoreMessage-text" rows="3" cols="30" placeholder="想說的話" style="resize:none;"></textarea>
							</div>

						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-secondary" data-dismiss="modal" id="scoreConfirm">Confirm</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	<!-- cart content end -->

<%@ include file="include/pageFooter.file"%>

<!-- 	<!-- back to top area start --> -->
<!-- 	<div class="back-to-top"> -->
<!-- 		<span class="back-top"><i class="fa fa-angle-up"></i></span> -->
<!-- 	</div> -->
<!-- 	<!-- back to top area end --> -->
	
	<%@ include file="include/chatBoxBody.file"%>

	<%@ include file="include/js_src"%>
	<script src="assets/js/jquery.rating-stars.min.js"></script>
	
	 <script>
        var ratingOptions = {
            selectors: {
                starsSelector: '.rating-stars',
                starSelector: '.rating-star',
                starActiveClass: 'is--active',
                starHoverClass: 'is--hover',
                starNoHoverClass: 'is--no-hover',
                targetFormElementSelector: '.rating-value'
            }
        };

        $(".rating-stars").ratingStars(ratingOptions);

        $(".rating-stars").on("ratingChanged", function (ev, data) {
            $("#ratingChanged").html(data.ratingValue);
            console.log($('#ratingChanged').text());
        });

        $(".rating-stars").on("ratingOnEnter", function (ev, data) {
            $("#ratingOnEnter").html(data.ratingValue);
        });

        $(".rating-stars").on("ratingOnLeave", function (ev, data) {
            $("#ratingOnLeave").html(data.ratingValue);
        });
    </script>
    
	<script>
		$(function() {
			var str;
			var gro_no;
			var member;
			var memberArray;
			var scoreTarget;
			var scoreValue;
			var currentMemberNumber;
			
			let data = {};
			
			// delete 按鈕
			$('.tab-content').on('click','#delete', function(){
				data = {};
				eventTarget = $(this);
				data.action = "delete";
				data.gro_no = $(this).attr('value');
				data.mem_id = '${memVOLogin.mem_id}';
				
				  swal({
		                title: "確定刪除？",
		                html: "按下確定後資料會永久刪除",
		                type: "question", // type can be "success", "error", "warning", "info", "question"
		                showCancelButton: true,
		                showCloseButton: true,
		            }).then(function(result) {
		                if (result) {
		                    $.ajax({
		                    type : "GET",
		                    url : "<%=request.getContextPath()%>/front-end/gpt/group.do",
		                    data : data,
		                    dataType : "json",
		                    success : function(resReportConfirm) {
		                    }
		                });
		                    eventTarget.closest('tr').fadeOut('slow',function(){
		                    eventTarget.remove();
		                });
		                    swal("完成!", "資料已經刪除", "success");
		                }
		            }, function(dismiss) { 
		                swal("取消", "資料未被刪除", "error");
		            }).catch(swal.noop);
			});
						
			$('#menu2 #scoreTarget').click(function() {
				scoreValue = $(this).val();
				$("#scoreGroupName").text("被評價揪團 : ");
				$("#scoreGroupName").append($(this).attr("name"));
			});
			
			// 評分確認鍵
			$('#scoreConfirm').click(function(){
				data = {};
				data.action = "updateScore";
				data.gro_no = scoreValue;
				data.mem_id = '${memVOLogin.mem_id}';
				data.gro_rating = $('#ratingChanged').text();
				data.gro_rating_intro = $('#scoreMessage-text').val();
				$.ajax({
					type : "GET",
					url : "<%=request.getContextPath()%>/front-end/gpt/group.do",
					data : data,
					dataType : "json",
					success : function(resScoreConfirm) {
					}
				});
				$('#menu2 [value="'+scoreValue+'"]#scoreTarget').prop('disabled', true);
				$('#scoreMessage-text').val('');
				swal('Success！', '', 'success');
			});
			
			//  檢舉 
			$('#menu1 #reportTarget').click(function() {
				$("#groupName").text("被檢舉揪團 : ");
				gro_no = $(this).val();
					
				$("#groupName").append($(this).attr("name"));
			});
			
			
			// exit 按鈕
			$('.tab-content').on('click','#exit', function(){
				data = {};
				data.action = "memberExitGroup";
				data.gro_no = $(this).attr('value');
				console.log("exitValue = "+$(this).attr('value'));
				data.mem_id = '${memVOLogin.mem_id}';
				
				$.ajax({
					type : "GET",
					url : "<%=request.getContextPath()%>/front-end/gpt/group.do",
					data : data,
					dataType : "json",
					success : function(resReportConfirm) {
					}
				});
				$(this).closest('tr').fadeOut('slow',function(){
				    $(this).remove();
				});
			});
			
			//  Report 確認鍵
			$('#Confirm').click(function(e) {
				if($('#message-text').val().length>0){
				data = {};
				data.action = "reportAdd";
				data.gro_no = gro_no;
				data.mem_id = '${memVOLogin.mem_id}';
				data.rep_reason = $('#message-text').val();
				$.ajax({
					type : "GET",
					url : "<%=request.getContextPath()%>/front-end/gpt/group.do",
					data : data,
					dataType : "json",
				});
				swal('Success！', '', 'success');
				$('#menu1 [value="'+gro_no+'"]#reportTarget').prop('disabled', true);
				$('#message-text').val('');
				}else{
					swal('Please enter Reason！', '', 'error');
					e.preventdefault();
				}
			});

			function getStr() {
				var intro = document.getElementById("message-text");
				console.log(intro.value)
				str = intro.value;
			}
			
			// kick Out 按鍵
			$('.modal-dialog.modal-dialog-centered.modal-dialog-scrollable').on("click",".btn-sm",function(){
		       
		       data = {};
			   data.action = "kickOut";
			   data.grouplist_no = $(this).val(); 
		       
		       $.ajax({
		        	type : "GET",
					url : "<%=request.getContextPath()%>/front-end/gpt/group.do",
					data : data,
					dataType : "json",
					success: function(resKickOut){
					}
		        });
				$(this).parent().parent().hide('slow');
				var newMemberArray = memberArray-1;
				$(currentMemberNumber).text(newMemberArray);
		    });
			
			//  Member按鈕(我加入的團 menu1)
			$('#menu1 #memberScope').click(function() {
				currentMemberNumber =  "#"+$(this).val();
				
				data = {};
				data.action = "view_My_Member";
				data.gro_no = $(this).val();
				console.log("#menu1 #memberScope = "+$(this).val());
				console.log("#menu1 #memberScope", data)
				$.ajax({
					type : "GET",
					url : "<%=request.getContextPath()%>/front-end/gpt/group.do",
					data : data,
					dataType : "json",
					success: function(resMember){
						showGroupMembers(resMember);
						memberArray = resMember.length;
					}
				});
			});
			
		//  Member按鈕(我的揪團 home)
			$('#home #memberScope').click(function() {
				currentMemberNumber =  "#"+$(this).val();
				
				data = {};
				data.action = "view_My_Member";
				data.gro_no = $(this).val();
				console.log("#home #memberScope = "+$(this).val());
				console.log("#home #memberScope", data)
				$.ajax({
					type : "GET",
					url : "<%=request.getContextPath()%>/front-end/gpt/group.do",
					data : data,
					dataType : "json",
					success: function(resMemberScope){
						showMyGroupMembers(resMemberScope);
						memberArray = resMemberScope.length;
					}
				});
			});
			
			//參加揪團的團員列表
			function showGroupMembers(resMember){
				console.log(resMember.length);
				let memVOLogin = '${memVOLogin.mem_id}';
				$('#listMemberBody').empty();
				$(resMember).each(function(i, item){
					$('#listMemberBody').append(
						"<tr id='kickOutTr'>"+
							"<td>"+
								"<img src='/EA102G4/front-end/mem/headphoto.do?mem_id="+item.mem_id+"' alt='' style='width: 60px; height: 60px;' class='square' id="+item.mem_id+">"+
							"</td>"+
							"<td style='color:black; padding-bottom: 5px; vertical-align: middle;'>"+
								item.mem_name+
							"</td>"+
							"<td style='vertical-align: middle;'>"+
							"<button value='"+item.mem_id+"' style='font-size: 18px;' class='btn btn-primary contact' id='Contact"+i+"'"+((memVOLogin === item.mem_id)?'disabled':'')+">Contact</button>"+
						"</td>"+
						"</tr>"
					);
				});
			}
			
			$('.modal-body.memberlist').on("click","img",function(){
				let imgId = $(this).attr("id");
				window.location.href = "<%=request.getContextPath()%>/front-end/mem/memDetail.jsp?mem_id="+imgId;
				
			});
			
			//個人開團團員列表
			function showMyGroupMembers(resMemberScope){
				console.log(resMemberScope.length);
				$('#listMemberBody').empty();
				if(resMemberScope.length != 0){
					$(resMemberScope).each(function(i, item){
						$('#listMemberBody').append(
							"<tr id='kickOutTr'>"+
								"<td>"+
									"<img src='/EA102G4/front-end/mem/headphoto.do?mem_id="+item.mem_id+"' alt='' style='width: 60px; height: 60px;' class='square'id="+item.mem_id+">"+
								"</td>"+
								"<td style='color:black; padding-bottom: 5px; vertical-align: middle;'>"+
									item.mem_name+
								"</td>"+
								"<td style='vertical-align: middle;'>"+
									"<button value='"+item.grouplist_no+"' class='btn btn-primary btn-sm' id='kick_Out"+i+"'"+">Kick Out</button>"+
								"</td>"+
								"<td style='vertical-align: middle;'>"+
								"<button value='"+item.mem_id+"' style='font-size: 18px;' class='btn btn-primary contact' id='Contact"+i+"'"+">Contact</button>"+
							"</td>"+
							"</tr>"
						);
					});
				}else{
					$('#listMemberBody').append('<tr><td>尚無團員</td></tr>');
				}
			}

			$(document).ready(function() {
				$("#my_activity_target").click(function() {
					$("#my_activity").toggle("show");
				});
			});

			$(document).ready(function() {
				$("#my_join_activity_target").click(function() {
					$("#my_join_activity").toggle("show");
				});
			});

			$(document).ready(function() {
				$("#history_activity_target").click(function() {
					$("#history_activity").toggle("show");
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
				console.log("成功接收訊息!!!!!!!!!");
            var h = '入團通知' || false,
                   s = jsonObj.message,
                   c = $(this).data('color') || '#CD0505',
                   t = 10000;
            triggerToast(h, s, c, t);
            let memberNumber = $('#'+jsonObj.sender).text();
            $('#'+jsonObj.sender).text(parseInt(memberNumber)+1);
            
			}else if("statusCheck" === jsonObj.type){
				console.log("成功接收揪團訊息!!!!!!!!!");
	               var h = '揪團狀態' || false,
	                   s = jsonObj.message,
	                   c = $(this).data('color') || '#CD0505',
	                   t = 300000;
	        triggerToast(h, s, c, t);
	        $('#tr_'+jsonObj.sender+' #groupStatus').text('活動進行中');
	        
			}else if("finalStatus" === jsonObj.type){
				console.log("成功接收揪團訊息!!!!!!!!!");
	               var h = '揪團狀態' || false,
	                   s = jsonObj.message,
	                   c = $(this).data('color') || '#CD0505',
	                   t = 300000;
	        triggerToast(h, s, c, t);
			}
		}
		
		webSocket.onclose = function(event) {
			console.log("Disconnected!");
		};
		
	}
	
	function sendMessage() {
		var message = '${memVOLogin.mem_name}加入你的${groupVO.gro_name}揪團囉!!';
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