package svc.menu;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import vo.Cart;

public class MenuCartListService {
	// 멤버변수
	// 기본생성자
	// 메서드
	
	public ArrayList<Cart> getCartList(HttpServletRequest request) {
		ArrayList<Cart> cartList = null;
		
		HttpSession session = request.getSession();
		cartList = (ArrayList<Cart>) session.getAttribute("cartList");
		
		return cartList;
	}

}
