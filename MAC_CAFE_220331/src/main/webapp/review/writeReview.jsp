<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>리뷰 작성하기</title>
</head>
<body>

<form action="reviewWrite.re" method="post">
	<div>
		<input type="hidden" name="m_id" id="m_id" value="${m_id }">
			<c:if test="${u_name ne null }">
				<b>${u_name }님의 평점</b>
			</c:if>
			<select name="rating" style="width:20%; height:4%;">
				<option value="0" selected="selected">평점선택</option>
				<option value="5" >★★★★★</option>
				<option value="4" >★★★★</option>
				<option value="3" >★★★</option>
				<option value="2" >★★</option>
				<option value="1" >★</option>
			</select>
	</div>
	
	<!-- 리뷰 작성하기 -->
	<table>
		<tr>
			<th>한줄평 입력</th>
			<td>
				<textarea name="text" rows="3" cols="50" placeholder="한줄평을 입력해주세요." style="text-align:left;"></textarea>
			</td>
			<th>
				<input type="submit" value="등록">
			</th>
		</tr>
	</table>
</form>

<hr>

</body>
</html>