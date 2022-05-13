package vo;

import util.SHA256;

public class UserBean {
	
	/* 아래 6개는 회원가입 폼에 있음 */
	private String u_id;
	private String u_grade; /*회원가입 폼에서 숨김 - 첫 회원가입때 hidden으로 Normal값 전달 */
	private String u_password;
	private String u_name;
	private String u_email;
	private String u_call;
	
	/* 아래 3개는 회원가입 폼에 없음 */
	private String u_joindate;
	private int order_quantity; /* 향후 삭제 예정*/
	private int order_money; /* 향후 삭제 예정*/
	
	// 기본생성자 : (암호화하는 방법중 1번째 방법사용)
	public UserBean() {
		super();
	}
	
	// 생성자 추가
	public UserBean(String u_id, String u_grade, String u_password, String u_name, String u_email, String u_call) {
		super();
		this.u_id = u_id;
		this.u_grade = u_grade;
		// 생성자에서 바로 암호화하여 UserBean객체 생성 (암호화하는 방법중 3번째 방법사용)
		this.u_password = SHA256.encodeSHA256(u_password);
		this.u_name = u_name;
		this.u_email = u_email;
		this.u_call = u_call;
	}
	
	
	public String getU_id() {
		return u_id;
	}
	
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getU_grade() {
		return u_grade;
	}
	public void setU_grade(String u_grade) {
		this.u_grade = u_grade;
	}
	public String getU_password() {
		return u_password;
	}
	public void setU_password(String u_password) {
		this.u_password = u_password;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public String getU_email() {
		return u_email;
	}
	public void setU_email(String u_email) {
		this.u_email = u_email;
	}
	public String getU_call() {
		return u_call;
	}
	public void setU_call(String u_call) {
		this.u_call = u_call;
	}
	public String getU_joindate() {
		return u_joindate;
	}
	public void setU_joindate(String u_joindate) {
		this.u_joindate = u_joindate;
	}
	public int getOrder_quantity() {
		return order_quantity;
	}
	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}
	public int getOrder_money() {
		return order_money;
	}
	public void setOrder_money(int order_money) {
		this.order_money = order_money;
	}
	
}
