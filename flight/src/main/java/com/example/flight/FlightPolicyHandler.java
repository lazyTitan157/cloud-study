package com.example.flight;

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

	//PayApproved 되어 좌석수 차감.
	@StreamListener(Processor.INPUT)
	public void onEventListen(@Payload PayApproved payApproved) {

		if (payApproved != null && payApproved.getPayStatus().equals("PayApproved")
				&& payApproved.getFlightId() != null) {
			System.out.println("=========여기까지온거야~~~~~~~~~~~~~ =========");
			System.out.println(payApproved);

			Optional<Flight> flightById = repository.findById(payApproved.getFlightId());
			Flight f = flightById.get();
			System.out.println(f.getSeat());

			System.out.println("재고량 수정 - 기존데이터가 없으니 현재는 그냥 저장로직만 수행");
			// Product product = new Product();
			// product.setId(orderPlaced.getOrderId());

			f.setSeat(f.getSeat() - payApproved.getCount());
			System.out.println(f.getSeat());
			repository.save(f);
			System.out.println("======================");
		}
	}
	//PayApproved 되어 좌석수 증가
	@StreamListener(Processor.INPUT)
	public void onEventListen1(@Payload PayCancelled payCancelled) {

		if (payCancelled != null && payCancelled.getPayStatus().equals("PayCancelled")
				&& payCancelled.getFlightId() != null) {
			System.out.println("=========여기까지온거야~~~~~~~~~~~~~ =========");
			System.out.println(payCancelled);

			Optional<Flight> flightById = repository.findById(payCancelled.getFlightId());
			Flight f = flightById.get();
			System.out.println(f.getSeat());

			System.out.println("재고량 수정 - 기존데이터가 없으니 현재는 그냥 저장로직만 수행");
			// Product product = new Product();
			// product.setId(orderPlaced.getOrderId());

			f.setSeat(f.getSeat() + payCancelled.getCount());
			System.out.println(f.getSeat());
			repository.save(f);
			System.out.println("======================");
		}
	}
}
