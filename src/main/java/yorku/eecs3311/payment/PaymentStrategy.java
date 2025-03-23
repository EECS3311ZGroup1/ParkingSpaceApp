package yorku.eecs3311.payment;

public abstract class PaymentStrategy {
	public abstract boolean pay(double amnount);
	public abstract boolean refund(double amount);
}
