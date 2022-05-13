package action.admin;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.menu.BurgerListService;
import vo.ActionForward;
import vo.Menu;

public class BurgerAdminListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		/* 메뉴관리 요청은은 Admin만 할수 있도록 세션영역의 grade를 얻어와 Admin인지 확인 */
		HttpSession session = request.getSession();
		String a_grade = (String)session.getAttribute("a_grade");
		 
		if(a_grade == null || !a_grade.equalsIgnoreCase("Admin")) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('관리자로 로그인해 주세요.');");
			out.println("location.href='adminMain.jsp'");
			out.println("</script>");
			
		 }else { // 관리자이면
			 /**** [변경하는 내용] **************************************************************/
			 BurgerListService burgerListService = new BurgerListService();
			 ArrayList<Menu> burgerList = burgerListService.getBurgerList();
			 
		 	 request.setAttribute("menuAdminList", burgerList);
		 	 
		 	/********************************************************************************/
		 	 request.setAttribute("admin_showMenu", "/admin/menuAdminList.jsp");
			 request.setAttribute("showAdmin", "admin/admin_template.jsp");
			 forward = new ActionForward("adminMain.jsp", false);
		 }
		
		return forward;
	}
}
