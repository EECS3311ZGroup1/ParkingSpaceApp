package yorku.eecs3311;

public class Student extends User {
	public Student(String email, String pwd, String id) {
		super(email, pwd, id);
		setRate(ParkingRate.STUDENT.getValue());
	}
}
