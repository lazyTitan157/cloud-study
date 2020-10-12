package com.example.flight;

public class PayCancelled {

    Long payId;
	String payStatus;

	Long reservationId;
	Long flightId;
	int count;
	
	int price;
	
//	String reserveStatus;
	
//    public PayCancelled(){
//        this.payStatus = PayCancelled.class.getSimpleName();
//    }
    public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
//
//	public String getReserveStatus() {
//		return reserveStatus;
//	}
//
//	public void setReserveStatus(String reserveStatus) {
//		this.reserveStatus = reserveStatus;
//	}

    
}
