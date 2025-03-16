package yorku.eecs3311.payment;

public class MobilePayment extends PaymentStrategy {

	public MobilePayment() {}
	
	@Override
	public boolean pay(double amount) {
		System.out.printf("Paid %.2f with Mobile Payment Successfully!", amount);
		return true;
	}

	@Override
	public boolean refund(double amount) {
		System.out.printf("Refunded %.2f to Mobile Payment Successfully!", amount);
		return true;
	}
}
