<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.group.model.*"%>
<%@ page import="com.mem.model.*"%>

<% GroupVO groupVO = (GroupVO) request.getAttribute("groupVO"); %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>oneGroupDetail</title>

<%@ include file="include/css_link"%>
<script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBw1IB_wF8J22LKiqPxdO8wbfWgFIpoDTo&libraries=places"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.6.10/vue.min.js"></script>
<style type="text/css">
/* Always set the map height explicitly to define the size of the div
       * element that contains the map. */

/* Optional: Makes the sample page fill the window. */
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
	
}

#map {
	height: 500px;
	width: 500px;
	border: 2px solid #333;
	background: #CCC;
}

#right-panel select, #right-panel input {
	font-size: 15px;
}

#right-panel select {
	width: 100%;
}

#right-panel i {
	font-size: 12px;
}

#right-panel {
	line-height: 30px;
	padding-left: 10px;
	height: 500px;
	width: 390px;
	overflow: auto;
	display: inline-block;
	border: 2px solid #333;
}
label{
	color:black;
	font-family:microsoft JhengHei;
}
</style>

</head>
<body>

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

	<!-- navbar start -->
	<div class="zarxio-navbar">
		<nav class="navbar navbar-area navbar-expand-lg black-bg nav-style-02">
			<div class="container nav-container">
				<div class="responsive-mobile-menu">
					<button class="navbar-toggler" type="button" data-toggle="collapse"
						data-target="#billatrail_main_menu" aria-expanded="false"
						aria-label="Toggle navigation">
						<span class="navbar-toggler-icon"></span> <span class="bar1"></span>
						<span class="bar2"></span> <span class="bar3"></span>
					</button>
				</div>
				<div class="logo">
					<a href="<%=request.getContextPath()%>/front-end/index.jsp"> <img src="assets/img/logo.png" alt="logo"></a>
				</div>
				<div class="collapse navbar-collapse" id="billatrail_main_menu">
					<ul class="navbar-nav menu-open">
						<li class="menu-item-has-children"><a href="#">Home</a>
							<ul class="sub-menu">
								<li><a href="index.html">Home 1</a></li>
								<li><a href="index-2.html">Home 2</a></li>
								<li><a href="index-3.html">Home 3</a></li>
							</ul></li>

						<li class="hidden-md"><a href="about.html">About</a></li>
						<li class="menu-item-has-children"><a href="#">Classes</a>
							<ul class="sub-menu">
								<li><a href="classes.html">Class</a></li>
								<li><a href="class-details.html">Class Details</a></li>
							</ul></li>
						<li class="menu-item-has-children"><a href="#">Trainer</a>
							<ul class="sub-menu">
								<li><a href="trainer.html">Trainer</a></li>
								<li><a href="trainer-details.html">Trainer Details</a></li>
							</ul></li>
						<li class="menu-item-has-children"><a href="#">Shop</a>
							<ul class="sub-menu">
								<li><a href="shop.html">Shop</a></li>
								<li><a href="product-details.html">Product Details</a></li>
								<li><a href="shopping-cart.html">Shopping Cart</a></li>
								<li><a href="payment.html">Payment</a></li>
								<li><a href="checkout.html">Checkout</a></li>
							</ul></li>
						<li class="menu-item-has-children"><a
							href="#">Blog</a>
							<ul class="sub-menu">
								<li><a href="blog.html">Blog</a></li>
								<li><a href="blog-details.html">Blog Details</a></li>
							</ul></li>
						
						<li class="menu-item-has-children current-menu-item"><a
							href="#">Activity</a>
							<ul class="sub-menu">
								<li><a href="<%=request.getContextPath()%>/front-end/gpt/listAllGroup.jsp">Activity</a></li>
								<li><a href="blog-details.html">My Activity</a></li>
								<li><a href="blog-details.html">Create Activity</a></li>
							</ul></li>
							
						<li class="menu-item-has-children"><a href="#">Pages</a>
							<ul class="sub-menu">
								<li class="d-block d-xl-none"><a href="about.html">About
										Us</a></li>
								<li><a href="service.html">Service</a></li>
								<li><a href="signin.html">Sign In</a></li>
								<li><a href="signup.html">Sign Up</a></li>
								<li><a href="404.html">404</a></li>
								<li><a href="coming-soon.html">Coming Soon</a></li>
							</ul></li>
						<li><a href="contact.html">Contact</a></li>
					</ul>
				</div>
				<div class="nav-right-part">
					<ul>
						<li class="search" id="search"><a href="#"><i
								class="fa fa-search"></i></a></li>
						<li>
							<div class="hamberger-area d-none d-lg-block">
								<div class="menu-toggle hamburger hamburger--squeeze is-active">
									<div class="hamburger-box">
										<div class="hamburger-inner"></div>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</nav>
	</div>
	<!-- navbar end -->

	<!-- breadcrumb area -->
	<div class="breadcrumb-style-1"
		style="background-image: url(assets/img/bg/blog-details.png);">
		<div class="breadcrumb-inner">
			<h1 class="page-title">Blog Details</h1>
			<ul class="page-list margin-bottom-4">
				<li><a href="index.html">Home</a></li>
				<li><a href="#">Blog Details</a></li>
			</ul>
		</div>
	</div>
	<!-- breadcrumb area end -->

	<!-- home blog start -->
	<div class="product-details-tab padding-top-100 padding-bottom-135">     <!--------- bottom想改成200不知道為什麼動style動不了 --------->
        <div class="container">
            <div class="row">
                <div class="col-lg-7">
                <jsp:useBean id="grouptypeSvc" scope="page" class="com.grouptype.model.GroupTypeService" />
                <FORM METHOD="post" ACTION="${pageContext.request.contextPath}/product/product.do" name="form1" enctype="multipart/form-data" id="form1">
                     <div class="content-part"> 
                     <!---------------------------------填寫購物資料------------------------------------>
                       <div class="row">
                       
                       <div class="col-lg-12"> 
                        <h2>填寫揪團資料:</h2>
						</div>
						
						<div class="col-lg-7">
                        <div class="form-group">
                          <label for="usr">揪團名稱:</label>
                          <input type="text" class="form-control" id="gro_name" name="gro_name" placeholder="請輸入揪團名稱" maxlength="10" value="<%=(groupVO == null) ? "" : groupVO.getGro_name()%>">
                        </div>
                        </div>
                        
                        <div class="col-lg-5">
                         	<div class="form-group">
							  <label for="sel1">揪團種類:</label>
							  <select class="form-control" id="show" name="type_no">
							    <c:forEach var="grouptypeVO" items="${grouptypeSvc.all}">
									<option value="${grouptypeVO.type_no}">${grouptypeVO.type_name}
								</c:forEach>
							  </select>
							</div>
                        </div>
                        
                        <div class="col-lg-6">
                        	<div class="form-group">
                         		<label for="gro_start">開始時間:</label> 
					<input type="text" class="form-control" name="gro_start" id="gro_start" value="<%=(groupVO == null) ? "" : groupVO.getGro_start()%>">
                       		</div>
                        </div>
                        
                       
                        <div class="col-lg-6">
                        	<div class="form-group">
                         		<label for="gro_end">結束時間:</label> 
					<input type="text" class="form-control" name="gro_end" id="gro_end" value="<%=(groupVO == null) ? "" : groupVO.getGro_end()%>">
                       		</div>
                        </div>
                        
                        <div class="col-lg-12">
                        <div class="form-group">
                        	<label for="gro_addr">活動地址:</label> 
					<input type="text" class="form-control" name="gro_addr" placeholder="請輸入活動地點地址" id="gro_addr" value="<%=(groupVO == null) ? "" : groupVO.getGro_addr()%>">
                        </div>
                        </div>
                        
                          <div class="col-lg-6">
                        	<div class="form-group">
                        	<label for="gro_min">最小人數:</label> 
					<input type="text" class="form-control" name="gro_min" placeholder="請輸入最小人數" value="<%=(groupVO == null) ? "" : groupVO.getGro_min()%>">
                       		</div>
                        </div>
                        
                       
                        <div class="col-lg-6">
                        	<div class="form-group">
                        	<label for="gro_max">最大人數:</label> 
					<input type="text" class="form-control" name="gro_max" placeholder="請輸入最大人數" value="<%=(groupVO == null) ? "" : groupVO.getGro_max()%>">
                       		</div>
                        </div>
                        
                        <div class="col-lg-12">
                        <div class="form-group">
                        	<label for="cate">揪團簡介:</label>
                       		<textarea id="cate" class="form-control" rows="7" name="pDetail"></textarea>
                        </div>
                        </div>
                        
                         <div class="input-group mb-1" style="padding: 30px;">
						  	<div class="custom-file">
						    	<input type="file" class="custom-file-input" id="gro_pic" name="gro_pic" value="gro_pic">
						    	<label class="custom-file-label" for="inputGroupFile02" style="font-size:14px">選擇圖片</label>
						  	</div>
						 </div>
						  
						<div id="preview">
					<img src="img/add.png" id="gro_pic_preview" width=400px height=274px>
				</div>
                        
                        <div class="btn-wrapper" style="text-align:right;padding-top:5px;">
                            <a href="#" class="btn btn-element btn-medium-size btn-main-color btn-rounded" id="productSubmit">確認上架</a>
                        </div>
                        <input type="hidden" name="action" value="insert">
                        <input type="hidden" name="action" value="getOneForInsertPhoto">
                         <input type="hidden" name="productNo" value="${productVO.productNo}">
                    
                    <!---------------------------------填寫購物資料------------------------------------>
                    </FORM>
                    </div>
                </div>
                </div>
                
                <div class="col-lg-5"> <!-- 圖片區域上 -->
                
		<!-- 搜尋框 -->
			<div class="col google-map">
				<h2>Search：</h2>
				<div class="form-group">
					<input type="text" class="form-control" ref="site" v-model="site">
				</div>
			</div>
		<!-- 放google map的div -->
			<div class="col google-map">
				<h2>Google Map：</h2>
				<div id="map" class="embed-responsive embed-responsive-16by9"></div>
			</div>
                
           
                </div>
        </div>
    </div>
    </div>

	<!-- brand-area start -->
	<div class="brand-area margin-top-85">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="brand-slider">
						<div class="brant-item">
							<img src="assets/img/brand/brand1.png" alt="brand">
						</div>
						<div class="brant-item">
							<img src="assets/img/brand/brand2.png" alt="brand">
						</div>
						<div class="brant-item">
							<img src="assets/img/brand/brand3.png" alt="brand">
						</div>
						<div class="brant-item">
							<img src="assets/img/brand/brand4.png" alt="brand">
						</div>
						<div class="brant-item">
							<img src="assets/img/brand/brand5.png" alt="brand">
						</div>
						<div class="brant-item">
							<img src="assets/img/brand/brand1.png" alt="brand">
						</div>
						<div class="brant-item">
							<img src="assets/img/brand/brand2.png" alt="brand">
						</div>
						<div class="brant-item">
							<img src="assets/img/brand/brand3.png" alt="brand">
						</div>
						<div class="brant-item">
							<img src="assets/img/brand/brand4.png" alt="brand">
						</div>
						<div class="brant-item">
							<img src="assets/img/brand/brand5.png" alt="brand">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- brand-area end -->

	<!-- footer area start -->
	<footer class="footer-area footer-style-2 footer-bg padding-top-100">
		<div class="footer-top padding-top-60 padding-bottom-0">
			<div class="container">
				<div class="row">
					<div class="col-lg-12">
						<div class="footer-widget widget">
							<div class="subscribe-form subscribe-form-style2">
								<div class="input-group margin-top-15 margin-bottom-100">
									<input type="text" class="form-control"
										placeholder="Enter your email address"> <span
										class="input-group-btn">
										<button class="btn btn-default submit-btn" type="button">Subscribe</button>
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-5 col-12">
						<div class="footer-widget widget widget_nav_menu">
							<ul>
								<li><a href="#">Home</a></li>
								<li><a href="#">About Us</a></li>
								<li><a href="#">Service</a></li>
								<li><a href="#">Classes</a></li>
							</ul>
						</div>
					</div>
					<div class="col-lg-2 col-12">
						<div class="footer-widget widget">
							<div class="about_us_widget">
								<a href="index.html" class="footer-logo"> <img
									src="assets/img/footer-logo.png" alt="footer logo">
								</a>
							</div>
						</div>
					</div>
					<div class="col-lg-5 col-12">
						<div class="footer-widget widget widget_nav_menu">
							<ul>
								<li><a href="#">Trainer</a></li>
								<li><a href="#">Shop</a></li>
								<li><a href="#">Blog</a></li>
								<li><a href="#">Contact Us</a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<div class="copyright-area-inner">
							c Zarxio 2019 All rights reserved. Powered with <i
								class="fa fa-heart"></i> by <a href="https://codingeek.net/"
								target="_blank">Codingeek</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</footer>
	<!-- footer area end -->

	<!-- back to top area start -->
	<div class="back-to-top">
		<span class="back-top"><i class="fa fa-angle-up"></i></span>
	</div>
	<!-- back to top area end -->
	

<%@ include file="include/js_src"%>
<!-- =========================================以下為  gro_start 之相關設定========================================== -->
<%
	java.sql.Date gro_start = null;
	try {
		gro_start = groupVO.getGro_start();
	} catch (Exception e) {
		gro_start = new java.sql.Date(System.currentTimeMillis());
	}
	java.sql.Date gro_end = null;
	try {
		gro_end = groupVO.getGro_end();
	} catch (Exception e) {
		gro_end = new java.sql.Date(System.currentTimeMillis());
	}
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#gro_start').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=gro_start%>',// value:   new Date(),
	//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
	//startDate:	            '2017/07/10',  // 起始日
		   minDate: '<%=gro_start%>', // 去除今日(不含)之前
	//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
	});
        $.datetimepicker.setLocale('zh');
        $('#gro_end').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=gro_end%>',// value:   new Date(),
	//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
	//startDate:	            '2017/07/10',  // 起始日
		   minDate: '<%=gro_end%>', // 去除今日(不含)之前
	//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
	});
</script>

 <script>
$.datetimepicker.setLocale('zh'); // kr ko ja en
$(function(){
  $('#gro_end').datetimepicker({
   format:'Y-m-d',
   onShow:function(){
    this.setOptions({
     minDate:$('#gro_start').val()?$('#gro_start').val():false
    })
   },
   timepicker:false,
   value: '<%= gro_end %>'
  });
});
</script>

<script>
$(document).ready(function(){
	  $("#gro_pic").change(function(){
	        readURL(this);
	    });
	  function readURL(input) {
		    if (input.files && input.files[0]) {
		        var reader = new FileReader();

		        reader.onload = function (e) {
		            $('#gro_pic_preview').attr('src', e.target.result).fadeIn('slow');
		        }
		        reader.readAsDataURL(input.files[0]);
		    }
		}
});
</script>



<script>
        const googleMap = new Vue({
            el: '#app',
            data: {
                map: null,
                autocomplete: null,
                site: '', 
                place: null 
            },
            methods: {
                initMap() {

                    let location = {
                        lat: 24.9680014,
                        lng: 121.1900142
                    };

                    this.map = new google.maps.Map(document.getElementById('map'), {
                        center: location,
                        zoom: 16
                    });
                },
                
                siteAuto() {
                    let options = {
                        componentRestrictions: { country: 'tw' } 
                    };
                    this.autocomplete = new google.maps.places.Autocomplete(this.$refs.site, options);
                    this.autocomplete.addListener('place_changed', () => {

                        this.place = this.autocomplete.getPlace();

                        var str = "";
                        for (i in ((this.place.address_components).reverse())) {
                            str += this.place.address_components[i].long_name;
                        }
                        $("#gro_addr").val(str);
                        console.log(str);

                        if (this.place.geometry) {
                            let searchCenter = this.place.geometry.location;
                            this.map.panTo(searchCenter); 
								
                            console.log(searchCenter.lat());
                            console.log(searchCenter.lng());
                            
                            let marker = new google.maps.Marker({
                                position: searchCenter,
                                map: this.map,
                                animation: google.maps.Animation.BOUNCE,
                                draggable: true

                            });
                            
                            

                            // info window
                            let infowindow = new google.maps.InfoWindow({
                                content: this.place.formatted_address

                            });
                            infowindow.open(this.map, marker);
                        }
                    });
                }
            },
            mounted() {
                window.addEventListener('load', () => {

                    this.initMap();
                    this.siteAuto();
                });
            }
        })
        </script>

</body>
</html>