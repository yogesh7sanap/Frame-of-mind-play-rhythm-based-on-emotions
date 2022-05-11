package helper;

public class UserModel {
    private String uid;
    private String fname;
    private String phone;
    private String uname;
    private String password;
    private String address;
    private String udate;

	

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the udate
     */
    public String getUdate() {
        return udate;
    }

    /**
     * @param udate the udate to set
     */
    public void setUdate(String udate) {
        this.udate = udate;
    }

	
	
}
