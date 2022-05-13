<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>Insert title here</title>
</head>
<body>

<header id="header">
	<div class="header1">
		<a href="<%=request.getContextPath()%>/adminMain.jsp">
			<img src="images/logo/maclogo.png" width="50%">
		</a>
	</div>
	
	<div class="header2">
		<a href="${pageContext.request.contextPath}/dayOrderManage.adm">실시간주문관리</a>
		&nbsp;&nbsp;&nbsp;
		<a href="${pageContext.request.contextPath}/totalOrderManage.adm">전체주문관리</a>
	   	&nbsp;&nbsp;&nbsp;	
	   	<a href="${pageContext.request.contextPath}/calenderSalesManage.adm">매출관리</a>
	   	&nbsp;&nbsp;&nbsp;
	   	<a href="${pageContext.request.contextPath}/menuManage.adm">메뉴관리</a>
	   	&nbsp;&nbsp;&nbsp;
	   	<a href="${pageContext.request.contextPath}/service.adm">고객센터</a>
	
	
	<br>
	<!-- 로그인에 성공하면 세션영역에 "a_id값과 a_name값"을 속성으로 공유 -->
		<div class="header3">
			<c:choose>
				<c:when test="${a_id eq null}">
					<a href="${pageContext.request.contextPath}/adminLogin.adm">로그인</a> 
					|
					<a href="${pageContext.request.contextPath}/adminJoin.adm">회원가입</a>  
				</c:when>
				<c:otherwise>
					${a_name} 관리자님 환영합니다.<br>
					<a href="${pageContext.request.contextPath}/adminLogout.adm">로그아웃</a>  
					|
					<a href="${pageContext.request.contextPath}/adminView.adm">회원정보관리</a>  
	
				</c:otherwise>	
			</c:choose>
		</div>
	</div>
	<br><br><br>
	<hr width="100%">
</header>

</body>
</html>