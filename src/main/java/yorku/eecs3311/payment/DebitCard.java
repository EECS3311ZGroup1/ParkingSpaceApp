package yorku.eecs3311.payment;

public class DebitCard extends PaymentStrategy{

	public DebitCard() {}
	
	@Override
	public boolean pay(double amount) {
		System.out.printf("Paid %.2f with Debit Card Successfully!", amount);
		return true;
	}

	@Override
	public boolean refund(double amount) {
		System.out.printf("Refunded %.2f to Debit Card Successfully!", amount);
		return true;
	}
}
