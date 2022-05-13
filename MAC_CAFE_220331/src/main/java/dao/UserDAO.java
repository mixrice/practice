// DB로 SQL구문을 전송하는 클래스

package dao;

import static db.JdbcUtil.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.SHA256;
import vo.Address;
import vo.UserBean;
import vo.UserPwChange;

public class UserDAO {
	// 멤버변수(전역변수 : 전체 메서드에서 사용 가능)  (DAO의 멤버변수는 private안해도 됨 (DTO의 멤버변수는 private해야함))
	private Connection con;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/** 싱글톤 패턴 : UserDAO객체 단 1개만 생성
	 *  이유? 외부클래스에서 "처음 생성된 UserDAO객체를 공유해서 사용하도록 하기 위해" 
	 **/
	
	// 1. 생성자는 무조건 private을 붙임(이유? 외부 클래스에서 생성자 호출 불가하여 직접적으로 객체생성 불가능하게 하기위해)
	private UserDAO() {}
	
	private static UserDAO userDAO; // 반드시 static이여야함 (getInstance()메서드에서 userDAO를 호출하여 사용하는데  getInstance()메서드가 static이기 때문)
	
	// 2. static이유? 객체를 생성하기 전에 이미 메모리에 올라간 'getInstance()메서드를 통해서만 UserDAO객체를 1개만 만들수' 있도록 하기 위해서
	public static UserDAO getInstance(){ // 객체를 만드는 생성자는 무조건 static 
		
		if(userDAO == null) { // User객체가 없으면 (만들어져 있다면 생성되면 안되기때문)
			userDAO = new UserDAO(); // 객체 생성
		}
		
		return userDAO; // 생성되어 있다면 기존 userDAO객체의 주소를 리턴
	}
	
	public void setConnection(Connection con){ // Connection객체를 받아 DB연결
		// Connection객체 1개를 받아서 멤버변수에 셋팅(Connection객체를 참조할수 있도록) 
		this.con = con;
	}
	
	/**************************************************************************/
	
	// 1. 로그인폼에서 입력한 id와 pw가 담긴 userBean객체로 회원인지 조회한 후 회원이면 그 id를 반환
	public String selectLoginId(UserBean user){
		String loginId = null;
		String sql = "SELECT U_ID FROM USER_TABLE WHERE U_ID = ? AND U_PASSWORD=?";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			
			pstmt.setString(1, user.getU_id()); // ? 값 설정
			pstmt.setString(2, user.getU_password());
			
			rs = pstmt.executeQuery(); // 실행
			
			if(rs.next()) { // sql문에서 insert한 값이 1개라도 있으면
				
				/* [ 방법 1 ]
				 * 다시한번 확인하는 작업 할필요 없음 -> 이유 ? SQL문에서 데이터 존재한다는 것이 아이디와 비밀번호가 일치한다는 의미이기때문
				 
				String db_Password = rs.getString("u_password"); // DB에 저장된 비번
				String hash_Password = user.getU_password(); // 사용자가 입력한 비번을 이미 암호화시켜 전송된 비번
				
				System.out.println("[selectLoginId]");
				System.out.println("db_Password : " + db_Password);
				System.out.println("hash_Password : " + hash_Password);
				
				if(db_Password.equals(hash_Password)) {
					loginId = user.getU_id();
				}else {
					loginId = null;
				}
				*/
				
				// loginId = user.getU_id(); // [방법 2]
				loginId = rs.getString("u_id"); // [방법3] (결과가 참이기 때문에 사용가능)
			
			} // if문 끝
			
		} catch (Exception e) {
			System.out.println("[UserDAO]selectLoginId 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return loginId;
	}

	// id로 조회한 회원정보 조회
	public UserBean getUserInfo(String u_id) {
		UserBean userInfo = null;
		
		// String sql = "SELECT * FROM USER_TABLE WHERE U_ID = ?"; // [방법 1] 순서1 
		String sql = "SELECT * FROM USER_TABLE WHERE U_ID = '" + u_id +"'"; // [방법 2]
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			
			// pstmt.setString(1, u_id); // // [방법 1] 순서2

			rs = pstmt.executeQuery(); // 실행
			
			if(rs.next()) { // sql문에서 insert한 값이 1개라도 있으면
				userInfo = new UserBean(); // 기본값으로 채워짐
				
				userInfo.setU_id(rs.getString("u_id")); // UserBean객체에 SQL결과로 반환받은 회원정보로 값 설정
				userInfo.setU_grade(rs.getString("u_grade"));
				userInfo.setU_name(rs.getString("u_name"));
				userInfo.setU_email(rs.getString("u_email"));
				userInfo.setU_call(rs.getString("u_call"));
				
			
			} // if문 끝
			
		} catch (Exception e) {
			System.out.println("[UserDAO]getUserInfo 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return userInfo;
	}

	// 로그인한 사용자의 id로 지난달 구매한 금액 조회(이때, status = 'get'(주문승인))
	public int getLastMonthMoney(String u_id) {
		int lastMonthMoney = 0;
		
		String sql = "SELECT SUM(TOTALMONEY) FROM ORDER_TABLE "
				+ "WHERE U_ID = ? AND YEAR(ORDER_DATE) = YEAR(now()) "
				+ "AND MONTH(ORDER_DATE) = MONTH(now())-1 AND ORDER_STATUS = 'GET'"; // MONTH(now())-1 : 지난달 
		          // 주문한 날짜(2022-2-15, 2022-3-1, 2022-3-30)의 달과 오늘 날짜(2022-4-4)의 달(4)-1을 비교하여 같으면
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			

			rs = pstmt.executeQuery(); // 실행
			pstmt.setString(1, u_id);
			
			if(rs.next()) { // sql문에서 insert한 값이 1개라도 있으면
				lastMonthMoney = rs.getInt("SUM(TOTALMONEY)");
			
			} // if문 끝
			
		} catch (Exception e) {
			System.out.println("[UserDAO]getLastMonthMoney 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		return lastMonthMoney;
	}

	// 사용자의 등급을 'VIP'로 업그레이드
	public int upGrade(UserBean user) {
		int gradeCount = 0; // 업데이트 성공여부 (0: 실패, 1 : 성공)
		
		String sql = "UPDATE USER_TABLE SET U_GRADE = 'VIP' WHERE U_ID = ?";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			
			pstmt.setString(1, user.getU_id());

			gradeCount = pstmt.executeUpdate(); // 업데이트에 성공하면 1을 리턴받음
			
			
		} catch (Exception e) {
			System.out.println("[UserDAO]upGrade 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs);
			close(pstmt);
		}
		
		return gradeCount;
	}

	// 사용자의 등급을 'Normal'로 다운
	public int downGrade(UserBean user) {
		int gradeCount = 0; // 업데이트 성공여부 (0: 실패, 1 : 성공)
		
		String sql = "UPDATE USER_TABLE SET U_GRADE = 'NORMAL' WHERE U_ID = ?";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			
			pstmt.setString(1, user.getU_id());

			gradeCount = pstmt.executeUpdate(); // 업데이트에 성공하면 1을 리턴받음
			
			
		} catch (Exception e) {
			System.out.println("[UserDAO]downGrade 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs);
			close(pstmt);
		}
		
		return gradeCount;
	}

	// 사용자 등급 별 세일 비율 조회
	public double selectSaleRate(String u_grade) {
		double saleRate = 1.0;
		
		String sql = "SELECT SALERATE FROM GRADE_TABLE WHERE GRADE = '" + u_grade + "'";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 

			rs = pstmt.executeQuery(); // 실행
			
			if(rs.next()) {
				saleRate = rs.getInt("salerate")/100.0; // Normal = 5/100.0 = 0.05, VIP = 10/100.0 = 0.1
			
			} // if문 끝
			
		} catch (Exception e) {
			System.out.println("[UserDAO]selectSaleRate 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return saleRate;
	}

	// 아이디 찾기
	public UserBean findId(String u_email) {
		
		UserBean userInfo =null;
		String sql = "SELECT * FROM USER_TABLE WHERE U_EMAIL = '" + u_email+"'";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userInfo = new UserBean(); // 기본값으로 채워진 UserBean객체에
				userInfo.setU_id(rs.getString("u_id")); // 조회한 id값 셋팅
				userInfo.setU_email(rs.getString("u_email"));
			}
			
		}catch (Exception e) {
			System.out.println("[UserDAO]findId 에러 : " + e); // e: 예외종류 + 예외 메세지
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return userInfo;
	}

	public UserBean findHashPw(String u_id, String u_email) {
		
		UserBean userInfo =null;
		String sql = "SELECT U_NAME FROM USER_TABLE WHERE U_ID =? AND U_EMAIL = ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u_id);
			pstmt.setString(2, u_email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userInfo = new UserBean(); // 기본값으로 채워진 UserBean객체에
				
				// 조회한 id값을 셋팅
				userInfo.setU_id(u_id);
				userInfo.setU_email(u_email);
				userInfo.setU_name(rs.getString("u_name")); // 조회한 u_name값 셋팅
			
			}
			
		}catch (Exception e) {
			System.out.println("[UserDAO]findHashPw 에러 : " + e); // e: 예외종류 + 예외 메세지
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return userInfo;
	}

	// 발급 받은 임시비번(랜덤비번)을 다시 DB의 해당 사용자의 비번으로 수정하여 사용자가 임시비번(랜덤비번)으로 로그인할 수 있도록 함
	public int setHashPw(String u_id, String u_email, String random_password) {
		
		int setHashPwCount = 0; // 업데이트 성공여부 (0: 실패, 1 : 성공)
		
		String sql = "UPDATE USER_TABLE SET U_PASSWORD = ? WHERE U_ID = ? AND U_EMAIL = ?";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			
			pstmt.setString(1, SHA256.encodeSHA256(random_password)); // 발급받은 임시번호를 암호화하여 DB에 저장
			//pstmt.setString(1, random_password);
			pstmt.setString(2, u_id);
			pstmt.setString(3, u_email);

			setHashPwCount = pstmt.executeUpdate(); // 업데이트에 성공하면 1을 리턴받음
			
			
		} catch (Exception e) {
			System.out.println("[UserDAO]setHashPw 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs); // update는 rs를 사용안했기때문에 close()해줄 필요 없음 (select에서는 해줘야함)
			close(pstmt);
		}
		
		return setHashPwCount;
	}

	// [방법 1] : 미리 암호화된 비번을 전송받았으므로 그대로 비번 저장
	public int changePw(UserPwChange userPwChange) {
		int changePwCount = 0; // 업데이트 성공여부 (0: 실패, 1 : 성공)
		
		String sql = "UPDATE USER_TABLE SET U_PASSWORD = ? WHERE U_ID = ? AND U_PASSWORD = ?";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			
			pstmt.setString(1, userPwChange.getNew_password()); // 그대로 저장
			pstmt.setString(2, userPwChange.getU_id());
			pstmt.setString(3, SHA256.encodeSHA256(userPwChange.getPre_password()));

			changePwCount = pstmt.executeUpdate(); // 업데이트에 성공하면 1을 리턴받음
			
		} catch (Exception e) {
			System.out.println("[UserDAO]changePw 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs); // update는 rs를 사용안했기때문에 close()해줄 필요 없음 (select에서는 해줘야함)
			close(pstmt);
		}
		
		return changePwCount;
	}
	
	// [방법 2] : 암호화되지 않은 비번을 전송받았으므로 비번을 암호화시켜 저장
	public int changeHashPw(UserPwChange userPwChange) {
		int changePwCount = 0; // 업데이트 성공여부 (0: 실패, 1 : 성공)
		
		String sql = "UPDATE USER_TABLE SET U_PASSWORD = ? WHERE U_ID = ? AND U_PASSWORD = ?";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			
			pstmt.setString(1, SHA256.encodeSHA256(userPwChange.getNew_password())); // 암호화 시켜 저장
			pstmt.setString(2, userPwChange.getU_id());
			pstmt.setString(3, SHA256.encodeSHA256(userPwChange.getPre_password()));

			changePwCount = pstmt.executeUpdate(); // 업데이트에 성공하면 1을 리턴받음
			
		} catch (Exception e) {
			System.out.println("[UserDAO]changeHashPw 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs); // update는 rs를 사용안했기때문에 close()해줄 필요 없음 (select에서는 해줘야함)
			close(pstmt);
		}
		
		return changePwCount;
	}
	
	// 회원등록(USER_TABLE)
	public int insertUser(UserBean user) {
		int insertUserCount = 0;

		String sql = "INSERT INTO USER_TABLE(U_ID, U_GRADE, U_PASSWORD, U_NAME, U_EMAIL, U_CALL, U_JOINDATE) VALUES(?, ?, ?, ?, ?, ?, NOW())";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getU_id());
			pstmt.setString(2, user.getU_grade());
			pstmt.setString(3, user.getU_password());
			pstmt.setString(4, user.getU_name());
			pstmt.setString(5, user.getU_email());
			pstmt.setString(6, user.getU_call());
	
			insertUserCount = pstmt.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("[UserDAO]insertUser 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs); // insert는 rs를 사용안했기때문에 close()해줄 필요 없음 (select에서는 해줘야함)
			close(pstmt);
		}
		
		return insertUserCount;
	}
	
	// 회원등록(ADDRESS_TABLE)
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
			System.out.println("[UserDAO]insertAddr 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs); // insert는 rs를 사용안했기때문에 close()해줄 필요 없음 (select에서는 해줘야함)
			close(pstmt);
		}
		
		return insertAddrCount;
	}
	
	// 회원 수정(USER_TABLE)
	public int updateUser(UserBean user) {
		int updateUserCount = 0;
	
		String sql = "UPDATE USER_TABLE SET U_NAME =? ,U_EMAIL =? ,U_CALL = ? WHERE U_ID=?";

		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, user.getU_name());
			pstmt.setString(2, user.getU_email());
			pstmt.setString(3, user.getU_call());
			pstmt.setString(4, user.getU_id());
			
			updateUserCount = pstmt.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("[UserDAO]updateUser 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs); // insert는 rs를 사용안했기때문에 close()해줄 필요 없음 (select에서는 해줘야함)
			close(pstmt);
		}
		
		return updateUserCount;
	}
	
	// 회원 수정(ADDRESS_TABLE)
	public int updateAddr(Address addr) {
		int updateAddrCount = 0;
	
		String sql = "UPDATE ADDRESS_TABLE SET POSTCODE=?, ADDRESS1=?, ADDRESS2 =? WHERE U_ID=?";
		try {
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, addr.getPostcode());
			pstmt.setString(2, addr.getAddress1());
			pstmt.setString(3, addr.getAddress2());
			pstmt.setString(4, addr.getU_id());
			
			updateAddrCount = pstmt.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("[UserDAO]UpdateAddr 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs); // insert는 rs를 사용안했기때문에 close()해줄 필요 없음 (select에서는 해줘야함)
			close(pstmt);
		}
		
		return updateAddrCount;
	}
	
	
	// 회원 탈퇴
	public int deleteUser(String u_id) {
		int deleteUserCount = 0;
	
		String sql = "DELETE FROM USER_TABLE WHERE U_ID=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u_id);
			
			deleteUserCount = pstmt.executeUpdate();
			System.out.println("deleteUserCount : " + deleteUserCount);
			
		}catch (Exception e) {
			System.out.println("[UserDAO]DeleteUser 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs); // insert는 rs를 사용안했기때문에 close()해줄 필요 없음 (select에서는 해줘야함)
			close(pstmt);
		}
		
		return deleteUserCount;
	}
	
	public int deleteAddr(String u_id) {
		int deleteAddrCount = 0;
	
		String sql = "DELETE FROM ADDRESS_TABLE WHERE U_ID=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, u_id);
			
			deleteAddrCount = pstmt.executeUpdate();
			System.out.println("deleteAddrCount : " + deleteAddrCount);
			
		}catch (Exception e) {
			System.out.println("[UserDAO]deleteAddr 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs); // insert는 rs를 사용안했기때문에 close()해줄 필요 없음 (select에서는 해줘야함)
			close(pstmt);
		}
		
		return deleteAddrCount;
	}

	public UserBean selectUserInfo(String viewId) {
		UserBean userInfo = null;
		
		String sql = "SELECT * FROM USER_TABLE WHERE U_ID = '" + viewId+"'"; 
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userInfo = new UserBean();
				
				userInfo.setU_id(rs.getString("u_id"));
				userInfo.setU_grade(rs.getString("u_grade"));
				userInfo.setU_name(rs.getString("u_name"));
				userInfo.setU_email(rs.getString("u_email"));
				userInfo.setU_call(rs.getString("u_call"));
			}
		}catch (Exception e) {
			System.out.println("[UserDAO]selectUserInfo 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return userInfo;
	}

	public Address selectUserAddrInfo(String viewId) {
		Address userAddrInfo = null;
		String sql = "select * from address_table where u_id = '" + viewId +"'";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userAddrInfo = new Address(); // 기본값으로 채워진 Address객체에
				
				userAddrInfo.setU_id(rs.getString("u_id"));
				userAddrInfo.setPostcode(rs.getInt("postcode"));
				userAddrInfo.setAddress1(rs.getString("address1"));
				userAddrInfo.setAddress2(rs.getString("address2"));
			}
		}catch (Exception e) {
			System.out.println("[UserDAO]selectUserAddrInfo 에러 : " + e);
		}finally {
			close(rs);
			close(pstmt);
		}
		return userAddrInfo;
	}
}
