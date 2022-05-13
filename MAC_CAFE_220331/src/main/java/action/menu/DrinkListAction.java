package action.menu;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.menu.DrinkListService;
import vo.ActionForward;
import vo.Menu;

public class DrinkListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		DrinkListService drinkListService = new DrinkListService();
		ArrayList<Menu> drinkList = drinkListService.getDrinkList();
		
		request.setAttribute("drinkList", drinkList);
		request.setAttribute("showMenu", "/kiosk/drinkList.jsp");
		
		forward = new ActionForward("menuTemplate.jsp", false);
		
		return forward;
	}

}
