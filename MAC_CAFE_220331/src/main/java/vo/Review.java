package vo;

public class Review {
	private int review_num;
	private String u_id;
	private String m_id;
	private int rating;
	private String text;
	
	
	// 생성자
	public Review() {
		super();
	}

	// review_num제외 (이유? 자동 1씩 증가되므로)
	public Review(String u_id, String m_id, int rating, String text) {
		super();
		this.u_id = u_id;
		this.m_id = m_id;
		this.rating = rating;
		this.text = text;
	}

	public Review(int review_num, String u_id, String m_id, int rating, String text) {
		super();
		this.review_num = review_num;
		this.u_id = u_id;
		this.m_id = m_id;
		this.rating = rating;
		this.text = text;
	}

	
	public int getReview_num() {
		return review_num;
	}

	public void setReview_num(int review_num) {
		this.review_num = review_num;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
