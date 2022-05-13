package vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

public class Cart { // 장바구니에 뿌릴때 담을 DTO 객체
	private String name;
	private int qty; // 수량
	private String image;
	private int price; // 가격
	
	private String m_id;
	private String category;
	private Date m_date;
	
	private String encoding_m_id; // ★★ 인코딩된 m_id 추가
	
	// 기본 생성자 public Cart(){}
	
	/*
	 * ★★ 링크 방식으로 파라미터 값을 전송하면 자동 인코딩이 되지않아
	 *     서버쪽에서 한글 파라미터를 받으면 한글이 깨진다.
	 *     그래서 m_id값을 UTF-8로 인코딩해서 반환해주는 메서드 정의함
	 */
	
	public String getEncoding_m_id() {
		
		try {
			encoding_m_id = URLEncoder.encode(m_id, "utf-8"); // 예외발생
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return encoding_m_id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
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
	public Date getM_date() {
		return m_date;
	}
	public void setM_date(Date m_date) {
		this.m_date = m_date;
	}
	
	
}
