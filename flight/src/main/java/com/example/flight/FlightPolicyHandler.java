package com.example.flight;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FlightPolicyHandler {

	@Autowired
	FlightRepository repository;

	//PayApproved 되어 좌석수 차감.
	@StreamListener(Processor.INPUT)
	public void onEventListen(@Payload String message){
    	System.out.println("##### listener : " + message);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//        ReservationCancelled reservationCancelled = null;
        PayApproved payApproved = null;
        PayCancelled payCancelled = null;
//        FlightAdded flightAdd = null;
        
        try {
        	payApproved = objectMapper.readValue(message, PayApproved.class);
        	System.out.println("type = payApproved");
        	
        	payCancelled = objectMapper.readValue(message, PayCancelled.class);
        	System.out.println("type = payCancelled");
        	
        } catch (Exception e) {
			// TODO: handle exception
		}
        try {
		if (payApproved != null && payApproved.getPayStatus().equals("PayApproved")) {
			System.out.println("payApproved");

			Optional<Flight> flightById = repository.findById(payApproved.getFlightId());
			Flight f = flightById.get();

			f.setSeat(f.getSeat() - payApproved.getCount());
//			System.out.println(f.getSeat());
			f.setStatus(FlightSeatRequested.class.getSimpleName());
			repository.save(f);
			System.out.println("======================");
			
			System.out.println("======================");
		} else if (payCancelled != null && payCancelled.getPayStatus().equals("PayCancelled")) {
			System.out.println("payCancelled");

			Optional<Flight> flightById = repository.findById(payCancelled.getFlightId());
			Flight f = flightById.get();

//			FlightSeatReturned flightSeatReturned = new FlightSeatReturned();
//			flightSeatReturned.setEventType(FlightSeatReturned.class.getSimpleName());
			System.out.println("재고량:"+f.getSeat()+" + "+ payCancelled.getCount()+ " " );

			f.setSeat(f.getSeat() + payCancelled.getCount());
//			System.out.println(f.getSeat());

			f.setStatus(FlightSeatReturned.class.getSimpleName());
			repository.save(f);
			
			
		}
        } catch (Exception e) {
			// TODO: handle exception
        	
		}
	}
}
