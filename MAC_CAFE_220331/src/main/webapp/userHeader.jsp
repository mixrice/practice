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
		<a href="<%=request.getContextPath()%>/userMain.jsp">
			<img src="images/logo/maclogo.png" width="50%">
		</a>
	</div>
	
	<div class="header2">
		<a href="#">MENU</a>
		&nbsp;&nbsp;&nbsp;
		<a href="${pageContext.request.contextPath}/menu.kiosk">주문하기</a>
	   	&nbsp;&nbsp;&nbsp;	
	   	<a href="#">이벤트</a>
	   	&nbsp;&nbsp;&nbsp;
	   	<a href="#">가맹점문의</a>
	
	
	<br>
	
		<div class="header3">
			<c:choose>
				<c:when test="${u_id eq null}">
					<a href="${pageContext.request.contextPath}/userLogin.usr">로그인</a> 
					|
					<a href="${pageContext.request.contextPath}/userJoin.usr">회원가입</a>  
				</c:when>
				<c:otherwise>
					${u_name}님 환영합니다.<br>
					<a href="${pageContext.request.contextPath}/userLogout.usr">로그아웃</a> 
					|
					<a href="${pageContext.request.contextPath}/myOrder.kiosk">주문내역보기</a> 
					|
					<a href="${pageContext.request.contextPath}/userView.usr">회원정보관리</a>  
					|
					<a href="#">고객문의</a>
				</c:otherwise>	
			</c:choose>
		</div>
	</div>
	<br><br><br>
	<hr width="100%">
</header>

</body>
</html>