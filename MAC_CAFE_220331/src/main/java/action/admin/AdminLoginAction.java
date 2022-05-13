package action.admin;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.admin.AdminLoginService;
import util.SHA256;
import vo.ActionForward;
import vo.UserBean;

public class AdminLoginAction implements Action {
	// 멤버변수
	
	// 기본생성자( 기본생성자를 호출하여 객체 생성)
	
	// 메서드
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String a_id = request.getParameter("u_id"); // request로부터 얻어온 u_id값을 저장
		String a_password = request.getParameter("u_password");
		
		String remember = request.getParameter("remember"); // 아이디 저장 체크여부를 확인 -> 쿠키객체 생성 여부를 사용함(체크 : 쿠키객체 생성, 체크X : 쿠키객체 생성X)
		
		// 로그인 폼 페이지에서 파라미터 전송된 id와 비번값을 저장할 UserBean객체 생성 (따로따로 보내는 것이 아니라 UserBean객체에 담아 한꺼번에 DAO로 전송함)
		UserBean admin = new UserBean(); // 기본값으로 채워진 UserBean객체 생성
		
		admin.setU_id(a_id); // 파라미터로 넘어온 값들로 셋팅
		// admin.setU_password(u_password); // 암호화X(DB안에는 이미 암호화된 상태로 저장되어있기 때문에 암호화를 해야지 DB값과 비교할 수 있기 때문)
		admin.setU_password(SHA256.encodeSHA256(a_password)); // 암호화 O (사용자가 입력한 암호를 암호화시켜 DB안의 암호화된 비번과 비교) (DB안에는 이미 암호화된 상태로 저장되어있기 때문)
		
		// ★★ 관리자모드 : 먼저, 관리자 정보를 얻어와 u_grade가 Admin인지 확인해야함
		// 로그인 처리를 위한 Service객체를 생성하여
		AdminLoginService adminLoginService = new AdminLoginService();
		UserBean adminInfo = adminLoginService.getAdminInfo(a_id); // 로그인한 사용자가 관리자인지 확인
		
		if(adminInfo == null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('존재하지않는 계정입니다.')"); // 경고창을 띄우고
			out.println("location.href ='adminLogin.adm'"); // 다시 '로그인 폼보기'요청을 함
			out.println("</script>");
		}else {
			if(adminInfo.getU_grade().equalsIgnoreCase("Admin")) { // 등급이 admin이면
				// 로그인 요청을 처리하는 login()호출 (이때, 매개값 : 로그인정보가 저장된
				boolean isloginSuccess = adminLoginService.login(admin); // boolean타입으로 전달 받음
				
				if(isloginSuccess == false) { //로그인 실패
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					
					out.println("<script>");
					out.println("alert('아이디나 비밀번호가 일치하지 않습니다.')"); // 경고창을 띄우고
					out.println("location.href ='adminLogin.adm'"); // 다시 '로그인 폼보기'요청을 함
					out.println("</script>");
					
				}else { // 로그인 성공
					Cookie cookie = new Cookie("a_id", a_id);
					System.out.println("관리자 Cookie객체 생성");
					
					if(remember != null) { // '아이디 저장'에 체크했으면
						response.addCookie(cookie); // 응답할때 응답객체에 쿠키와 함께 보냄
						
					}else { // '아이디 저장'에 체크 안했으면
						cookie.setMaxAge(0); // 쿠키는 삭제 메서드가 없기때문에 유효시간을 0으로 셋팅해줌 (쿠키를 삭제한 효과)
						// cookie.setMaxAge(-1); // 세션이 끝나면 쿠키가 삭제됨 (세션유지시간 1시간으로 설정했음 (WEB-INF의 web.xml에 코드를 추가했음))
						response.addCookie(cookie); // 응답할때 응답객체에 쿠키와 함께 보냄
					}
					
					HttpSession session = request.getSession();
					session.setAttribute("a_id", a_id);
					session.setAttribute("a_password", a_password);
					
					session.setAttribute("a_grade", adminInfo.getU_grade());
					session.setAttribute("a_name", adminInfo.getU_name());
					
					session.setMaxInactiveInterval(24*60*60); // 세션유지시간을 24시간으로 설정 (3600초=1시간)
					
					forward = new ActionForward("adminMain.jsp",false);
					
				}
				
			}else { // 등급이 admin이 아니면
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('관리자 권한이 없습니다.')"); // 경고창을 띄우고
				out.println("location.href ='adminLogin.adm'"); // 다시 '로그인 폼보기'요청을 함
				out.println("</script>");
			}
			
		}
		
		return forward;
	}

}
