package com.example.cqrs;

public class ReservationPlaced {

    Long reservationId;
   
	String phone;
    Long flightId;
    int count;
    int price;
    String reserveStatus;
    
    public ReservationPlaced(Reservation reservation) {
        this.reserveStatus = ReservationPlaced.class.getSimpleName();
        this.reservationId = reservation.getReservationId();
        this.phone = reservation.getPhone();
        this.setFlightId(reservation.getFlightId());
        this.setCount(reservation.getCount());
        this.setPrice(reservation.getPrice());
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
	public String getreserveStatus() {
		return reserveStatus;
	}
	public void setreserveStatus(String status) {
		this.reserveStatus = status;
	}   
    
}
