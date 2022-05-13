package svc.menu;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vo.Cart;

public class MenuCartRemoveService {

	public void cartRemove(String m_id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		ArrayList<Cart> cartList = (ArrayList<Cart>)session.getAttribute("cartList");
		
		for(int i =0; i <cartList.size(); i++) {
			if(cartList.get(i).getM_id().equals(m_id)) {
				cartList.remove(cartList.get(i)); // 장바구니 목록에서 삭제
				break; // 삭제 시킨후 반복문 빠져나감
			}
		}
		
		session.setAttribute("cartList", cartList);
		
	}

}
