package com.example.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class FlightPolicyHandler {

	@Autowired
	FlightRepository repository;

	@StreamListener(Processor.INPUT)
	public void onEventListen(@Payload PayApproved payApproved) {

//        if( payApproved != null && payApproved.equals(PayApproved.class.getSimpleName()) ){
//
//        }
		//if (PayApproved.class.getSimpleName().equals(payApproved.getPayStatus())) {
		if (payApproved.getPayStatus().equals("PayApproved")) {
			System.out.println("=========여기까지온거야~~~~~~~~~~~~~ =========");
			System.out.println(payApproved);

			Optional<Flight> flightById = repository.findById(payApproved.getFlightId());
			Flight f = flightById.get();
			System.out.println(f.getSeat());

			System.out.println("재고량 수정 - 기존데이터가 없으니 현재는 그냥 저장로직만 수행");
			// Product product = new Product();
			// product.setId(orderPlaced.getOrderId());

			f.setSeat(f.getSeat() - 1);
			System.out.println(f.getSeat());
			repository.save(f);
			System.out.println("======================");
		}

//            Store store = new Store();
//            store.setOrderId(payApproved.getOrderId());
//            storeRepository.save(store);
	}
}
