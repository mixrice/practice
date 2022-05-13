package svc.user;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.UserDAO;

public class UserDeleteService {
	//멤버변수
	// 생성자
	// 메서드
	
	// 회원탈퇴
	public boolean userdelete(String u_id) {
		// 1. 커넥션 풀에서 Connection객체 얻어와
		Connection con =  getConnection(); // 바로 호출해서 사용 가능 (클래스명(JdbcUtil)생략가능) -> 이유: import static db.JdbcUtil.*; 해줬기 때문
		
		// 2. 싱글톤 패턴 UserDAO 객체 생성
		UserDAO userDAO = UserDAO.getInstance();
		
		// 3. DB작업에 사용될 Connection객체를 UserDAO의 멤버변수에 전달하여 DB와 연결하여 작업하도록 서비스 해줌
		userDAO.setConnection(con);
		
		/*--- UserDAO의 해당 메서드를 호출하여 처리 ---*/
		
		int deleteUserCount = userDAO.deleteUser(u_id);
		int deleteAddrCount = userDAO.deleteAddr(u_id);
		
		/*--- [insert, update, delete 경우] 성공하면 COMMIT 실패하면 ROLLBACK 
                       (select 제외)                               ---*/
		
		boolean isUserDeleteResult = false;
		if(deleteUserCount > 0 && deleteAddrCount > 0) { // 회원가입에 성공하면
			isUserDeleteResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		// 4. 해제
		close(con); // Connection 객체 해제
		
		return isUserDeleteResult;
	}

}
