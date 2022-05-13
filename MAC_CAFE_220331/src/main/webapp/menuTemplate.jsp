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
<body>

<div>
	<jsp:include page="userHeader.jsp"/>
</div>
<section>
	<table>
		<tr>
			<td>
				<!--  jsp:include 액션태그 일때는 상대 경로 : "${pageContext.request.contextPath}" 사용 X-->
				<jsp:include page="kiosk/kiosk_menu.jsp"/>
			</td>
		</tr>
		<c:if test="${showMenu ne null }">
			<tr>
				<td>
					<jsp:include page="${showMenu }" />
				</td>
			</tr>
		</c:if>
		
		<tr>
			<td><!-- 장바구니 목록 : 처음 [주문하기] 요청하면 '장바구니가 비어있음'이 출력됨 -->
				<jsp:include page="kiosk/menuCartList.jsp"/>
			</td>
		</tr>
	</table>
</section>


<div>
	<jsp:include page="userFooter.jsp"/>
</div>

</body>
</html>