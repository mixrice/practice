package action.user;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.user.UserIdFindService;
import vo.ActionForward;
import vo.UserBean;

public class UserIdFindAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = null;
		
		String u_email = request.getParameter("u_email");
		UserIdFindService userIdFindService = new UserIdFindService();
		UserBean userInfo = userIdFindService.findId(u_email);
		
		if(userInfo == null) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('존재하지 않는 계정입니다.');");
			out.println("history.back();");
			out.println("</script>");
			
		}else{
			String u_id = userInfo.getU_id();
			request.setAttribute("u_id", u_id);
			request.setAttribute("showPage", "user/findIdComplete.jsp");
			
			forward = new ActionForward("userTemplate.jsp", false); // 최종적인 이동경로
		}
		
		return forward;
	}

}
