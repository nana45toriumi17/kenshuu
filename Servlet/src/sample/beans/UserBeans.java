package sample.beans;

public class UserBeans {
	private String username;
	private String address;
	private String phone;

	//チェック処理
	public void checkValues() {
		if (username != null && address != null && phone != null) {
			username += "(チェック済)";
		}
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
