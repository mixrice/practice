package action.menu;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.menu.SideListService;
import vo.ActionForward;
import vo.Menu;

public class SideListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		SideListService sideListService = new SideListService();
		ArrayList<Menu> sideList = sideListService.getSideList();
		
		request.setAttribute("sideList", sideList);
		request.setAttribute("showMenu", "/kiosk/sideList.jsp");
		
		forward = new ActionForward("menuTemplate.jsp", false);
		
		return forward;
	}

}
