package action.menu;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.menu.BurgerListService;
import vo.ActionForward;
import vo.Menu;

public class BurgerListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
ActionForward forward = null;
		
	BurgerListService burgerListService = new BurgerListService();
		ArrayList<Menu> burgerList = burgerListService.getBurgerList();
		
		request.setAttribute("burgerList", burgerList);
		request.setAttribute("showMenu", "/kiosk/burgerList.jsp");
		
		forward = new ActionForward("menuTemplate.jsp", false);
		
		return forward;
	}

}
