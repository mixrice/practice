<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>


<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet" href="css/style.css">
<title>맥 카페</title>
</head>
<body class="main">

	<!--  jsp:include 액션태그 일때는 상대 경로 : "${pageContext.request.contextPath}" 사용 X-->
	<jsp:include page="userHeader.jsp"/>
	<!-- <section class="section" id="section" style="margin-top: 100px;"> -->
	<section class="section" id="section" style="margin-top: 30px;">
	    <h3>움직이는 배너나 홍보 이미지</h3>
	    <!-- ----------------------------------------------------------- -->
	
		<input type="radio" name="slide" id="slide01" checked>
		<input type="radio" name="slide" id="slide02">
		<input type="radio" name="slide" id="slide03">
		
		<div class="slidewrap">
			
			<ul class="slidelist">
				<!-- 슬라이드 영역 -->
				<li class="slideitem">
					<a>
						<div class="textbox"></div>
						<a href="<%=request.getContextPath()%>/menuView.kiosk?m_id=Chicken Burger">
							<img src="<%=request.getContextPath()%>/images/user_img/user_main/burger/Chicken Burger.png">
						</a>
					</a>
				</li>
				<li class="slideitem">
					<a>
						
						<div class="textbox"></div>
						<img src="<%=request.getContextPath()%>/images/user_img/user_main/drink/Strawberry Chiller.png" >
					</a>
				</li>
				<li class="slideitem">
					<a>					
						<div class="textbox"></div>
						<img src="<%=request.getContextPath()%>/images/user_img/user_main/drink/Vanilla Latte.png">
					</a>
				</li class="slideitem">
	
				<!-- 좌,우 슬라이드 버튼 -->
				<div class="slide-control">
					<div>
						<label for="slide03" class="left"></label>
						<label for="slide02" class="right"></label>
					</div>
					<div>
						<label for="slide01" class="left"></label>
						<label for="slide03" class="right"></label>
					</div>
					<div>
						<label for="slide02" class="left"></label>
						<label for="slide01" class="right"></label>
					</div>
				</div>
	
			</ul>
			<!-- 페이징 -->
			<ul class="slide-pagelist">
				<li><label for="slide01"></label></li>
				<li><label for="slide02"></label></li>
				<li><label for="slide03"></label></li>
			</ul>
		</div>
	    
	   <!-- ----------------------------------------------------------- -->  
	    
	    
	    
	    
	    
		<h3>메인부분에 올라갈 상품 이미지나 글 입력</h3>
		
		
	    <a href="<%=request.getContextPath()%>/menuView.kiosk?m_id=1955">
	    	<img src="<%=request.getContextPath()%>/images/user_img/user_main/burger/1955.png">
	    </a>
		
		
		<a href="${pageContext.request.contextPath}/menuView.kiosk?m_id=Big Mac">
			<img src="${pageContext.request.contextPath}/images/user_img/user_main/burger/Big Mac.png">
		</a>
		
	</section>
	<jsp:include page="userFooter.jsp"/>
 
</body>
</html>