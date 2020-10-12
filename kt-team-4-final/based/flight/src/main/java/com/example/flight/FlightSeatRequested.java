package com.example.flight;

public class FlightSeatRequested {
	
	String eventType;
	Long flightId;
    String flightName;
    int price;
    int seat;
//    double seat2;
    
    
//    public double getSeat2() {
//    	//seat2 = getSeat()*2;
//		return seat2;
//	}
//
//	public void setSeat2(double seat2) {
//		this.seat2 = seat2;
//	}

	String destination;
    public String getDestination() {
    	
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	String status;
    
    public FlightSeatRequested() {
    
    	eventType = FlightSeatRequested.class.getSimpleName();
    }

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    

}
