package svc.user;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.UserDAO;
import vo.UserPwChange;

public class UserHashPwChangeService {
	// 멤버변수
	// 생성자
		
	// 메서드
	// 랜덤생성된 비밀번호를 사용자가 설정한 비번으로 변경
	public boolean changePw(UserPwChange userPwChange) {
		// 1. 커넥션 풀에서 Connection객체 얻어와
		Connection con =  getConnection(); // 바로 호출해서 사용 가능 (클래스명(JdbcUtil)생략가능) -> 이유: import static db.JdbcUtil.*; 해줬기 때문
		
		// 2. 싱글톤 패턴 UserDAO 객체 생성
		UserDAO userDAO = UserDAO.getInstance();
		
		// 3. DB작업에 사용될 Connection객체를 UserDAO의 멤버변수에 전달하여 DB와 연결하여 작업하도록 서비스 해줌
		userDAO.setConnection(con);
		
		/*--- UserDAO의 해당 메서드를 호출하여 처리 ---*/
		// [방법 1] [방법 3] 미리 비번 암호화
		//int chagngePwCount = userDAO.changePw(userPwChange);
		
		// [방법 2]  비번을 암호화 하지않고 DTO에 가서 암호화해서 업데이트
		int chagngePwCount = userDAO.changeHashPw(userPwChange);
		
		/*--- [insert, update, delete 경우] 성공하면 COMMIT 실패하면 ROLLBACK 
                       (select 제외)                               ---*/
		
		boolean isChagePwResult = false;
		if(chagngePwCount > 0) {
			isChagePwResult = true;
			commit(con);
		}else {
			rollback(con);
		}
		// 4. 해제
		close(con); // Connection 객체 해제
		
		return isChagePwResult;
		
	}
}
