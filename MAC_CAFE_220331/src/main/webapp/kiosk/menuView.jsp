<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>메뉴 상세 정보보기 + 리뷰보기</title>
</head>
<body>
	<section>
		<!-- 메뉴 상세 정보보기 --------------------->
		<h3>${menuInfo.m_name }</h3>
		<table>
			<tr> <!-- [관리자 모드] : 업로드한 폴더이름 images -->
				<img src="<%=request.getContextPath() %>/images/${menuInfo.image }" width="100%">
			</tr>
			<tr>
				<td>
					<h4>제품 상세 정보</h4><br>
					${menuInfo.m_detail }
				</td>
			</tr>
			<tr>
				<td>가격 : ${menuInfo.m_price}원</td>
			</tr>
			<tr>
				<td>
					<h4><a href="menuCartAdd.kiosk?m_id=${menuInfo.m_id }&m_name=${menuInfo.m_name}">
						[장바구니 담기]
					</a></h4>
				</td>
			</tr>
		</table>
		<br><br>
		<hr>
		<br><br>
		
		<!-- 리뷰보기 -------------------->
		<h3>이 메뉴를 구매한 고객님들의 리뷰</h3>
		<div>
			<jsp:include page="${reviewPage }" />
		</div>
		
		
		
		
	</section>
</body>
</html>