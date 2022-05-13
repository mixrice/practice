package action.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.menu.MenuCartRemoveAllService;
import vo.ActionForward;

public class MenuCartRemoveAllAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		MenuCartRemoveAllService menuCartRemoveAllService = new MenuCartRemoveAllService();
		menuCartRemoveAllService.cartRemoveAll(request);
		
		forward = new ActionForward("menuCartList.kiosk", true);
		
		return forward;
	}

}
