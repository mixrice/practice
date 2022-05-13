package vo;

import java.util.Date;

public class Menu {
	// 멤버변수
	private String m_id;
	private String category;
	private String m_name;
	private int m_price;
	private String m_detail;
	private String m_status;
	private Date m_date;
	private String image;
	
	private int quantity; // 메뉴 주문수량 추가함(SQL문에는 없음)

	// 기본생성자
	public Menu() {
		super();
	}

	public Menu(String m_id, String category, String m_name, int m_price, int quantity) {
		super();
		this.m_id = m_id;
		this.category = category;
		this.m_name = m_name;
		this.m_price = m_price;
		this.quantity = quantity;
	}

	// quantity(주문수량)제외한 생성자
	public Menu(String m_id, String category, String m_name, int m_price, String m_detail, String m_status,
			Date m_date, String image) {
		super();
		this.m_id = m_id;
		this.category = category;
		this.m_name = m_name;
		this.m_price = m_price;
		this.m_detail = m_detail;
		this.m_status = m_status;
		this.m_date = m_date;
		this.image = image;
	}

	// m_date, quantity(주문수량)제외한 생성자 : m_date는 오늘 날짜인 now()로 처리함
	public Menu(String m_id, String category, String m_name, int m_price, String m_detail, String m_status,
			String image) {
		super();
		this.m_id = m_id;
		this.category = category;
		this.m_name = m_name;
		this.m_price = m_price;
		this.m_detail = m_detail;
		this.m_status = m_status;
		this.image = image;
	}

	
	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public int getM_price() {
		return m_price;
	}

	public void setM_price(int m_price) {
		this.m_price = m_price;
	}

	public String getM_detail() {
		return m_detail;
	}

	public void setM_detail(String m_detail) {
		this.m_detail = m_detail;
	}

	public String getM_status() {
		return m_status;
	}

	public void setM_status(String m_status) {
		this.m_status = m_status;
	}

	public Date getM_date() {
		return m_date;
	}

	public void setM_date(Date m_date) {
		this.m_date = m_date;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
