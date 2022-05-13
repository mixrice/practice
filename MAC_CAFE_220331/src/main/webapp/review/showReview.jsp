<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><!-- "fn:trim()" 함수를 사용하기 위해 작성 -->
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>작성된 리뷰보기</title>
</head>
<body>

<section>
	
	<c:choose>
		<c:when test="${reviewList ne null }"> <!-- 작성된 리뷰가 있으면 -->
			<table>
				<tr>
					<th>작성자</th>
					<th>평점</th>
					<th>한줄평</th>
					<th>수정/삭제</th>
				</tr>
				<tr>
					<c:forEach var="review" items="${reviewList }" varStatus="status">
						<td>${review.u_id }</td>
					
					<td>
						<c:if test="${review.rating == 5 }">★★★★★</c:if>
						<c:if test="${review.rating == 4 }">★★★★</c:if>
						<c:if test="${review.rating == 3 }">★★★</c:if>
						<c:if test="${review.rating == 2 }">★★</c:if>
						<c:if test="${review.rating == 1 }">★</c:if>
					</td>
					<td><!-- 한줄평 적기는 null을 허용하기 때문에 -->
						<!-- "fn:trim()"  : text안에 공백 제거 -->
						<c:choose>
							<c:when test="${fn:trim(review.text) eq '' }">
								<c:if test="${review.rating == 5 }">너무 맛있어요 !</c:if>
								<c:if test="${review.rating == 4 }">맛있어요 !</c:if>
								<c:if test="${review.rating == 3 }">잘먹었습니다 !</c:if>
								<c:if test="${review.rating == 2 }">조금 아쉬워요..</c:if>
								<c:if test="${review.rating == 1 }">슬퍼요...</c:if>
							</c:when>
							<c:otherwise>
								${review.text }
							</c:otherwise>
						</c:choose>
					</td>
					<th><!-- 보안에 노출되면 안되는 정보는 이렇게 전송하면 안됨 -->
						<a href="reviewUpdateForm.re?review_num=${review.review_num }&u_id=${review.u_id} &m_id=${review.m_id}">[수정]</a>
						&nbsp;&nbsp;&nbsp;
						<a href="reviewDelete.re?review_num=${review.review_num }&u_id=${review.u_id} &m_id=${review.m_id}">[삭제]</a>						
					</th>
					
					<c:if test="${((status.count) mod 1) eq 0 }">
						</tr>
						<tr>
					</c:if>
					</c:forEach>
				</tr>
			</table>
		</c:when>
		
		<c:otherwise>
			작성된 리뷰가 없습니다.
		</c:otherwise>
	</c:choose>
</section>

</body>
</html>