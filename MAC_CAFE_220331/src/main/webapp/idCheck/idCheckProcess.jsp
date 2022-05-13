<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.sql.*, javax.sql.*, javax.naming.*" %> 

<!-- java로 처리만 하고 web상에 보여주지 않음  -->

<%
request.setCharacterEncoding("utf-8");

String idCheck = request.getParameter("idCheck");

if(idCheck != null && !idCheck.trim().equals("")){ // 항상 먼저, null여부 확인 (idCheck값을 입력했다면)
	
	// 1. DB 연결 -------------------------------------------------
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs =null;
	
	String sql ="SELECT * FROM USER_TABLE WHERE U_ID = ?";
		
	try {
		Context initCtx = new InitialContext(); // 톰캣자체에 있는 Context객체를 가져옴
		// Resource에 정의된 Context를 Object타입으로 얻어와 Context으로 강제 형변환
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		
		// name으로 커넥션 풀인 DataSource 객체를 얻어와
		DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQLDB"); // "jdbc/MySQLDB" : META-INF/context.xml에 설정한 name 값을 가져와야함
		
		con = ds.getConnection(); // 커넥션 풀에서 Connection객체를 얻어와
		
		//con.setAutoCommit(false); // <리턴하기전에> Connection객체의 트랙잭션을 '자동커밋' 되지않도록 하기위해 "false"설정을 해줘야함
		                            // select문을 사용하기 때문에 필요없음
		
		// 2. SQL문 실행 후 결과 -----------------------------------------------                            
		pstmt = con.prepareStatement(sql);
		pstmt.setString(1, idCheck);
		rs = pstmt.executeQuery();
		
		if(rs.next()){ // 같은 id가 있으면 리다이렉트 방식으로 요청함(기존의 request 사용X)
			response.sendRedirect("idCheck.jsp?chk_id=" + idCheck + "&useable=NO"); // 전달 받은 "&useable=NO"값을 보고 아이디를 사용할 수 없다고 판단함
		}else{ // 같은 id가 없으면
			response.sendRedirect("idCheck.jsp?chk_id=" + idCheck + "&useable=YES"); // 전달 받은 "&useable=YESO"값을 보고 아이디를 사용할 수 있다고 판단함
		}
		
	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		// 3. DB 해제 ----------------------------------------------- 
		try{
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}else{ // 입력된 값이 없다면
	out.println("<script>");
	out.println("alert('중복을 확인할 아이디를 입력하세요.');");
	out.println("location.href = 'idCheck.jsp'");
	out.println("</script>");
}
%>
