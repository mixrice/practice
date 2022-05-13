package svc.review;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.ReviewDAO;
import vo.Review;

public class ReviewUpdateFormService {

	public Review getReview(int review_num) {
		// 1. 커넥션 풀에서 Connection객체 얻어와
		Connection con =  getConnection(); // 바로 호출해서 사용 가능 (클래스명(JdbcUtil)생략가능) -> 이유: import static db.JdbcUtil.*; 해줬기 때문
		
		// 2. 싱글톤 패턴 ReviewDAO 객체 생성
		ReviewDAO reviewDAO = ReviewDAO.getInstance();
		
		// 3. DB작업에 사용될 Connection객체를 ReviewDAO의 멤버변수에 전달하여 DB와 연결하여 작업하도록 서비스 해줌
		reviewDAO.setConnection(con);
		
		/*--- AdminDAO의 해당 메서드를 호출하여 처리 ---*/
		Review reviewInfo = reviewDAO.selectReview(review_num);
		
		/*--- [insert, update, delete 경우] 성공하면 COMMIT 실패하면 ROLLBACK 
                       (select 제외)                               ---*/
		
		// 4. 해제
		close(con); // Connection 객체 해제
		return reviewInfo;
	}
	
}
