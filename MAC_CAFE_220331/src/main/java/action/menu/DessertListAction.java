package action.menu;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.menu.DessertListService;
import vo.ActionForward;
import vo.Menu;

public class DessertListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		DessertListService dessertListService = new DessertListService();
		ArrayList<Menu> dessertList = dessertListService.getDessertList();
		
		request.setAttribute("dessertList", dessertList);
		request.setAttribute("showMenu", "/kiosk/dessertList.jsp");
		
		forward = new ActionForward("menuTemplate.jsp", false);
		
		return forward;
	}

}
