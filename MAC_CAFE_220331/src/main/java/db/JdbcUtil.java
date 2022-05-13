/*
 * DB관련 공통 기능 클래스
 */
package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

// 모든 메서드가 static임 : 객체 생성 없이 바로 메모리에 올라감 (바로 사용할 수 있도록 static 붙여줌)
public class JdbcUtil {
	// ★★ 먼저, context.xml에서 "MySQLDB"로 설정
	// 커넥션 풀에서 Connection객체를 얻어와 반환하는 메서드
	public static Connection getConnection(){
		Connection con = null;
		
		try {
			Context initCtx = new InitialContext(); // 톰캣자체에 있는 Context객체를 가져옴
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			
			// name으로 커넥션 풀인 DataSource 객체를 얻어와
			DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQLDB"); // "jdbc/MySQLDB" : META-INF/context.xml에 설정한 name 값을 가져와야함
			
			con = ds.getConnection(); // 커넥션 풀에서 Connection객체를 얻어와
			
			con.setAutoCommit(false); // <리턴하기전에> Connection객체의 트랙잭션을 '자동커밋' 되지않도록 하기위해 "false"설정을 해줘야함
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	/*
	 * Connection 객체를 닫아주는 메서드
	 */
	public static void close(Connection con) {
		try {
			con.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Statement 객체를 닫아주는 메서드
	 */
	public static void close(Statement stmt) {
		try {
			stmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * PreparedStatement 객체를 닫아주는 메서드
	 */
	public static void close(PreparedStatement pstmt) {
		try {
			pstmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * ResultSet 객체를 닫아주는 메서드
	 */
	public static void close(ResultSet rs) {
		try {
			rs.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**********************************************************************/
	
	/*
	 * 트랜잭션 중에 실행된 작업들을 '완료'시키는 메서드
	 * insert, update, delete 성공하면 commit 함
	 */
	
	public static void commit(Connection con){
		try {
			con.commit();
			System.out.println("commit success");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 트랜잭션 중에 실행된 작업들을 '취소'시키는 메서드
	 * insert, update, delete 실패하면 rollback 함
	 */
	
	public static void rollback(Connection con){
		try {
			con.rollback();
			System.out.println("rollback success");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
