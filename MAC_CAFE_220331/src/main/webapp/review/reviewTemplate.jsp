<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>리뷰</title>
</head>
<body>

<!-- switch문 -->
<c:choose>
	<c:when test="${m_id ne '' }">
		<table>
			<tr><!-- 사용자가 리뷰 작성 -->
				<td>
					<jsp:include page="writeReview.jsp" />
				</td>
			</tr>
			<tr>
				<td><!-- 작성된 리뷰 보기 -->
					<jsp:include page="${showReview }" />
				</td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
		&nbsp;
	</c:otherwise>
</c:choose>

</body>
</html>