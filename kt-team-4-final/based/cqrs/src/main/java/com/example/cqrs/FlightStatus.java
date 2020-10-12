package com.example.cqrs;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="FlightStatus_table")
public class FlightStatus {

    @Id @GeneratedValue
    private Long flightId;
    private String flightName;
    private int seat;
    private Integer price;
    private String status;
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
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
		this.seat = seat;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    

	
}
