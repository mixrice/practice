<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>사용자의 주문내역</title>
</head>

<body>

<div>
	<jsp:include page="userHeader.jsp" />
</div>

<section id="section">
	<c:if test="${sessionScope.menuList !=  null &&  sessionScope.menuList.size() > 0}">
		<h2>${sessionScope.u_name }님의 주문내역</h2>
		<form action="post">
			<table>
				<tr>
					<th>번호</th>
					<th>메뉴명</th>
					<th>가격</th>
					<th>수량</th>
				</tr>
				
				<c:forEach var="menu" items="${sessionScope.menuList }" varStatus="status">
					<tr>
						<td>${status.count }</td>
						<td>${menu.m_name }</td>
						<td>${menu.m_price }</td>
						<td>${menu.quantity }</td>
					</tr>
				</c:forEach>
				
				<tr>
					<td colspan="4">합계 금액 : ${sessionScope.totalMoney }원</td>
				</tr>
				<tr>
					<td colspan="4">할인 금액 : - ${sessionScope.totalMoney - sessionScope.saleTotalMoney }원</td>
				</tr>
				<tr>
					<td colspan="4">결재 금액 : ${sessionScope.saleTotalMoney }원</td>
				</tr>
				<tr>
					<td colspan="4">
						<jsp:include page="user/orderStatus.jsp" />
					</td>
				</tr>
				
			</table>
		</form>
	</c:if>
	
	<c:if test="${sessionScope.menuList ==  null}">
		<h2>${sessionScope.u_name }님의 주문내역이 없습니다.</h2>
	</c:if>
</section>

<div>
	<jsp:include page="userFooter.jsp" />
</div>

</body>
</html>