<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">

<style type="text/css">
.menu input {
	display:none; /* 라디오 버튼 숨겨서 보이지 않도록 한 후 사용자가 1개만 선택할 수 있도록 */
}
</style>

<title>주문 메뉴</title>
</head>
<body>
	<div class="menu">
		<input type="radio" name="tabs" checked="checked">
		<a href="${pageContext.request.contextPath}/set.kiosk">세트</a>
		|
		<input type="radio" name="tabs">
		<a href="${pageContext.request.contextPath}/burger.kiosk">버거</a>
		|
		<input type="radio" name="tabs">
		<a href="${pageContext.request.contextPath}/drink.kiosk">음료</a>
		|
		<input type="radio" name="tabs">
		<a href="${pageContext.request.contextPath}/side.kiosk">사이드</a>
		|
		<input type="radio" name="tabs">
		<a href="${pageContext.request.contextPath}/dessert.kiosk">디저트</a>
		
		
	</div>
</body>
</html>