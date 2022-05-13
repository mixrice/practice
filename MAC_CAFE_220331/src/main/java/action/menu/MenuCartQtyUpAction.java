package action.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.menu.MenuCartQtyUpService;
import vo.ActionForward;

public class MenuCartQtyUpAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String m_id = request.getParameter("m_id");
		
		// 장바구니 목록에서 해당 m_id를 찾아 수량 1증가
		MenuCartQtyUpService menuCartQtyUpService = new MenuCartQtyUpService();
		menuCartQtyUpService.upCartQty(m_id, request);
		
		// 다시 '장바구니 목록보기' 요청
		forward = new ActionForward("menuCartList.kiosk", true);
		return forward;
	}

}
