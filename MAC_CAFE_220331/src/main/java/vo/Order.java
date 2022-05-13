package vo;

import java.util.Date;

public class Order {
	private int order_num;
	private String u_id;
	private String u_email;
	private Date order_date;
	private String order_status;
	private int totalmoney;
	
	
	public Order() {
		super();
	}

	public Order(int order_num, String u_id, String u_email, Date order_date, String order_status, int totalmoney) {
		super();
		this.order_num = order_num;
		this.u_id = u_id;
		this.u_email = u_email;
		this.order_date = order_date;
		this.order_status = order_status;
		this.totalmoney = totalmoney;
	}

	
	public int getOrder_num() {
		return order_num;
	}

	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getU_email() {
		return u_email;
	}

	public void setU_email(String u_email) {
		this.u_email = u_email;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public int getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(int totalmoney) {
		this.totalmoney = totalmoney;
	}
	
	
	
}
