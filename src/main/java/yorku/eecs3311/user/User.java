package yorku.eecs3311.user;

public abstract class User {
	private String email;
	private String pwd;
	private String id;
	private double rate;
	
	/*
	 * Overloaded constructors
	 */
	public User(String email, String pwd, String id, double rate) {
		this.email = email;
		this.pwd = pwd;
		this.id = id;
		this.rate = rate;
	}
	
	public User(String email, String pwd, String id) {
		this.email = email;
		this.pwd = pwd;
		this.id = id;
	}

	// Getters
	public String getEmail() { return email; }
	public String getPwd() { return pwd; }
	public String getId() { return id; }
	public double getRate() { return rate; }
	public void setRate(double rate) { this.rate = rate; }

	@Override
	public String toString() {
		return "User [email=" + email + ", pwd=" + pwd + ", id=" + id + ", rate=" + rate + "]";
	}
}
