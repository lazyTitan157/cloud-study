package com.example.cqrs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlightStatusViewHandler {

    @Autowired
    private FlightStatusRepository flightStatusRepository;

    @StreamListener(Processor.INPUT)
    public void whenflightadded_then_CREATE_1 (@Payload FlightAdded flightAdded) {
        try {
        	if ( flightAdded.getEventType().equals(FlightAdded.class.getSimpleName())) {
        		FlightStatus flightStatus = new FlightStatus();
            	flightStatus.setSeat(flightAdded.getSeat());
            	if (flightStatus.getSeat() == 0) {
            		System.out.println("123");
            	}
            	else {
            	flightStatus.setFlightName(flightAdded.getFlightName());
            	flightStatus.setStatus("new flight");
                flightStatusRepository.save(flightStatus);
            	}
            	}
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @StreamListener(Processor.INPUT)
    public void whenseatRequested_then_UPDATE_1 (@Payload FlightSeatRequested flightSeatRequested) {
        try {
            if ( flightSeatRequested.getEventType().equals(FlightSeatRequested.class.getSimpleName())) {
            	Optional<FlightStatus> flightStatusOptional  = flightStatusRepository.findById(flightSeatRequested.getFlightId());
            	if (flightStatusOptional.isPresent()) {
            		FlightStatus flightStatus = flightStatusOptional.get();
            		flightStatus.setSeat(flightSeatRequested.getSeat());
                	flightStatus.setFlightName(flightSeatRequested.getFlightName());
                	flightStatus.setStatus("seat requested");
                	flightStatusRepository.save(flightStatus);
            	}        	
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @StreamListener(Processor.INPUT)
    public void whenseatReturned_then_UPDATE_2 (@Payload FlightSeatReturned flightSeatReturned) {
        try {
            if ( flightSeatReturned.getEventType().equals(FlightSeatReturned.class.getSimpleName())) {
            	Optional<FlightStatus> flightStatusOptional  = flightStatusRepository.findById(flightSeatReturned.getFlightId());
            	if (flightStatusOptional.isPresent()) {
            		FlightStatus flightStatus = flightStatusOptional.get();
            		flightStatus.setSeat(flightSeatReturned.getSeat());
                	flightStatus.setFlightName(flightSeatReturned.getFlightName());
                	flightStatus.setStatus("seat returned");
                	flightStatusRepository.save(flightStatus);
            	}        	
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
}