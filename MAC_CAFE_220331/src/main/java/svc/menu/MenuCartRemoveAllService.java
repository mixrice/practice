package svc.menu;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vo.Cart;

public class MenuCartRemoveAllService {

	public void cartRemoveAll(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// ArrayList<Cart> cartList = (ArrayList<Cart>)session.getAttribute("cartList");
		
		session.removeAttribute("cartList");
	}

}
