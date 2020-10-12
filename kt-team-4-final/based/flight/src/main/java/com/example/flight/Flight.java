package com.example.flight;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Entity
public class Flight {

    @Id
    @GeneratedValue
    private Long flightId;
    //private Long reserveId;
    private String status;
    
    private String flightName;
    
    private int seat;  //항공사 직원이 ADD할때 max seat던짐.
//    private double seat2;
    

	private int price;
    private String destination;

    /*******************************/
    /*********** 항공권 등록 ***********/
    /*******************************/
    @PostPersist 
    public void onCreated(){
    	FlightAdded flightAdd = new FlightAdded();
    	flightAdd.setFlightId(this.getFlightId());
    	flightAdd.setFlightName(this.getFlightName());
    	flightAdd.setDestination(this.getDestination());
    	flightAdd.setPrice(this.getPrice());
    	flightAdd.setSeat(this.getSeat());
//    	flightAdd.setSeat2(this.getSeat2());
    	flightAdd.setEventType("FlightAdded");
    	
   
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(flightAdd);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }
        System.out.println(json);

        Processor processor = FlightApplication.applicationContext.getBean(Processor.class);
        MessageChannel outputChannel = processor.output();

        outputChannel.send(MessageBuilder
                .withPayload(json)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
        System.out.println("Flight Added");
    }
    
    @PostUpdate
    public void onModified() {
    	if(this.getStatus().equals(FlightSeatRequested.class.getSimpleName())) {
    		FlightSeatRequested flightSeatRequested = new FlightSeatRequested();
    		flightSeatRequested.setFlightId(this.getFlightId());
    		flightSeatRequested.setSeat(this.getSeat());
    		System.out.println(flightSeatRequested.getEventType() +" "+flightSeatRequested.getSeat());
    		
//    		repository.save(f);
    		ObjectMapper objectSendMapper = new ObjectMapper();
    		String json = null;

    		try {
    			json = objectSendMapper.writeValueAsString(flightSeatRequested);
    		} catch (JsonProcessingException e) {
    			throw new RuntimeException("JSON format exception", e);
    		}

    		Processor processor = FlightApplication.applicationContext.getBean(Processor.class);
    		MessageChannel outputChannel = processor.output();

    		outputChannel.send(MessageBuilder
    				.withPayload(json)
    				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
    				.build());	
    	} else {
			FlightSeatReturned flightSeatReturned = new FlightSeatReturned();
			flightSeatReturned.setFlightId(this.getFlightId());
			flightSeatReturned.setSeat(this.getSeat());
			System.out.println(flightSeatReturned.getEventType() +" "+flightSeatReturned.getSeat());
			
			ObjectMapper objectSendMapper = new ObjectMapper();
			String json = null;

			try {
				json = objectSendMapper.writeValueAsString(flightSeatReturned);
			} catch (JsonProcessingException e) {
				throw new RuntimeException("JSON format exception", e);
			}

			Processor processor = FlightApplication.applicationContext.getBean(Processor.class);
			MessageChannel outputChannel = processor.output();

			outputChannel.send(MessageBuilder
					.withPayload(json)
					.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
					.build());
			System.out.println("======================");
		}
    }

    
    
	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

//	public Long getReserveId() {
//		return reserveId;
//	}
//
//	public void setReserveId(Long reserveId) {
//		this.reserveId = reserveId;
//	}

	public String getFlightName() {
		return flightName;
	}

	public void setFlightName(String flightName) {
		this.flightName = flightName;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

//    public double getSeat2() {
//		return seat2;
//	}
//
//	public void setSeat2(double seat2) {
//		this.seat2 = seat2;
//	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
