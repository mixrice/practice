package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import vo.Order;

public class OrderDAO {
	Connection con; // private 없어도 됨 (DTO는 반드시 private 있어야함)
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	/** 싱글톤 패턴 : ReviewDAO객체 단 1개만 생성
	 *  이유? 외부클래스에서 "처음 생성된 ReviewDAO객체를 공유해서 사용하도록 하기 위해" 
	 **/
	
	private OrderDAO() {}
	
	private static OrderDAO orderDAO;
	
	public static OrderDAO getInstance(){ 
		
		if(orderDAO == null) { 
			orderDAO = new OrderDAO(); // 객체 생성
		}
		
		return orderDAO; // 생성되어 있다면 기존 orderDAO객체의 주소를 리턴
	}
	
	public void setConnection(Connection con){ // Connection객체를 받아 DB연결
		// Connection객체 1개를 받아서 멤버변수에 셋팅(Connection객체를 참조할수 있도록) 
		this.con = con;
	}

	
	/**********************************************************************************************/
	
	/* -- 사용자 모드 ---------------------------------------------------*/
	// 해당 사용자의 가장 마지막 주문 내역
	public Order getLatestOrder(String u_id) {
		Order latestOrder = null;
		// '가장 최근 날짜에 대한 주문내역' : 주문한 내역 중 날짜로 내림차순 정렬 후  첫번째 줄만 가져옴
		String sql = "SELECT * FROM ORDER_TABLE WHERE U_ID =? ORDER BY ORDER_DATE DESC LIMIT 1";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			pstmt.setString(1, u_id); // ? 값 설정
			rs = pstmt.executeQuery(); // 실행
			
			if(rs.next()) { 
				latestOrder = new Order(rs.getInt("order_num"), 
										rs.getString("u_id"), 
										rs.getString("u_email"), 
										rs.getTimestamp("order_date"),   // sql문에서 order_date를 date대신 timestamp로 설정했음
										rs.getString("order_status"), 
										rs.getInt("totalmoney")
										);
			} // if문 끝
			
		} catch (Exception e) {
			System.out.println("[OrderDAO]getLatestOrder 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		return latestOrder;
	}
	
	
}
