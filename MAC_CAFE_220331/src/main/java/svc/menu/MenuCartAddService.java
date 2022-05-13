package svc.menu;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.MenuDAO;
import vo.Cart;
import vo.Menu;

public class MenuCartAddService {

	public Menu getMenuView(String m_id) {
		// 1. 커넥션 풀에서 Connection객체 얻어와
		Connection con =  getConnection(); // 바로 호출해서 사용 가능 (클래스명(JdbcUtil)생략가능) -> 이유: import static db.JdbcUtil.*; 해줬기 때문
		
		// 2. 싱글톤 패턴 AdminDAO 객체 생성
		MenuDAO menuDAO = MenuDAO.getInstance();
		
		// 3. DB작업에 사용될 Connection객체를 AdminDAO의 멤버변수에 전달하여 DB와 연결하여 작업하도록 서비스 해줌
		menuDAO.setConnection(con);
		
		/*--- AdminDAO의 해당 메서드를 호출하여 처리 ---*/
		Menu menuInfo = menuDAO.selectMenuInfo(m_id);
		
		/*--- [insert, update, delete 경우] 성공하면 COMMIT 실패하면 ROLLBACK 
                       (select 제외)                               ---*/
		
		// 4. 해제
		close(con); // Connection 객체 해제
		return menuInfo;
	}

	public void addCart(HttpServletRequest request, Menu menuInfo) {
		// 현재 session영역에 저장되어 있는 장바구니 목록을 얻어와
		HttpSession session = request.getSession();
		ArrayList<Cart> cartList = (ArrayList<Cart>) session.getAttribute("cartList");
		
		if(cartList == null) {
			cartList = new ArrayList<Cart>();
			session.setAttribute("cartList", cartList);
		}
		// 지금 장바구니에 담는 항목이 새로 추가되는 항목인지 저장할 변수
		boolean isNewCart = true;
		
		// 기존에 장바구니 항목이 존재하면 같은 상품을 찾아서 수량을 1증가
		for(int i =0; i <cartList.size(); i++) {
			if(menuInfo.getM_id().equals(cartList.get(i).getM_id())) {
				isNewCart = false;
				cartList.get(i).setQty(cartList.get(i).getQty()+1);
				break;
			}
		}
		
		// 지금 장바구니에 담는 항목이 새로 추가되는 항목이면
		if(isNewCart) {
			Cart cart = new Cart(); // 기본값으로 채워진 Cart객체를
			// 매개값으로 전송된 menuInfo값으로 채운 후
			cart.setM_id(menuInfo.getM_id());
			cart.setCategory(menuInfo.getCategory());
			cart.setName(menuInfo.getM_name());
			cart.setPrice(menuInfo.getM_price());
			cart.setImage(menuInfo.getImage());
			cart.setM_date(menuInfo.getM_date());
			cart.setQty(1); // 수량은 처음이므로 1로 셋팅
			
			cartList.add(cart);// cartList에 추가
		}
	}

}
