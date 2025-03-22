package yorku.eecs3311.booking;

public class BookingBuilder {
	
    private int bookingID;
    private String lotName;
    private int spaceID;
    private int startHour;
    private int dur;
    private String payment;
    private double deposit;

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

    public Booking build() {
        return new Booking(bookingID, lotName, spaceID, startHour, dur, payment, deposit);
    }
    
}
