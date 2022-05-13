package vo; // DTO


public class UserPwChange {
	
	// 멤버변수
	private String u_id;
	private String pre_password;
	private String new_password;
	
	// 반드시 기본생성자
	public UserPwChange() {
		super();
	}

	public UserPwChange(String u_id, String pre_password, String new_password) {
		super();
		this.u_id = u_id;
		this.pre_password = pre_password;
		this.new_password = new_password;
		
		// [방법 3] : DAO에서 암호화된 비번을 전달받았으므로 그대로 저장하는 changePw()메소드를 호출해서 수정해야함
		// this.new_password = SHA256.encodeSHA256(new_password);
	}

	// 메서드
	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getPre_password() {
		return pre_password;
	}

	public void setPre_password(String pre_password) {
		this.pre_password = pre_password;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}
	
	
}
