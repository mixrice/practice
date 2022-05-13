package action.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import svc.menu.MenuCartQtyDownService;
import vo.ActionForward;

public class MenuCartQtyDownAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		String m_id = request.getParameter("m_id");
		
		// 장바구니 목록에서 해당 m_id를 찾아 수량 1감소
		MenuCartQtyDownService menuCartQtyDownService = new MenuCartQtyDownService();
		menuCartQtyDownService.downCartQty(m_id, request);
		
		// 다시 '장바구니 목록보기' 요청
		forward = new ActionForward("menuCartList.kiosk", true);
		return forward;
	}

}
