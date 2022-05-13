package action.menu;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import svc.menu.MenuCartListService;
import vo.ActionForward;
import vo.Cart;

public class MenuCartListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		
		MenuCartListService menuCartListService = new MenuCartListService();
		ArrayList<Cart> cartList =  menuCartListService.getCartList(request); // request : 세션영역의 값을 얻어오기 위해
		
		/*--- 사용자가 지불할 총 금액 계산 --------------------------------*/
		int totalMoney = 0 ;
		
		if(cartList != null) {
			for(int i =0; i < cartList.size(); i++) {
				totalMoney += cartList.get(i).getPrice() * cartList.get(i).getQty();
			}
		}
		
		// 로그인된 상태동안 공유할 수 있도록 session영역에 저장함
		HttpSession session = request.getSession(); // 기존의 세션영역을 가지고와서
		session.setAttribute("totalMoney", totalMoney);
		session.setAttribute("cartList", cartList);
		
		request.setAttribute("showCart", "/kiosk/menuCartList.jsp");
		
		forward = new ActionForward("menuTemplate.jsp", false);
		
		return forward;
	}

}
