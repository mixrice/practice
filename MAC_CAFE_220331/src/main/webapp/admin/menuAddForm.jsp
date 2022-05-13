<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>[관리자모드] 메뉴 추가</title>
</head>
<body>

<section id="addForm">

	<header><h2>맥카페 메뉴 등록</h2></header>
	<!-- 반드시 method="post"  파일을 전송하므로 enctype="multipart/form-data" 작성해줘야함-->
	<form action="menuAdd.adm" name="addForm" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<th colspan="2">메뉴 등록</th>
			</tr>
			<tr>
				<th class="td_left">매뉴 ID</th>
				<td class="td_right"> <!-- 장바구니에서 메뉴ID로 구분하여 사용 -->
					<input type="text" name="m_id" id="m_id" required="required">
				<td>
			</tr>
			<tr>
				<th class="td_left">매뉴 카테고리</th>
				<td class="td_right"> <!-- 반드시 선택하도록 처리 -->
					<input type="radio" name="category" value="set"> 세트
					<input type="radio" name="category" value="burger"> 버거
					<input type="radio" name="category" value="drink"> 음료
					<input type="radio" name="category" value="side"> 사이드
					<input type="radio" name="category" value="dessert"> 디저트
				<td>
			</tr>
			<tr>
				<th class="td_left">매뉴명</th>
				<td class="td_right">
					<input type="text" name="m_name" id="m_name" required="required">
				<td>
			</tr>
			<tr>
				<th class="td_left">매뉴가격</th>
				<td class="td_right">
					<input type="number" name="m_price" id="m_price" step="100" min="0" max="100000" required="required">
				<td>
			</tr>
			<tr>
				<th class="td_left">매뉴 설명</th>
				<td class="td_right"><!-- <textarea></textarea> 사이 공백X(시작커서가 젤 앞에 있지않게 됨, 떨어진만큼 커서도 떨어져 있음)  (wrap="off" : 자동줄바꿈방지) -->
					<textarea name="m_detail" id="m_detail"  rows="13" cols="40" wrap="off" required="required" ></textarea>
				<td>
			</tr>
			<tr>
				<th class="td_left">매뉴 판매 가능여부</th>
				<td class="td_right"> <!-- 반드시 선택하도록 처리 -->
					<input type="radio" name="m_status" id="m_status" value="y" checked="checked"> 판매가능
					<input type="radio" name="m_status" id="m_status" value="n" > 판매불가			
				<td>
			</tr>
			<tr>
				<th class="td_left">매뉴 이미지</th>
				<td class="td_right">
					<input type="file" name="image" id="image" required="required">
				<td>
			</tr>
			<tr>
				<th colspan="2" id="commandCell">
					<input type="submit" value="메뉴등록">
					<input type="reset" value="다시작성">
				</th>
			</tr>
		</table>
	</form>
</section>

</body>
</html>