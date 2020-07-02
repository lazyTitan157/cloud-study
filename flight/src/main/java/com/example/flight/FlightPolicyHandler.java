package com.example.flight;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

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
        
        try {
        	payApproved = objectMapper.readValue(message, PayApproved.class);
        	System.out.println("type = payApproved");
        } catch (Exception e) {
			// TODO: handle exception
		}
        try {
        	payCancelled = objectMapper.readValue(message, PayCancelled.class);
        	System.out.println("type = payCancelled");
        } catch (Exception e) {
			// TODO: handle exception
		}
        
        try {
		if (payApproved != null && payApproved.getPayStatus().equals("PayApproved")
				&& payApproved.getFlightId() != null) {
			System.out.println("payApproved");
//			System.out.println(payApproved);

			Optional<Flight> flightById = repository.findById(payApproved.getFlightId());
			Flight f = flightById.get();
			System.out.println(f.getSeat());

			System.out.println("재고량 - 기존데이터가 없으니 현재는 그냥 저장로직만 수행");
			// Product product = new Product();
			// product.setId(orderPlaced.getOrderId());

			f.setSeat(f.getSeat() - payApproved.getCount());
			System.out.println(f.getSeat());
			repository.save(f);
			System.out.println("======================");
		} else if (payCancelled != null && payCancelled.getPayStatus().equals("PayCancelled")
				&& payCancelled.getFlightId() != null) {
			System.out.println("payCancelled");
			System.out.println(payCancelled);

			Optional<Flight> flightById = repository.findById(payCancelled.getFlightId());
			Flight f = flightById.get();
			System.out.println(f.getSeat());

			System.out.println("재고량 + 기존데이터가 없으니 현재는 그냥 저장로직만 수행");
			// Product product = new Product();
			// product.setId(orderPlaced.getOrderId());

			f.setSeat(f.getSeat() + payCancelled.getCount());
			System.out.println(f.getSeat());
			repository.save(f);
			System.out.println("======================");
			
		}
        } catch (Exception e) {
			// TODO: handle exception
		}
	}
}
