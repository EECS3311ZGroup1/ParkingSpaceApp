package yorku.eecs3311.booking;

public class BookingBuilder {
	
    private int bookingID;
    private String lotName;
    private int spaceID;
    private String date;
    private int startHour;
    private int dur;
    private String payment;
    private double deposit;
    private String email;
    private boolean isExtended;
	private boolean isCancelled;
	private boolean isCheckedOut; 

    public BookingBuilder setBookingID(int bookingID) {
        this.bookingID = bookingID;
        return this;
    }

    public BookingBuilder setLotName(String lotName) {
        this.lotName = lotName;
        return this;
    }

    public BookingBuilder setSpaceID(int spaceID) {
        this.spaceID = spaceID;
        return this;
    }
    
    public BookingBuilder setDate(String date) {
    	this.date = date;
    	return this;
    }

    public BookingBuilder setStartHour(int startHour) {
        this.startHour = startHour;
        return this;
    }

    public BookingBuilder setDur(int dur) {
        this.dur = dur;
        return this;
    }
    
    public BookingBuilder setPaymentMethod(String method) {
        this.payment = method;
        return this;
    }

    public BookingBuilder setDeposit(double deposit) {
        this.deposit = deposit;
        return this;
    }
    
    public BookingBuilder setEmail(String email) {
    	this.email = email;
    	return this;
    }
    
    public BookingBuilder isExtended(boolean isExtended) {
    	this.isExtended = isExtended;
    	return this;
    }
    
    public BookingBuilder isCancelled(boolean isCancelled) {
    	this.isCancelled = isCancelled;
    	return this;
    }
    
    public BookingBuilder isCheckedOut(boolean isCheckedOut) {
    	this.isCheckedOut = isCheckedOut;
    	return this;
    }

    public Booking build() {
        return new Booking(bookingID, lotName, spaceID, 
        				   date, startHour, dur,
        				   payment, deposit, email,
        				   isExtended, isCancelled, isCheckedOut);
    }
    
}
