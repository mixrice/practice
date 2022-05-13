package svc.menu;

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;

import dao.MenuDAO;
import vo.Menu;

/*
 * menu 패키지에 넣는 이유 ?
 * 사용자와 관리자 둘다 사용하므로 ...
 */
public class MenuViewService {
	// 멤버변수
	// 기본 생성자
	// 메서드
	
	// menu_table 안의 메뉴정보를 m_id로 조회한 결과를 리턴
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

}
