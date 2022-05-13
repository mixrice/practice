package vo;

public class Address {
	private String u_id;
	
	private int postcode;
	
	private String address1;
	private String address2;
	
	public Address() {
		super();
	}
	
	public Address(int postcode, String address1, String address2) {
		super();
		this.postcode = postcode;
		this.address1 = address1;
		this.address2 = address2;
	}
	
	public Address(String u_id, int postcode, String address1, String address2) {
		super();
		this.u_id = u_id;
		this.postcode = postcode;
		this.address1 = address1;
		this.address2 = address2;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	
}
