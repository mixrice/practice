package action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import vo.ActionForward;

public class MenuAddFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		request.setAttribute("admin_showMenu", "/admin/menuAddForm.jsp"); // menuAddForm.jsp 폼보기
		request.setAttribute("showAdmin", "admin/admin_template.jsp");
		forward = new ActionForward("adminMain.jsp", false);
		
		return forward;
	}

}
