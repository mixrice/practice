package dao;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.Review;

public class ReviewDAO {
	private Connection con;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/** 싱글톤 패턴 : ReviewDAO객체 단 1개만 생성
	 *  이유? 외부클래스에서 "처음 생성된 ReviewDAO객체를 공유해서 사용하도록 하기 위해" 
	 **/
	
	private ReviewDAO() {}
	
	private static ReviewDAO reviewDAO;
	
	public static ReviewDAO getInstance(){ 
		
		if(reviewDAO == null) { 
			reviewDAO = new ReviewDAO(); // 객체 생성
		}
		
		return reviewDAO; // 생성되어 있다면 기존 reviewDAO객체의 주소를 리턴
	}
	
	public void setConnection(Connection con){ // Connection객체를 받아 DB연결
		// Connection객체 1개를 받아서 멤버변수에 셋팅(Connection객체를 참조할수 있도록) 
		this.con = con;
	}

	public ArrayList<Review> selectReviewList(String m_id) {
		ArrayList<Review> reviewList = null;
		
		String sql = "SELECT * FROM REVIEW_TABLE WHERE M_ID ='" + m_id +"'";
		
		try{
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 기본값인 NULL로 채워진 ArrayList<Review>객체를
				reviewList = new ArrayList<Review>();
				
				do {
					// 조회한 review정보값으로 채워진 'Review객체 생성'하여 바로 ArrayList에 추가
					reviewList.add(new Review(rs.getInt("review_num"),
					                          rs.getString("u_id"),
						                      rs.getString("m_id"),
						                	  rs.getInt("rating"),
						                	  rs.getString("text")
			     	));
					
				}while(rs.next());
			}
			
		}catch (Exception e) {
			System.out.println("[ReviewDAO]selectReviewList 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return reviewList;
	}

	// 리뷰등록
	public int insertReview(Review review) {
		int insertReviewCount = 0;
		
		String sql = "INSERT INTO REVIEW_TABLE(U_ID, M_ID, RATING, TEXT) VALUES(?, ?, ?, ?)";
		
		try{
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, review.getU_id());
			pstmt.setString(2, review.getM_id());
			pstmt.setInt(3, review.getRating());
			pstmt.setString(4, review.getText());
	
			insertReviewCount = pstmt.executeUpdate();
		
		}catch (Exception e) {
			System.out.println("[ReviewDAO]insertReview 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(pstmt);
		}
		
		return insertReviewCount;
	}
	
	// 해당 리뷰번호로(review_num)로 '원본 리뷰의 정보'를 조회하여 리턴
	public Review selectReview(int review_num) {
		Review reviewInfo = null;
		
		String sql = "SELECT * FROM REVIEW_TABLE WHERE REVIEW_NUM =" + review_num;
		
		try{
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 기본값인 NULL로 채워진 Review객체를
				reviewInfo = new Review(rs.getInt("review_num"),
						                rs.getString("u_id"),
						                rs.getString("m_id"),
						                rs.getInt("rating"),
						                rs.getString("text")
					     	);
				
			
			}
			
		}catch (Exception e) {
			System.out.println("[ReviewDAO]selectReview 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		return reviewInfo;
	}
	
	// 리뷰수정
	public int updateReview(Review review) { // 반드시 Review 생성자의 매개변수에 REVIEW_NUM이 존재하는 생성자
		int updateReviewCount = 0;
		
		String sql = "UPDATE REVIEW_TABLE SET U_ID =?, M_ID=?, RATING=?, TEXT=? WHERE REVIEW_NUM =?";
		
		try{
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, review.getU_id());
			pstmt.setString(2, review.getM_id());
			pstmt.setInt(3, review.getRating());
			pstmt.setString(4, review.getText());
			pstmt.setInt(5, review.getReview_num());
	
			updateReviewCount = pstmt.executeUpdate();
		
		}catch (Exception e) {
			System.out.println("[ReviewDAO]updateReview 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(pstmt);
		}
		
		return updateReviewCount;
	}
	
	// 리뷰삭제
	public int deleteReview(int review_num, String u_id) { // 반드시 Review 생성자의 매개변수에 REVIEW_NUM이 존재하는 생성자
		int deleteReviewCount = 0;
		
		String sql = "DELETE FROM REVIEW_TABLE WHERE REVIEW_NUM =? AND U_ID =?";
		
		try{
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, review_num);
			pstmt.setString(2, u_id);
	
			deleteReviewCount = pstmt.executeUpdate();
		
		}catch (Exception e) {
			System.out.println("[ReviewDAO]deleteReview 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(pstmt);
		}
		
		return deleteReviewCount;
	}
}
