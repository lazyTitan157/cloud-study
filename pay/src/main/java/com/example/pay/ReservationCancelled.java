package com.example.pay;

public class ReservationCancelled {


    Long reservationId;
   
	String phone;
    Long flightId;
    int count;
    int price;
    String reserveStatus;
    
    public ReservationCancelled() {
    	this.reserveStatus = ReservationCancelled.class.getSimpleName();
    }

    public Long getReservationId() {
		return reservationId;
	}
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getFlightId() {
		return flightId;
	}
	public void setFlightId(Long flightId) {
		this.flightId = flightId;
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
	public String getStatus() {
		return reserveStatus;
	}
	public void setStatus(String status) {
		this.reserveStatus = status;
	}   
}
