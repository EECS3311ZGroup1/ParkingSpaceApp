package yorku.eecs3311.observer;

import yorku.eecs3311.publisher.PaymentService;

public abstract class PaymentObserver extends Observer {
	public PaymentService subject; 
	public PaymentObserver(PaymentService subject) {
		super(subject);
		this.subject = subject; 
	}

}
