package dao;

import static db.JdbcUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import vo.Menu;

public class MenuDAO {
	// 멤버변수(전역변수 : 전체 메서드에서 사용 가능)  (DAO의 멤버변수는 private안해도 됨 (DTO의 멤버변수는 private해야함))
	private Connection con;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/** 싱글톤 패턴 : MenuDAO객체 단 1개만 생성
	 *  이유? 외부클래스에서 "처음 생성된 MenuDAO객체를 공유해서 사용하도록 하기 위해" 
	 **/
	
	// 1. 생성자는 무조건 private을 붙임(이유? 외부 클래스에서 생성자 호출 불가하여 직접적으로 객체생성 불가능하게 하기위해)
	private MenuDAO() {}
	
	private static MenuDAO menuDAO; // 반드시 static이여야함 (getInstance()메서드에서 MenuDAO를 호출하여 사용하는데  getInstance()메서드가 static이기 때문)
	
	// 2. static이유? 객체를 생성하기 전에 이미 메모리에 올라간 'getInstance()메서드를 통해서만 MenuDAO객체를 1개만 만들수' 있도록 하기 위해서
	public static MenuDAO getInstance(){ // 객체를 만드는 생성자는 무조건 static 
		
		if(menuDAO == null) { // Menu객체가 없으면 (만들어져 있다면 생성되면 안되기때문)
			menuDAO = new MenuDAO(); // 객체 생성
		}
		
		return menuDAO; // 생성되어 있다면 기존 menuDAO객체의 주소를 리턴
	}
	
	public void setConnection(Connection con){ // Connection객체를 받아 DB연결
		// Connection객체 1개를 받아서 멤버변수에 셋팅(Connection객체를 참조할수 있도록) 
		this.con = con;
	}

	// (이미지를 클릭하면) 메뉴 상세 정보 조회
	public Menu selectMenuInfo(String m_id){
		Menu menuInfo = null;
		String sql = "SELECT * FROM MENU_TABLE WHERE M_ID = ?";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			pstmt.setString(1, m_id); // ? 값 설정
			rs = pstmt.executeQuery(); // 실행
			
			if(rs.next()) { 
				menuInfo = new Menu(rs.getString("m_id"), 
						            rs.getString("category"), 
						            rs.getString("m_name"),
						            
						            rs.getInt("m_price"), 
						            
						            rs.getString("m_detail"), 
						            rs.getString("m_status"), 
						            
						            rs.getDate("m_date"), 
						            
						            rs.getString("image"));
			
			} // if문 끝
			
		} catch (Exception e) {
			System.out.println("[MenuDAO]selectMenuInfo 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		return menuInfo;
	}
	
	// 새로운 메뉴 추가
	public int insertNewMenu(Menu newMenu){
		int insertNewMenuCount = 0;
		
		String sql = "INSERT INTO MENU_TABLE VALUES(?, ?, ?, ?, ?, ?, NOW(), ?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, newMenu.getM_id());
			pstmt.setString(2, newMenu.getCategory());
			pstmt.setString(3, newMenu.getM_name());
			
			pstmt.setInt(4, newMenu.getM_price()); // int타입
			
			pstmt.setString(5, newMenu.getM_detail());
			pstmt.setString(6, newMenu.getM_status());
			pstmt.setString(7, newMenu.getImage());
	
			insertNewMenuCount = pstmt.executeUpdate(); // 업데이트 성공하면 1리턴
			
		}catch (Exception e) {
			System.out.println("[MenuDAO]insertNewMenu 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs); // insert는 rs를 사용안했기때문에 close()해줄 필요 없음 (select에서는 해줘야함)
			close(pstmt);
		}
		return insertNewMenuCount;
	}

	// 카테고리 중 set목록 조회
	public ArrayList<Menu> selectSetList() {
		ArrayList<Menu> setList = null;
		String sql = "SELECT * FROM MENU_TABLE WHERE CATEGORY = 'SET'";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			rs = pstmt.executeQuery(); // 실행
			
			if(rs.next()) { 
				setList = new ArrayList<Menu>();
				
				do {
					// "조회한 set메뉴정보값으로 체워진 menu객체" 생성하여 바로 ArryList에 추가
					setList.add(new Menu(rs.getString("m_id"),
								         rs.getString("category"),
									     rs.getString("m_name"),
									 	 rs.getInt("m_price"),
										 rs.getString("m_detail"),
										 rs.getString("m_status"),
										 rs.getDate("m_date"),
										 rs.getString("image")
						     	));
				}while(rs.next());
			
			} // if문 끝
			
		} catch (Exception e) {
			System.out.println("[MenuDAO]selectSetList 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		return setList;
	}
	
	// 카테고리 중 burger목록 조회
	public ArrayList<Menu> selectBurgerList() {
		ArrayList<Menu> burgerList = null;
		String sql = "SELECT * FROM MENU_TABLE WHERE CATEGORY = 'BURGER'";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			rs = pstmt.executeQuery(); // 실행
			
			if(rs.next()) { 
				burgerList = new ArrayList<Menu>();
				
				do {
					// "조회한 burger메뉴정보값으로 체워진 menu객체" 생성하여 바로 ArryList에 추가
					burgerList.add(new Menu(rs.getString("m_id"),
								            rs.getString("category"),
									        rs.getString("m_name"),
									     	rs.getInt("m_price"),
										    rs.getString("m_detail"),
										    rs.getString("m_status"),
										    rs.getDate("m_date"),
										    rs.getString("image")
						           ));
				}while(rs.next());
			
			} // if문 끝
			
		} catch (Exception e) {
			System.out.println("[MenuDAO]selectBurgerList 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		return burgerList;
	}
	
	// 카테고리 중 drink목록 조회
	public ArrayList<Menu> selectDrinkList() {
		
		ArrayList<Menu> drinkList = null;
		String sql = "SELECT * FROM MENU_TABLE WHERE CATEGORY = 'DRINK'";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			rs = pstmt.executeQuery(); // 실행
			
			if(rs.next()) { 
				drinkList = new ArrayList<Menu>();
				
				do {
					// "조회한 drink 메뉴정보값으로 체워진 menu객체" 생성하여 바로 ArryList에 추가
					drinkList.add(new Menu(rs.getString("m_id"),
								           rs.getString("category"),
									       rs.getString("m_name"),
									 	   rs.getInt("m_price"),
										   rs.getString("m_detail"),
										   rs.getString("m_status"),
										   rs.getDate("m_date"),
										   rs.getString("image")
						     	 ));
				}while(rs.next());
			
			} // if문 끝
			
		} catch (Exception e) {
			System.out.println("[MenuDAO]selectDrinkList 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		return drinkList;
	}
	
	// 카테고리 중 side목록 조회
	public ArrayList<Menu> selectSideList() {
		
		ArrayList<Menu> sidekList = null;
		String sql = "SELECT * FROM MENU_TABLE WHERE CATEGORY = 'SIDE'";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			rs = pstmt.executeQuery(); // 실행
			
			if(rs.next()) { 
				sidekList = new ArrayList<Menu>();
				
				do {
					// "조회한 side 메뉴정보값으로 체워진 menu객체" 생성하여 바로 ArryList에 추가
					sidekList.add(new Menu(rs.getString("m_id"),
								           rs.getString("category"),
									       rs.getString("m_name"),
									 	   rs.getInt("m_price"),
										   rs.getString("m_detail"),
										   rs.getString("m_status"),
										   rs.getDate("m_date"),
										   rs.getString("image")
						     	 ));
				}while(rs.next());
			
			} // if문 끝
			
		} catch (Exception e) {
			System.out.println("[MenuDAO]selectSideList 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		return sidekList;
	}
	
	// 카테고리 중 dessert목록 조회
	public ArrayList<Menu> selectDessertList() {
		
		ArrayList<Menu> dessertList = null;
		String sql = "SELECT * FROM MENU_TABLE WHERE CATEGORY = 'DESSERT'";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			rs = pstmt.executeQuery(); // 실행
			
			if(rs.next()) { 
				dessertList = new ArrayList<Menu>();
				
				do {
					// "조회한 dessert 메뉴정보값으로 체워진 menu객체" 생성하여 바로 ArryList에 추가
					dessertList.add(new Menu(rs.getString("m_id"),
								             rs.getString("category"),
									         rs.getString("m_name"),
									 	     rs.getInt("m_price"),
										     rs.getString("m_detail"),
										     rs.getString("m_status"),
										     rs.getDate("m_date"),
										     rs.getString("image")
						     	    ));
				}while(rs.next());
			
			} // if문 끝
			
		} catch (Exception e) {
			System.out.println("[MenuDAO]selectDessertList 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs);
			close(pstmt);
		}
		return dessertList;
	}
	
	// 메뉴 수정
	public int updateMenu(Menu menu) {
		int updateMenuCount = 0;
		
		// [방법 1] : 기존에 이미지를 그대로 사용하려면
		String sql = "UPDATE MENU_TABLE SET CATEGORY=?, M_NAME=?, M_PRICE=?, M_DETAIL=?, M_STATUS=?, M_DATE=NOW()";
		
		if(menu.getImage() != null) {
			sql += ", IMAGE= '"+ menu.getImage() +"'"; // ★ 콤마 포함하기 (? 넣으면 안됨 -> 만약 menu.getImage() == null값일 경우 ?의 순서가 바꿔지기 떄문)
		}
		
		sql += " WHERE M_ID = ?"; // ★ 한칸 띄우기
		
		// [방법 2] : 이미지를 다시 올리는 경우
		// String sql = "UPDATE MENU_TABLE SET CATEGORY=?, M_NAME=?, M_PRICE=?, M_DETAIL=?, M_STATUS=?, M_DATE=NOW(), IMAGE=? WHERE M_ID = ?";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			
			// [방법 1] : 기존에 이미지를 그대로 사용하려면
			
			pstmt.setString(1, menu.getCategory());
			pstmt.setString(2, menu.getM_name());
			
			pstmt.setInt(3, menu.getM_price()); // int타입
			
			pstmt.setString(4, menu.getM_detail());
			pstmt.setString(5, menu.getM_status());
			
			// pstmt.setString(6, menu.getImage()); // 있으면 안됨 (if문이 거짓일때 문제 발생)
			pstmt.setString(6, menu.getM_id());
			
			// [방법 2] : 이미지를 다시 올리는 경우
			/*
			pstmt.setString(1, menu.getCategory());
			pstmt.setString(2, menu.getM_name());
			
			pstmt.setInt(3, menu.getM_price()); // int타입
			
			pstmt.setString(4, menu.getM_detail());
			pstmt.setString(5, menu.getM_status());
			pstmt.setString(6, menu.getImage());
			pstmt.setString(7, menu.getM_id());
			*/
			updateMenuCount = pstmt.executeUpdate();
			System.out.println("*** updateMenuCount : " + updateMenuCount);
			
		} catch (Exception e) {
			System.out.println("[MenuDAO]updateMenu 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs);
			close(pstmt);
		}
		
		return updateMenuCount;
	}
	
	// 메뉴 삭제
	public int deleteMenu(String m_id) {
		int deleteMenuCount = 0;
		
	
		String sql = "DELETE FROM MENU_TABLE WHERE M_ID = ?";
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			
			pstmt.setString(1, m_id);
			
			deleteMenuCount = pstmt.executeUpdate();
			
			System.out.println("*** deleteMenuCount : " + deleteMenuCount);
			
		} catch (Exception e) {
			System.out.println("[MenuDAO]deleteMenu 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			// close(rs);
			close(pstmt);
		}
		
		return deleteMenuCount;
	}

	public int insertOrderMenu(String u_id, String u_email, ArrayList<Menu> menuList, int saleTotalMoney) {
		int insertOrderMenuCount = 0;
		
		// 1. [사용자모드] : 주문테이블(order_table)에 'order(주문대기)'상태로 주문을 넣으면, [관리자모드] : '실시간 주문관리'에서 order_status를 'get(주문승인)', 'cancel(주문취소)'로 변경해줘야함
		String sql = "INSERT INTO ORDER_TABLE(U_ID, U_EMAIL, ORDER_DATE, ORDER_STATUS, TOTALMONEY) VALUES(?,?, NOW(), 'ORDER', ?)";
		
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		
		PreparedStatement pstmt3 = null;
		ResultSet rs3 = null;
		
		try {
			pstmt = con.prepareStatement(sql); // prepareStatement 객체생성 
			
			pstmt.setString(1, u_id);
			pstmt.setString(2, u_email);
			pstmt.setInt(3, saleTotalMoney);
			
			insertOrderMenuCount = pstmt.executeUpdate();
			
			System.out.println("*** insertOrderMenuCount : " + insertOrderMenuCount);
			
			
			// 2. 주문 테이블(order_table)에서 '가장 최근 주문 번호'를 얻어와(방금 1에서 insert한 주문번호 조회)
			pstmt2 = con.prepareStatement("SELECT MAX(ORDER_NUM) FROM ORDER_TABLE WHERE U_ID = ?");
			pstmt2.setString(1, u_id);
			rs2 = pstmt2.executeQuery();
			
			if(rs2.next()) { // 최근 주문번호가 있으면
				for(int i =0; i<menuList.size(); i++) { // 주문한 내역을 하나씩 가져옴
					// 주문 상세테이블(orderDetail_table)에 insert함 (주문번호 클릭하면 '주문상세보기' 정보)
					pstmt3 = con.prepareStatement("INSERT INTO ORDERDETAIL_TABLE(M_ID, ORDER_NUM, QUANTITY, M_NAME, M_PRICE) VALUES(?,?,?,?,?)");
					
					Menu menu = menuList.get(i);
					
					pstmt3.setString(1, menu.getM_id()); // 주문한 메뉴 ID
					pstmt3.setInt(2, rs2.getInt("MAX(ORDER_NUM)")); // 주문번호  (★★ 주의 : 1에서 insert한 가장 최근 주문번호)
					pstmt3.setInt(3, menu.getQuantity()); // 주문한 메뉴의 수량
					pstmt3.setString(4, menu.getM_name()); // 주문한 메뉴 명
					pstmt3.setInt(5, menu.getM_price()); // 주문한 메뉴 가격
					
					insertOrderMenuCount = pstmt3.executeUpdate();
					System.out.println("*** insertOrderMenuCount : " + insertOrderMenuCount);
					
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("[MenuDAO]insertOrderMenu 에러 : " + e); // e: 예외종류 + 예외 메세지
		}finally {
			close(rs2);
			close(pstmt);
			close(pstmt2);
			close(pstmt3);
		}
		
		
		return insertOrderMenuCount;
	}
}
