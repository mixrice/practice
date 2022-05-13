package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import vo.Address;
import vo.UserBean;

public class AdminDAO {
	// 멤버변수(전역변수 : 전체 메서드에서 사용 가능)  (DAO의 멤버변수는 private안해도 됨 (DTO의 멤버변수는 private해야함))
	private Connection con;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/** 싱글톤 패턴 : AdminDAO객체 단 1개만 생성
	 *  이유? 외부클래스에서 "처음 생성된 AdminDAO객체를 공유해서 사용하도록 하기 위해" 
	 **/
	
	// 1. 생성자는 무조건 private을 붙임(이유? 외부 클래스에서 생성자 호출 불가하여 직접적으로 객체생성 불가능하게 하기위해)
	private AdminDAO() {}
	
	private static AdminDAO adminDAO; // 반드시 static이여야함 (getInstance()메서드에서 AdminDAO를 호출하여 사용하는데  getInstance()메서드가 static이기 때문)
	
	// 2. static이유? 객체를 생성하기 전에 이미 메모리에 올라간 'getInstance()메서드를 통해서만 AdminDAO객체를 1개만 만들수' 있도록 하기 위해서
	public static AdminDAO getInstance(){ // 객체를 만드는 생성자는 무조건 static 
		
		if(adminDAO == null) { // Admin객체가 없으면 (만들어져 있다면 생성되면 안되기때문)
			adminDAO = new AdminDAO(); // 객체 생성
		}
		
		return adminDAO; // 생성되어 있다면 기존 adminDAO객체의 주소를 리턴
	}
	
	public void setConnection(Connection con){ // Connection객체를 받아 DB연결
		// Connection객체 1개를 받아서 멤버변수에 셋팅(Connection객체를 참조할수 있도록) 
		this.con = con;
	}
	
	// 1. 로그인폼에서 입력한 id와 pw가 담긴 adminBean객체로 회원인지 조회한 후 회원이면 그 id를 반환
	public String selectLoginId(UserBean admin){
		String loginId = null;
		String sql = "SELECT U_ID FROM USER_TABLE WHERE U_ID = ? AND U_PASSWORD=?";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			
			pstmt.setString(1, admin.getU_id()); // ? 값 설정
			pstmt.setString(2, admin.getU_password());
			
			rs = pstmt.executeQuery(); // 실행
			
			if(rs.next()) { // sql문에서 insert한 값이 1개라도 있으면
				
				/* [ 방법 1 ]
				 * 다시한번 확인하는 작업 할필요 없음 -> 이유 ? SQL문에서 데이터 존재한다는 것이 아이디와 비밀번호가 일치한다는 의미이기때문
				 
				String db_Password = rs.getString("u_password"); // DB에 저장된 비번
				String hash_Password = admin.getU_password(); // 사용자가 입력한 비번을 이미 암호화시켜 전송된 비번
				
				System.out.println("[selectLoginId]");
				System.out.println("db_Password : " + db_Password);
				System.out.println("hash_Password : " + hash_Password);
				
				if(db_Password.equals(hash_Password)) {
					loginId = admin.getU_id();
				}else {
					loginId = null;
				}
				*/
				
				// loginId = admin.getU_id(); // [방법 2]
				loginId = rs.getString("u_id"); // [방법3] (결과가 참이기 때문에 사용가능)
				System.out.println("**** u_id : " + loginId);
			
			} // if문 끝
			
		} catch (Exception e) {
			System.out.println("[AdminDAO]selectLoginId 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return loginId;
	}
	
	// id로 조회한 회원정보 조회
	public UserBean selectAdminInfo(String a_id) {
		UserBean adminInfo = null;
		
		// String sql = "SELECT * FROM USER_TABLE WHERE U_ID = ?"; // [방법 1] 순서1 
		String sql = "SELECT * FROM USER_TABLE WHERE U_ID = '" + a_id +"'"; // [방법 2]
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			
			// pstmt.setString(1, a_id); // // [방법 1] 순서2

			rs = pstmt.executeQuery(); // 실행
			
			if(rs.next()) { // sql문에서 insert한 값이 1개라도 있으면
				adminInfo = new UserBean(); // 기본값으로 채워짐
				
				adminInfo.setU_id(rs.getString("u_id")); // AdminDAO객체에 SQL결과로 반환받은 회원정보로 값 설정
				adminInfo.setU_grade(rs.getString("u_grade"));
				adminInfo.setU_name(rs.getString("u_name"));
				adminInfo.setU_email(rs.getString("u_email"));
				adminInfo.setU_call(rs.getString("u_call"));
				
			
			} // if문 끝
			
		} catch (Exception e) {
			System.out.println("[AdminDAO]selectAdminInfo 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return adminInfo;
	}
	
	// 관리자등록(USER_TABLE)
	public int insertAdmin(UserBean admin) {
		int insertAdminCount = 0;

		String sql = "INSERT INTO USER_TABLE(U_ID, U_GRADE, U_PASSWORD, U_NAME, U_EMAIL, U_CALL, U_JOINDATE) VALUES(?, ?, ?, ?, ?, ?, NOW())";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, admin.getU_id());
			pstmt.setString(2, admin.getU_grade());
			pstmt.setString(3, admin.getU_password());
			pstmt.setString(4, admin.getU_name());
			pstmt.setString(5, admin.getU_email());
			pstmt.setString(6, admin.getU_call());
	
			insertAdminCount = pstmt.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("[AdminDAO]insertAdmin 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs); // insert는 rs를 사용안했기때문에 close()해줄 필요 없음 (select에서는 해줘야함)
			close(pstmt);
		}
		
		return insertAdminCount;
	}
	// 관리자등록(ADDRESS_TABLE)
	public int insertAddr(Address addr) {
		int insertAddrCount = 0;
		
		String sql = "INSERT INTO ADDRESS_TABLE(U_ID, POSTCODE, ADDRESS1, ADDRESS2) VALUES(?, ?, ?, ?)";
		
		try {

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, addr.getU_id());
			pstmt.setInt(2, addr.getPostcode());
			pstmt.setString(3, addr.getAddress1());
			pstmt.setString(4, addr.getAddress2());
			
			insertAddrCount = pstmt.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("[AdminDAO]insertAddr 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs); // insert는 rs를 사용안했기때문에 close()해줄 필요 없음 (select에서는 해줘야함)
			close(pstmt);
		}
		
		return insertAddrCount;
	}
	
	
	
}
