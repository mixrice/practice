package svc.user;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.UserDAO;
import vo.Address;
import vo.UserBean;

public class UserViewService {

	// 멤버변수
	// 기본 생성자
	// 메서드
	
	// user_table안의 회원정보를 viewId로 조회
	public UserBean getUserInfo(String viewId) {
		
		// 1. 커넥션 풀에서 Connection객체 얻어와
		Connection con = getConnection();
		// 2. 싱글톤 패턴 userDAO객체 생성
		UserDAO userDAO = UserDAO.getInstance();
		// 3. DB작업에 사용될 Connection객체를 UserDAO의 멤버변수에 전달하여 DB와 연결하여 작업하도록 서비스 해줌
		userDAO.setConnection(con);
		
		/*--- UserDAO의 해당 메서드를 호출하여 처리 ---*/
		UserBean userInfo = userDAO.selectUserInfo(viewId);
		
		/*--- [insert, update, delete 경우] 성공하면 COMMIT 실패하면 ROLLBACK 
	                   (select 제외)                               ---*/

		// 4. 해제
		close(con); // Connection 객체 해제
		
		return userInfo;
	}

	// address_table안의 주소정보를 viewId로 조회
	public Address getAddressInfo(String viewId) {
		// 1. 커넥션 풀에서 Connection객체 얻어와
		Connection con = getConnection();
		// 2. 싱글톤 패턴 userDAO객체 생성
		UserDAO userDAO = UserDAO.getInstance();
		// 3. DB작업에 사용될 Connection객체를 UserDAO의 멤버변수에 전달하여 DB와 연결하여 작업하도록 서비스 해줌
		userDAO.setConnection(con);
		
		/*--- UserDAO의 해당 메서드를 호출하여 처리 ---*/
		Address userAddrInfo = userDAO.selectUserAddrInfo(viewId);
		
		// 4. 해제
		close(con); // Connection 객체 해제
		
		return userAddrInfo;
	}
	
}
