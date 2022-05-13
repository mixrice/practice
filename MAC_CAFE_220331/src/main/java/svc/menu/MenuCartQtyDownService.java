package svc.menu;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vo.Cart;

public class MenuCartQtyDownService {

	public void downCartQty(String m_id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ArrayList<Cart> cartList = (ArrayList<Cart>) session.getAttribute("cartList");
		
		// 장바구니 목록에서 해당 m_id를 찾아 수량 1감소
		for(int i =0; i<cartList.size(); i++) {
			//if(m_id.equals(cartList.get(i).getM_id()) && cartList.get(i).getQty() > 0) {
			if(cartList.get(i).getM_id().equals(m_id) && cartList.get(i).getQty() > 0) {
				cartList.get(i).setQty(cartList.get(i).getQty() - 1);
				break;
			}
		}
		
	}

}
