package action.menu;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class MenuAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		// 먼저, 주문하기 위해 로그인된 상태인지를 확인 (세션 영역에서)
		HttpSession session = request.getSession();
		String u_id = (String)session.getAttribute("u_id");
		
		if(u_id == null) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('로그인 후 이용해주세요.');");
			out.println("location.href ='userLogin.usr';");
			out.println("</script>");
		}else {
			forward = new ActionForward("menuTemplate.jsp", false);
			
		}
		
		return forward;
	}

}
