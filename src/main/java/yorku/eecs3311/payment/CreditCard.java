package yorku.eecs3311.payment;

public class CreditCard extends PaymentStrategy{
	
	public CreditCard() {}

	@Override
	public boolean pay(double amount) {
		System.out.printf("Paid %.2f with Credit Card Successfully!", amount);
		return true;
	}

	@Override
	public boolean refund(double amount) {
		System.out.printf("Refunded %.2f to Credit Card Successfully!", amount);
		return true;
	}
}
