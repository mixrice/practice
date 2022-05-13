<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
String cookieId =""; // ★★ null로 초기화하면 안됨
Cookie[] cookies = request.getCookies();

if(cookies != null && cookies.length > 0){ // 쿠키객체가 비어있지 않으면
	for(int i=0; i <cookies.length; i++){
		if(cookies[i].getName().equals("u_id")){
			cookieId = cookies[i].getValue(); // 쿠키값(=사용자 아이디)을 얻어와 cookId 변수에 저장
			break;
		}
	}
}
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<title>사용자 로그인 폼 페이지</title>

<style type="text/css">
	#loginformArea{
		margin: auto;
		width: 450ps;
		border: 1px solid gray;
	}
	
	table{
		margin: auto;
		width: 450ps;
		border: 1px solid gray;
	}
</style>

</head>
<body>
<section id="loginformArea">
	<form action="userLoginAction.usr" method="post" name="loginform">
		<input type="hidden" name="u_grade">
		
		<table>
			<tr>
				<th colspan="2">
					<h1>로그인</h1>
				</th>
			</tr>
			<tr>
				<th>아이디</th>
				<td><input type="text" name="u_id" value="<%=cookieId %>" size="30" placeholder="아이디입력(필수)"></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="u_password" size="31" placeholder="비밀번호입력(필수)"></td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="checkbox" name="remember"> 아이디 저장
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="submit" value="로그인">
				</th>
			</tr>
			<tr>
				<th colspan="2">
					<a href="userIdFindForm.usr">[아이디 찾기]</a>
					<a href="userHashPwFindForm.usr">[암호화된 비밀번호 찾기]</a>
				</th>
			</tr>
		</table>
		
	</form>
</section>
</body>
</html>