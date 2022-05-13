package svc.admin;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.AdminDAO;

import vo.UserBean;

public class AdminLoginService {
	// 멤버변수
	// 기본생성자	
	// 메서드
	
	// id로 관리자 정보 가져오기
	public UserBean getAdminInfo(String a_id) {
		// 1. 커넥션 풀에서 Connection객체 얻어와
		Connection con =  getConnection(); // 바로 호출해서 사용 가능 (클래스명(JdbcUtil)생략가능) -> 이유: import static db.JdbcUtil.*; 해줬기 때문
		
		// 2. 싱글톤 패턴 AdminDAO 객체 생성
		AdminDAO adminDAO = AdminDAO.getInstance();
		
		// 3. DB작업에 사용될 Connection객체를 AdminDAO의 멤버변수에 전달하여 DB와 연결하여 작업하도록 서비스 해줌
		adminDAO.setConnection(con);
		
		/*--- AdminDAO의 해당 메서드를 호출하여 처리 ---*/
		UserBean adminInfo = adminDAO.selectAdminInfo(a_id);
		
		/*--- [insert, update, delete 경우] 성공하면 COMMIT 실패하면 ROLLBACK 
                       (select 제외)                               ---*/
		
		// 4. 해제
		close(con); // Connection 객체 해제
		return adminInfo;
	}

	public boolean login(UserBean admin) {
		// 1. 커넥션 풀에서 Connection객체 얻어와
		Connection con =  getConnection(); // 바로 호출해서 사용 가능 (클래스명(JdbcUtil)생략가능) -> 이유: import static db.JdbcUtil.*; 해줬기 때문
		
		// 2. 싱글톤 패턴 AdminDAO 객체 생성
		AdminDAO adminDAO = AdminDAO.getInstance();
		
		// 3. DB작업에 사용될 Connection객체를 AdminDAO의 멤버변수에 전달하여 DB와 연결하여 작업하도록 서비스 해줌
		adminDAO.setConnection(con);
		
		/*--- AdminDAO의 해당 메서드를 호출하여 처리 ---*/
		String loginId = adminDAO.selectLoginId(admin);
		
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

}
