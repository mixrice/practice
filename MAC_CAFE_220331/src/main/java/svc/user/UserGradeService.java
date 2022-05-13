package svc.user;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.UserDAO;
import vo.UserBean;

public class UserGradeService {
	
	// 멤버변수
	// 생성자
	
	// 메서드
	// [1]. 지난달 구매금약을 조회하여 사용자 등급 변경한 사용자 정보를 다시 리턴받음
	public static UserBean updateGrade(UserBean user){
		// 1. 커넥션 풀에서 Connection객체 얻어와
		Connection con =  getConnection(); // 바로 호출해서 사용 가능 (클래스명(JdbcUtil)생략가능) -> 이유: import static db.JdbcUtil.*; 해줬기 때문
		
		// 2. 싱글톤 패턴 UserDAO 객체 생성
		UserDAO userDAO = UserDAO.getInstance();
		
		// 3. DB작업에 사용될 Connection객체를 UserDAO의 멤버변수에 전달하여 DB와 연결하여 작업하도록 서비스 해줌
		userDAO.setConnection(con);
		
		/*--- UserDAO의 해당 메서드를 호출하여 처리 ---*/
		int lastMonthMoney= userDAO.getLastMonthMoney(user.getU_id());
		
		// 사용자의 등급을 얻어와
		String grade = user.getU_grade();
		
		/*--- [insert, update, delete 경우] 성공하면 COMMIT 실패하면 ROLLBACK 
                       (select 제외)                               ---*/
		
		int gradeCount = 0;
		if(grade.trim().equalsIgnoreCase("Normal") && lastMonthMoney >= 100000) {
			// 등급을 VIP로 업그레이드해줌
			gradeCount = userDAO.upGrade(user); // 성공하면 1리턴받음
			if(gradeCount > 0) {
				commit(con); // 업데이트한 내용을 영구저장
			}else {
				rollback(con); // 실패시 이전상태로 복구
			}
		}
		else if(grade.trim().equalsIgnoreCase("Vip") && lastMonthMoney < 100000) {
			// 등급을 NORMAL로 다운
			gradeCount = userDAO.downGrade(user); // 성공하면 1리턴받음
			if(gradeCount > 0) {
				commit(con); // 업데이트한 내용을 영구저장
			}else {
				rollback(con); // 실패시 이전상태로 복구
			}
		}
		
		// 갱신된 DB를 다시 id로 조회한 사용자 정보를 리턴함
		UserBean userInfo = userDAO.getUserInfo(user.getU_id()); // id로 사용자 정보를 가져오는 메서드
		
		// 4. 해제
		close(con); // Connection 객체 해제
		
		return userInfo;
	}
	
	// [2]. u_grade등급으로 세일비율을 조회하여 리턴
	public double getSaleRate(String u_grade) {
		// 1. 커넥션 풀에서 Connection객체 얻어와
		Connection con =  getConnection(); // 바로 호출해서 사용 가능 (클래스명(JdbcUtil)생략가능) -> 이유: import static db.JdbcUtil.*; 해줬기 때문
		
		// 2. 싱글톤 패턴 UserDAO 객체 생성
		UserDAO userDAO = UserDAO.getInstance();
		
		// 3. DB작업에 사용될 Connection객체를 UserDAO의 멤버변수에 전달하여 DB와 연결하여 작업하도록 서비스 해줌
		userDAO.setConnection(con);
		
		/*--- UserDAO의 해당 메서드를 호출하여 처리 ---*/
		double saleRate = 1.0;
		saleRate = userDAO.selectSaleRate(u_grade);
		/*--- [insert, update, delete 경우] 성공하면 COMMIT 실패하면 ROLLBACK 
                       (select 제외)                               ---*/
		
		// 4. 해제
		close(con); // Connection 객체 해제
		
		return saleRate;
		
	}
	
	
	
	
	
	
}
