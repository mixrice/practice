package action.user;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.user.UserDeleteService;
import vo.ActionForward;

public class UserDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		// [디자인 1] [회원탈퇴]메뉴가 따로 존재할때
		//HttpSession session = request.getSession();
		//String u_id = (String)session.getAttribute("u_id");
		
		// [디자인 1] [회원탈퇴]메뉴가 '회원정보관리'안에서 링크(href, get방식(정보노출-보안상 안좋음))를 통해서 전달 -> request로 값을 받으면됨
		String u_id = request.getParameter("u_id");
		
		UserDeleteService userDeleteService = new UserDeleteService();
		boolean isDeleteSuccess = userDeleteService.userdelete(u_id);
		
		if(isDeleteSuccess == false) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원탈퇴에 실패했습니다. 다시 시도해주세요.');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			// 세션영역을 하나하나 지워야함 (전체지울경우 관리자의 세션영역까지 삭제될 수 있음)
			HttpSession session = request.getSession();
			
			// ※주의 : 세션의 모든 속성을 삭제 : 관리자모드의 Session도 함께 삭제되므로 사용 X
			// session.invalidate();
			
			// 하나하나씩 속성을 삭제
			session.removeAttribute("u_id");
			session.removeAttribute("u_password");
			session.removeAttribute("u_grade");
			session.removeAttribute("u_name");

			session.removeAttribute("totalMoney");
			
			session.removeAttribute("cartList");
			session.removeAttribute("saleTotalMoney");
			session.removeAttribute("latestOrder");
			
			String cookieId = ""; // ★★ null로 초기화하면 안됨
			Cookie cookie = new Cookie("u_id", cookieId);
			cookie.setMaxAge(0); // 쿠키 즉시삭제(쿠키는 삭제메서드를 제공하지않음)
			// cookie.setMaxAge(-1); // 세션이 끝나면 삭제 (즉, 세션유효범위인 "브라우저 종료할 때 삭제됨")
			response.addCookie(cookie); // 반드시 쿠키를 추가해줘야함 (쿠키는 클라이언트에서 저장함)
			
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원정보 탈퇴에 성공했습니다. 감사합니다.');");
			out.println("location.href = 'userMain.jsp';");
			out.println("</script>");
			
			//forward = new ActionForward("userTemplate.jsp", false); // 디스패치방식으로 포워딩
		}
		
		return forward;
	}

}
