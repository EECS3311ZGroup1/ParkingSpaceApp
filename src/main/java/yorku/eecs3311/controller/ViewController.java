package yorku.eecs3311.controller;

import java.util.List;
import yorku.eecs3311.manager.ManagerAccount;
import yorku.eecs3311.model.ModelBooking;
import yorku.eecs3311.model.ModelLogin;
import yorku.eecs3311.model.ModelRegistration;
import yorku.eecs3311.parking.ParkingSpace;
import yorku.eecs3311.user.LoggedInUser;

public class ViewController {
	
	// Views
	private ViewMain mainView;
	private ViewHero heroView;
	private ViewRegistration registrationView;
	private ViewLogin loginView;
	private ViewDashboard dashboardView;
	
	// Models
	private ModelRegistration registrationModel;
	private ModelLogin loginModel;
	private ModelBooking bookingModel;
	
	// Booking info
	private String selectedSpace;
	private String selectedDate;
	private String selectedTime;
	private String selectedDuration;
	private String selectedPlateNumber;
	private String selectedPaymentMethod;
	
	private LoggedInUser loggedInUser;
	List<ParkingSpace> availableSpaces;
	
	public ViewController() {
		loggedInUser = null;
		availableSpaces = null;
		
		mainView = new ViewMain();
		heroView = new ViewHero(this);
		registrationView = new ViewRegistration(this);
		loginView = new ViewLogin(this);
		dashboardView = new ViewDashboard(this);
		
		mainView.addView("hero", heroView);
		mainView.addView("registration", registrationView);
		mainView.addView("login", loginView);
		mainView.addView("dashboard", dashboardView);
		
		registrationModel = new ModelRegistration();
		loginModel = new ModelLogin();
		bookingModel = new ModelBooking();
		
		// Start on Hero view
		showHeroView();
		mainView.setVisible(true);
	}
	
	/*
	 * Switch views
	 */
	public void showHeroView() { mainView.showView("hero"); }
	public void showRegistrationView() { mainView.showView("registration"); }
	public void showLoginView() { mainView.showView("login"); }
	public void showDashboardView() { mainView.showView("dashboard"); }
	
	public void showBookingView() {
	    if (loggedInUser != null) {
	    	// Get latest update on available spaces
	    	availableSpaces = loggedInUser.getAvailableSpaces();

	        // None space available
	    	if (availableSpaces == null || availableSpaces.isEmpty()) {
	            System.out.println("\n[-] No available parking spaces. Booking screen will not be shown.");
	            return;
	        }
	        
	        // Here we go
	    	System.out.println("\n[*] Start Booking Session. Available Spaces: " + availableSpaces.size());
	        
	        ViewBooking bookingView = new ViewBooking(this, availableSpaces);
	        mainView.addView("booking", bookingView);
	        mainView.showView("booking");
	    }
	}
	
	public void showBookingPlateView(String space, String date, String time, String duration) {
	    // Store selected info of booking phase 1
		this.selectedSpace = space;
	    this.selectedDate = date;
	    this.selectedTime = time;
	    this.selectedDuration = duration;
	    
	    ViewBookingPlate bookingPlateView = new ViewBookingPlate(this);
	    mainView.addView("bookingPlate", bookingPlateView);
	    mainView.showView("bookingPlate");
	}
	
	public void showBookingPayView(String plate) {
		// Store selected info of booking phase 2
	    this.selectedPlateNumber = plate;

	    ViewBookingPay bookingPayView = new ViewBookingPay(this);
	    mainView.addView("bookingPay", bookingPayView);
	    mainView.showView("bookingPay");
	}

	
	public void showBookingConfirmView(String paymentMethod) {
		// Store selected info of booking phase 3
	    this.selectedPaymentMethod = paymentMethod;
	    
	    ViewBookingConfirm bookingConfirmView = new ViewBookingConfirm(this);
	    mainView.addView("bookingConfirm", bookingConfirmView);
	    mainView.showView("bookingConfirm");
	}
	
	/*
	 * Start session for logged in user
	 */
	public void setLoggedInUser(LoggedInUser loggedInUser) {
		this.loggedInUser = loggedInUser;
		
		ManagerAccount.addSubscriber(loggedInUser);
		loggedInUser.update(ManagerAccount.getAvailableSpaces());
		
		System.out.println("\n[+] User Logged In. Available Spaces: " + this.loggedInUser.getAvailableSpaces().size());
	}
	
	public void updateAvailableSpaces() {
		availableSpaces = ManagerAccount.getAvailableSpaces();
	}
	
	/*
	 * Calling model classes
	 */
	public String registerUser(String type, String email, String pwd, String id) {
	    return registrationModel.registerUser(type, email, pwd, id);
	}

	public boolean validateUser(String email, String pwd) {
	    if (loginModel.validateUser(email, pwd, this)) {
	        System.out.println("\n[*] Logged In " + loggedInUser);
	        return true;
	    }
	    return false;
	}
	
	public boolean bookAParking() {
		return bookingModel.bookAParking(selectedSpace, selectedDate, selectedTime, selectedDuration, selectedPlateNumber, selectedPaymentMethod, loggedInUser.getRate());
	}
	
	/*
	 * Getters
	 */
	public String getSelectedSpace() { return selectedSpace; }
	public String getSelectedDate() { return selectedDate; }
	public String getSelectedTime() { return selectedTime; }
	public String getSelectedDuration() { return selectedDuration; }
	public String getSelectedPlateNumber() { return selectedPlateNumber; }
	public String getSelectedPaymentMethod() { return selectedPaymentMethod; }
	public LoggedInUser getLoggedInUser() { return loggedInUser; }
	
}