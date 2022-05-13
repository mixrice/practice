<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>dessertList 뷰페이지 </title>
</head>
<body>
	<section id="listForm">
		<!-- 1. dessert메뉴 목록이 있으면 -->
		<c:if test="${dessertList != null && dessertList.size() > 0 }">
			<table>
				<tr>
				<c:forEach var="dessert" items="${dessertList }" varStatus="status">
					
					<c:if test="${dessert.m_status eq 'n' }">
							<td>
								<img src="${pageContext.request.contextPath}/images/${dessert.image }" alt="이 상품은 품절입니다.">
								<br>
								${dessert.m_name }<br>
								가격 : ${dessert.m_price }원<br>
								<br><br><br>
							</td>
						</c:if>
						
						<c:if test="${dessert.m_status eq 'y' }">
							<td>
								<a href="menuView.kiosk?m_id=${dessert.m_id }"><img src="${pageContext.request.contextPath}/images/${dessert.image }"></a>
								<br>
								${dessert.m_name }<br>
								가격 : ${dessert.m_price }원<br>
								<br><br><br>
							</td>
						</c:if>
					
						<c:if test="${((status.index+1) mod 4) == 0 }">
						</tr>
						<tr>
	
						</c:if>
							
						</c:if>
					
				</c:forEach>
				</tr>
			</table>
		</c:if>
		
		<!-- 2. dessert메뉴 목록이 없으면 -->
		<c:if test="${dessertList == null }">
			<div>등록된 디저트 메뉴가 없습니다.</div>
		</c:if>
		
	</section>
</body>
</html>