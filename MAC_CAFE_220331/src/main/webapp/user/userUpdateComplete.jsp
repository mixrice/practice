<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원수정 완료</title>
</head>
<body>
	<div>
		<%-- <h3>[${u_name }]회원님의 정보수정이 완료되었습니다.</h3> --%><!-- request영역에 있는 내용 출력 -->
		<h3>[${sessionScope.u_name }]회원님의 정보수정이 완료되었습니다.</h3><!-- Session영역에 있는 내용 출력 -->
		<hr>
		<a href="userMain.jsp">[확인]</a>
		
	</div>
</body>
</html>