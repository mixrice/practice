package action.menu;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.menu.SetListService;
import vo.ActionForward;
import vo.Menu;

public class SetListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		SetListService setListService = new SetListService();
		ArrayList<Menu> setList = setListService.getSetList();
		
		request.setAttribute("setList", setList);
		request.setAttribute("showMenu", "/kiosk/setList.jsp");
		
		forward = new ActionForward("menuTemplate.jsp", false);
		
		return forward;
	}

}
