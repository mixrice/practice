<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>리뷰 수정</title>
</head>
<body>

<form action="reviewUpdate.re" method="post">
	<div>
		<input type="hidden" name="review_num" value="${reviewInfo.review_num}">
		<input type="hidden" name="u_id" value="${reviewInfo.u_id}">
		<input type="hidden" name="m_id" value="${reviewInfo.m_id}">
		
		<c:if test="${u_name ne null }">
		<b>${u_name }님의 평점</b>
		</c:if>
		
		<c:if test="${reviewInfo.rating==5 }">
			<select name="rating" style="width: 20%; height: 4%;">
				<option value="0" >평점선택</option>
				<option value="5" selected="selected" >★★★★★</option>
				<option value="4" >★★★★</option>
				<option value="3" >★★★</option>
				<option value="2" >★★</option>
				<option value="1" >★</option>
			</select>
		</c:if>
		<c:if test="${reviewInfo.rating==4 }">
			<select name="rating" style="width: 20%; height: 4%;">
				<option value="0" >평점선택</option>
				<option value="5" >★★★★★</option>
				<option value="4" selected="selected">★★★★</option>
				<option value="3" >★★★</option>
				<option value="2" >★★</option>
				<option value="1" >★</option>
			</select>
		</c:if>
		<c:if test="${reviewInfo.rating==3 }">
			<select name="rating" style="width: 20%; height: 4%;">
				<option value="0" >평점선택</option>
				<option value="5" >★★★★★</option>
				<option value="4" >★★★★</option>
				<option value="3" selected="selected">★★★</option>
				<option value="2" >★★</option>
				<option value="1" >★</option>
			</select>
		</c:if>
		<c:if test="${reviewInfo.rating==2 }">
			<select name="rating" style="width: 20%; height: 4%;">
				<option value="0" >평점선택</option>
				<option value="5" >★★★★★</option>
				<option value="4" >★★★★</option>
				<option value="3" >★★★</option>
				<option value="2" selected="selected">★★</option>
				<option value="1" >★</option>
			</select>
		</c:if>
		<c:if test="${reviewInfo.rating==1 }">
			<select name="rating" style="width: 20%; height: 4%;">
				<option value="0" >평점선택</option>
				<option value="5" >★★★★★</option>
				<option value="4" >★★★★</option>
				<option value="3" >★★★</option>
				<option value="2" >★★</option>
				<option value="1" selected="selected">★</option>
			</select>
		</c:if>

	</div>
	
	<!-- 작성한 리뷰 -->
	<table>
		<tr>
			<th>한줄평 입력</th>
			<td>
			<!-- [과제] 평점만 선택했을때 기본 텍스트가 출력, 평점을 선택하고 리뷰를 작성했으면 작성된 리뷰가 출력되도록 함 (반드시 입력한 리뷰는 첫줄 제일 처음에 위치)-->
			<c:choose>
				<c:when test="${fn:trim(reviewInfo.text) eq '' }">
					<c:if test="${review.rating == 5 }">너무 맛있어요 !</c:if>
					<c:if test="${review.rating == 4 }">맛있어요 !</c:if>
					<c:if test="${review.rating == 3 }">잘먹었습니다 !</c:if>
					<c:if test="${review.rating == 2 }">조금 아쉬워요..</c:if>
					<c:if test="${review.rating == 1 }">슬퍼요...</c:if>
					<textarea name="text" rows="3" cols="50" placeholder="한줄평을 입력해주세요." style="text-align:left;"></textarea>
				</c:when>

				<c:otherwise>
					<textarea name="text" rows="3" cols="50" placeholder="한줄평을 입력해주세요." style="text-align:left;">${reviewInfo.text}</textarea>
				</c:otherwise>
			</c:choose>
	
			</td>
			<th>
				<input type="submit" value="수정">
			</th>
		</tr>
	</table>
</form>

<hr>

</body>
</html>