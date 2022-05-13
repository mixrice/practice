<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>맥카페</title>
</head>
<body>

<div>
	<!--  jsp:include 액션태그 일때는 상대 경로 : "${pageContext.request.contextPath}" 사용 X-->
	<jsp:include page="adminHeader.jsp" />
</div>	

	<section id="section">
		<c:if test="${showAdmin ne null }">
		
			<div>
				<jsp:include page="${showAdmin }" />
				<c:choose>
					<c:when test="${a_id eq null and showAdmin eq null }">
						<jsp:include page="admin/adminLoginForm.jsp" />
					</c:when>
					<c:when test="${a_id ne null and showAdmin ne null }">
						<div align="center">[${sessionScope.a_name }]사장님 오늘하루도 화이팅하세요!</div>
					</c:when>
				</c:choose>
			</div>
			
		</c:if>
	</section>
	
<div>		
	<jsp:include page="adminFooter.jsp" />
</div>


</body>
</html>

<%
System.out.println("adminMain.jsp");
%>