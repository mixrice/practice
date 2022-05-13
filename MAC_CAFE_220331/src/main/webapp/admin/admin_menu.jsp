<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>메뉴</title>
</head>
<body>
	<div>
		<a href="${pageContext.request.contextPath }/set.adm">세트</a>
		|
		<a href="${pageContext.request.contextPath }/burger.adm">버거</a>
		|
		<a href="${pageContext.request.contextPath }/drink.adm">음료</a>
		|
		<a href="${pageContext.request.contextPath }/side.adm">사이드</a>
		|
		<a href="${pageContext.request.contextPath }/dessert.adm">디저트</a>
		|
		<a href="${pageContext.request.contextPath }/menuAddForm.adm">메뉴추가</a>
	</div>
</body>
</html>