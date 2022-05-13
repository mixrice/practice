<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.sql.*, javax.sql.*, javax.naming.*" %>
<%
String openInit = "false";
if(request.getParameter("openInit") != null){
	openInit = request.getParameter("openInit"); // openInit = "true"가 됨
}
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<title>ID 중복확인</title>
</head>

<script type="text/javascript">
// 사용자가 회원가입폼(opener인 부모창)에서 입력한 id가 자동 셋팅됨
function init() {
	if(<%=openInit%>){ // openInit = "true"이면
		// opener인 부모 창의 u_id에 입력된 값을 가져와 아래 idCheck에 그대로 셋팅한다.
		document.getElementById("idCheck").value = opener.document.getElementById("u_id").value;
	        // 현재 창에서 name이 "idCheck"요소 = 부모창에 입력된 "u_id"값
	}
}

function useId(chk_id) {
	opener.document.getElementById("u_id").value = chk_id.trim(); // 부모창의 "u_id"값에 전달받은 chk_id값 셋팅
	window.close();
}

</script>

<!-- [아이디중복확인] 창이 열리면 onload 이벤트에 의해 init()함수가 호출되어 실행됨 -->
<body onload="init()">
	<form action="idCheckProcess.jsp" method="post" name="f">
		<input type="text" name="idCheck" id="idCheck">&nbsp;&nbsp;
		<input type="submit" value="중복확인" id="submit">
	</form>
</body >
</html>

<!-- 순서 중요 (아이디 중복을 확인한 결과를 받고 나서 java로 처리해야함) -->
<%
request.setCharacterEncoding("UTF-8");
String chk_id = request.getParameter("chk_id");

//if(request.getParameter("chk_id") != null && !request.getParameter("chk_id").trim().equals("")){
if(chk_id != null && !chk_id.trim().equals("")){
	String useable = request.getParameter("useable");
	
	out.println("<hr>"); // 수평선 출력
	
	if(useable.equals("NO")){ // 같은아이디가 존재하기때문에 사용할 수 없음
		out.println("<h4>" + chk_id + "는(은) 사용 불가능한 아이디 입니다. 다시 시도해 주세요.</h4>");
	}else{
		out.println("<h4>" + chk_id + "는(은) 사용 가능한 아이디 입니다.");
		out.println("<a href='javascript:useId(\"" + chk_id + "\")'>사용하기</a> </h4>");
	}
}
%>