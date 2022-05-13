package action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.menu.MenuViewService;
import vo.ActionForward;
import vo.Menu;

public class MenuUpdateFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		// 파라미터로 전송된 m_id로 해당 메뉴의 정보를 얻어와
		String m_id = request.getParameter("m_id");
		
		MenuViewService menuViewService = new MenuViewService(); // 메뉴에 대한 정보를 보여줌
		Menu menu_update = menuViewService.getMenuView(m_id);
		
		request.setAttribute("menu_update", menu_update);
		
		request.setAttribute("admin_showMenu", "/admin/menuUpdateForm.jsp"); // menuAddForm.jsp 폼보기
		request.setAttribute("showAdmin", "admin/admin_template.jsp");
		
		forward = new ActionForward("adminMain.jsp", false);
		
		return forward;
	}

}
