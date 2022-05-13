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
	<div>
		<jsp:include page="admin_menu.jsp" />
	</div>
	
	<c:if test="${admin_showMenu ne null }">
		<div>
			<jsp:include page="${admin_showMenu }" />
		</div>
	</c:if>
	
	<c:if test="${admin_showMenu eq null }">
		<div>
			<h1>[admin_showMenu] 값이 없습니다.</h1>
		</div>
	</c:if>
	
</body>
</html>

<%
System.out.println("admin_template.jsp");
%>