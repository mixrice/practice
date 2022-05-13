package svc.menu;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.util.ArrayList;

import dao.MenuDAO;
import dao.OrderDAO;
import vo.Menu;
import vo.Order;

public class MenuCartOrderService {
	// 멤버변수
	// 기본생성자
	// 메서드
	
	public boolean orderMenu(String u_id, String u_email, ArrayList<Menu> menuList, int saleTotalMoney) {
		// 1. 커넥션 풀에서 Connection객체 얻어와
		Connection con =  getConnection(); // 바로 호출해서 사용 가능 (클래스명(JdbcUtil)생략가능) -> 이유: import static db.JdbcUtil.*; 해줬기 때문
		
		// 2. 싱글톤 패턴 AdminDAO 객체 생성
		MenuDAO menuDAO = MenuDAO.getInstance();
		
		// 3. DB작업에 사용될 Connection객체를 AdminDAO의 멤버변수에 전달하여 DB와 연결하여 작업하도록 서비스 해줌
		menuDAO.setConnection(con);
		
		/*--- AdminDAO의 해당 메서드를 호출하여 처리 ---*/
		int insertOrderMenuCount = menuDAO.insertOrderMenu(u_id, u_email, menuList, saleTotalMoney);
		
		/*--- [insert, update, delete 경우] 성공하면 COMMIT 실패하면 ROLLBACK 
                       (select 제외)                               ---*/
		
		boolean isOrderMenuResult = false;
		
		if(insertOrderMenuCount > 0) {
			isOrderMenuResult = true;
			commit(con);
			
		}else {
			rollback(con);
		}
	
		// 4. 해제
		close(con); // Connection 객체 해제
		return isOrderMenuResult;
	}

	public Order userLastOrder(String u_id) {
		// 1. 커넥션 풀에서 Connection객체 얻어와
		Connection con =  getConnection(); // 바로 호출해서 사용 가능 (클래스명(JdbcUtil)생략가능) -> 이유: import static db.JdbcUtil.*; 해줬기 때문
		
		// 2. 싱글톤 패턴 AdminDAO 객체 생성
		OrderDAO orderDAO = OrderDAO.getInstance();
		
		// 3. DB작업에 사용될 Connection객체를 AdminDAO의 멤버변수에 전달하여 DB와 연결하여 작업하도록 서비스 해줌
		orderDAO.setConnection(con);
		
		/*--- AdminDAO의 해당 메서드를 호출하여 처리 ---*/

		
		/*--- [insert, update, delete 경우] 성공하면 COMMIT 실패하면 ROLLBACK 
                       (select 제외)                               ---*/
		
		Order latestOrder = orderDAO.getLatestOrder(u_id);
		
		// 4. 해제
		close(con); // Connection 객체 해제
		return latestOrder;
	}

}
