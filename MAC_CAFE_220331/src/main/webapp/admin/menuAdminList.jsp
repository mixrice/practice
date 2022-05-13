<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>menuAdminList</title>
</head>
<body>

<section id="listForm">
	<!-- 클릭한 메뉴목록이 있으면 (세트를 클릭하면 세트의 목록이 있으면) -->
	<c:if test="${menuAdminList != null && menuAdminList.size() > 0}">
		<table>
			<tr>
				<!-- 향상된 for문 -->
				<c:forEach var="menu" items="${menuAdminList }" varStatus="status"> 
					<td>
						<img src="images/${menu.image }" id="productImage"><br>
						메뉴ID : ${menu.m_id }<br>
						메뉴 카테고리 : ${menu.category }<br>
						메뉴명 : ${menu.m_name }<br>
						메뉴가격 : ${menu.m_price }원<br><br>
						[메뉴설명]<br>  ${menu.m_detail }<br><br>
						
						판매가능 여부 : 
							<c:if test="${menu.m_status eq 'y' }">
								판매가능<br>
							</c:if>
							<c:if test="${menu.m_status eq 'n'}">
								판매불가<br>
							</c:if>
						
						메뉴등록날짜 : ${menu.m_date }<br>
						메뉴 이미지 : ${menu.image }<br><br>
						
						<a href="menuUpdateForm.adm?m_id=${menu.m_id }"><b>[메뉴 수정]</b></a>
						&nbsp;&nbsp;&nbsp;
						<a href="menuDelete.adm?m_id=${menu.m_id }&category=${menu.category}"><b>[메뉴 삭제]</b></a>
						
						<br><br><br>
					</td>
						<!-- 메뉴 목록을 출력할때 1줄에 4개씩만 나란히 출력되도록 하기위해 -->
						<c:if test="${((status.index+1) mod 4) == 0 }">
							</tr>
							<tr>
							<!-- 1 mod 2 == 1 (거짓) </tr><tr> 실행 X
							     2 mod 2 == 0 (참)  </tr><tr> 실행 O -->
						</c:if>
				</c:forEach>
			</tr>
	    </table>
	</c:if>
	
	<!-- 클릭한 메뉴목록이 없으면 -->
	<c:if test="${menuAdminList eq null }">
		<div><br><h1>등록된 메뉴가 없습니다. 메뉴를 등록해 주세요.</h1><br></div>
	</c:if>
</section>

</body>
</html>

<%
System.out.println("menuAdminList.jsp");
%>