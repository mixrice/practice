<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>[관리자모드] 메뉴 수정 폼</title>
</head>
<body>

<section id="updateForm">

	<header><h2>맥카페 메뉴 수정</h2></header>
	<!-- 반드시 method="post"  파일을 전송하므로 enctype="multipart/form-data" 작성해줘야함-->
	<form action="menuUpdate.adm" name="updateForm" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<th colspan="2">메뉴 수정</th>
			</tr>
			<tr>
				<th class="td_left">매뉴 ID</th>
				<td class="td_right"> <!-- 장바구니에서 메뉴ID로 구분하여 사용 -->
					<input type="text" name="m_id" id="m_id" value="${menu_update.m_id }" required="required">
				<td>
			</tr>
			<tr>
				<th class="td_left">매뉴 카테고리</th>
				<td class="td_right"> <!-- 반드시 선택하도록 처리 -->
					<c:if test="${menu_update.category ne null && menu_update.category eq 'set' }">
						<input type="radio" name="category" value="set" checked="checked"> 세트
						<input type="radio" name="category" value="burger"> 버거
						<input type="radio" name="category" value="drink"> 음료
						<input type="radio" name="category" value="side"> 사이드
						<input type="radio" name="category" value="dessert"> 디저트
					</c:if>
					<c:if test="${menu_update.category ne null && menu_update.category eq 'burger' }">
						<input type="radio" name="category" value="set" > 세트
						<input type="radio" name="category" value="burger" checked="checked"> 버거
						<input type="radio" name="category" value="drink"> 음료
						<input type="radio" name="category" value="side"> 사이드
						<input type="radio" name="category" value="dessert"> 디저트
					</c:if>
					<c:if test="${menu_update.category ne null && menu_update.category eq 'drink' }">
						<input type="radio" name="category" value="set"> 세트
						<input type="radio" name="category" value="burger"> 버거
						<input type="radio" name="category" value="drink" checked="checked"> 음료
						<input type="radio" name="category" value="side"> 사이드
						<input type="radio" name="category" value="dessert"> 디저트
					</c:if>
					<c:if test="${menu_update.category ne null && menu_update.category eq 'side' }">
						<input type="radio" name="category" value="set"> 세트
						<input type="radio" name="category" value="burger"> 버거
						<input type="radio" name="category" value="drink"> 음료
						<input type="radio" name="category" value="side" checked="checked"> 사이드
						<input type="radio" name="category" value="dessert"> 디저트
					</c:if>
					<c:if test="${menu_update.category ne null && menu_update.category eq 'dessert' }">
						<input type="radio" name="category" value="set"> 세트
						<input type="radio" name="category" value="burger"> 버거
						<input type="radio" name="category" value="drink"> 음료
						<input type="radio" name="category" value="side"> 사이드
						<input type="radio" name="category" value="dessert" checked="checked"> 디저트
					</c:if>
				<td>
			</tr>
			<tr>
				<th class="td_left">매뉴명</th>
				<td class="td_right">
					<input type="text" name="m_name" id="m_name" value="${menu_update.m_name }" required="required">
				<td>
			</tr>
			<tr>
				<th class="td_left">매뉴가격</th>
				<td class="td_right">
					<input type="number" name="m_price" id="m_price" value="${menu_update.m_price }" step="100" min="0" max="100000" required="required">
				<td>
			</tr>
			<tr>
				<th class="td_left">매뉴 설명</th>
				<td class="td_right"><!-- <textarea></textarea> 사이 공백X(시작커서가 젤 앞에 있지않게 됨, 떨어진만큼 커서도 떨어져 있음)  (wrap="off" : 자동줄바꿈방지) -->
					<textarea name="m_detail" id="m_detail" rows="13" cols="40" wrap="off" required="required" >${menu_update.m_detail }</textarea>
				<td>
			</tr>
			<tr>
				<th class="td_left">매뉴 판매 가능여부</th>
				<td class="td_right"> <!-- 반드시 선택하도록 처리 -->
				
					<c:if test="${menu_update.m_status ne null and menu_update.m_status eq 'y' }">
						<input type="radio" name="m_status" id="m_status" value="y" checked="checked"> 판매가능
						<input type="radio" name="m_status" id="m_status" value="n" > 판매불가		
					</c:if>
					<c:if test="${menu_update.m_status ne null and menu_update.m_status eq 'n' }">
						<input type="radio" name="m_status" id="m_status" value="y" > 판매가능
						<input type="radio" name="m_status" id="m_status" value="n" checked="checked"> 판매불가
					</c:if>
				
				<td>
			</tr>
			<tr>
				<th class="td_left">매뉴 이미지</th>
				<td class="td_right">
					<!-- [방법 1 ] : 기존의 이미지를 그대로 사용 ->  required="required" 필요없음 -->
					<input type="file" name="image" id="image">
					
					<!-- [방법 2 ]  : 이미지를 다시 올리는 경우 -->
					<!-- <input type="file" name="image" id="image" required="required"> -->
				<td>
			</tr>
			<tr>
				<th colspan="2" id="commandCell">
					<input type="submit" value="메뉴수정">
					<input type="reset" value="다시작성">
				</th>
			</tr>
		</table>
	</form>
</section>

</body>
</html>