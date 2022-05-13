package svc.user;

import static db.JdbcUtil.*; 

import static db.JdbcUtil.close;
import static db.JdbcUtil.getConnection;

import java.sql.Connection;

import dao.UserDAO;
import vo.UserBean;
/*
 * [Service의 공통적인 역할]
 *  1. 커넥션풀에서 커넥션 객체를 얻어와 DAO에 전달 (DAO생성도 Service에서 해줌)
 */
public class UserLoginService {
	// 멤버변수
	
	// 기본생성자
	
	// 메서드
	
	// 1. 회원여부확인
	public boolean login(UserBean user){ // (UserBean안에 회원 id와 비밀번호가 담겨져 있음)
		
		// 1. 커넥션 풀에서 Connection객체 얻어와
		Connection con =  getConnection(); // 바로 호출해서 사용 가능 (클래스명(JdbcUtil)생략가능) -> 이유: import static db.JdbcUtil.*; 해줬기 때문
		
		// 2. 싱글톤 패턴 UserDAO 객체 생성
		UserDAO userDAO = UserDAO.getInstance();
		
		// 3. DB작업에 사용될 Connection객체를 UserDAO의 멤버변수에 전달하여 DB와 연결하여 작업하도록 서비스 해줌
		userDAO.setConnection(con);
		
		/*--- UserDAO의 해당 메서드를 호출하여 처리 ---*/
		String loginId = userDAO.selectLoginId(user);
		
		/*--- [insert, update, delete 경우] 성공하면 COMMIT 실패하면 ROLLBACK 
                       (select 제외)                               ---*/
		
		boolean isloginResult = false;
		
		if(loginId != null) { // 값이 존재하면 (성공했으면)
			isloginResult = true;
		}
		
		// 4. 해제
		close(con); // Connection 객체 해제
		
		return isloginResult;
	}
	
	// 2. id로 회원정보 가져오기
	public UserBean getUserInfo(String u_id){ // DTO에 UserBean값을 담아 전달함
		// 1. 커넥션 풀에서 Connection객체 얻어와
		Connection con =  getConnection(); // 바로 호출해서 사용 가능 (클래스명(JdbcUtil)생략가능) -> 이유: import static db.JdbcUtil.*; 해줬기 때문
		
		// 2. 싱글톤 패턴 UserDAO 객체 생성
		UserDAO userDAO = UserDAO.getInstance();
		
		// 3. DB작업에 사용될 Connection객체를 UserDAO의 멤버변수에 전달하여 DB와 연결하여 작업하도록 서비스 해줌
		userDAO.setConnection(con);
		
		/*--- UserDAO의 해당 메서드를 호출하여 처리 ---*/
		UserBean userInfo = userDAO.getUserInfo(u_id);
		
		/*--- [insert, update, delete 경우] 성공하면 COMMIT 실패하면 ROLLBACK 
        (select 제외)                               ---*/

		// 4. 해제
		close(con); // Connection 객체 해제
		
		return userInfo;
	}
}
