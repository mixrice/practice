package action.admin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class MenuManageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		/* 메뉴관리 요청은 Admin만 할 수 있도록 세션영역의 grade를 얻어와 확인*/
		 HttpSession session = request.getSession();
		 String a_grade = (String)session.getAttribute("a_grade");
		 
		 if(a_grade == null || !a_grade.equalsIgnoreCase("Admin")) {
			 response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('관리자로 로그인해 주세요.');");
				out.println("location.href='adminMain.jsp'");
				out.println("</script>");
		 }else {
			 request.setAttribute("showAdmin", "admin/admin_template.jsp");
			 forward = new ActionForward("adminMain.jsp", false);
		 }
		
		return forward;
	}

}
