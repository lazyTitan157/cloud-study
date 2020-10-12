package com.example.cqrs;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;

import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name="reservation_table")
public class Reservation {
	@Id @GeneratedValue
	Long reservationId;

	Long flightId;
	String reserveStatus;
	int count;

	String phone;
	int price;

	@PostPersist
	public void onCheck(){

		if(reserveStatus != null && reserveStatus.equals("place")) {
			// 1. 예약됨 이벤트 발송
			
			ReservationPlaced reservationPlaced = new ReservationPlaced(this);
			//			reservationPlaced.setReservationId(this.getReservationId());;
			//			reservationPlaced.setCount(this.getCount());
			//			reservationPlaced.setreserveStatus(ReservationPlaced.class.getSimpleName()); //by Constructor
			//			reservationPlaced.setFlightId(this.getFlightId());
			//
			//			reservationPlaced.setPrice(this.getPrice());
			//			reservationPlaced.setPhone(this.getPhone());

			ObjectMapper objectMapper = new ObjectMapper();
			String json = null;

			try {
				json = objectMapper.writeValueAsString(reservationPlaced);
			} catch (JsonProcessingException e) {
				throw new RuntimeException("JSON format exception", e);
			}

			Processor processor = ReservationApplication.applicationContext.getBean(Processor.class);
			MessageChannel outputChannel = processor.output();

			outputChannel.send(MessageBuilder
					.withPayload(json)
					.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
					.build());

			// 2. 결재 정보 post
			RestTemplate restTemplate = ReservationApplication.applicationContext.getBean(RestTemplate.class);
			String payUrl = "http://localhost:8083/pays";
			Pay pay = new Pay();
			pay.setReservationId(reservationPlaced.getReservationId());
			pay.setCount(reservationPlaced.getCount());
			pay.setPrice(reservationPlaced.getPrice());
			pay.setFlightId(reservationPlaced.getFlightId());
			pay.setReserveStatus(reservationPlaced.getreserveStatus());

			restTemplate.postForEntity(payUrl, pay ,String.class);

			//        ResponseEntity<String> response = restTemplate.postForEntity(payUrl, pay ,String.class);
			//        if( response.getStatusCode() == HttpStatus.CREATED){
			//
			//        }
			//		}
		}
	}

	@PostUpdate //PostDelete는 아예삭제
	public void cancelOrder() {
		if(reserveStatus != null && reserveStatus.equals("cancel")) {
			// 1. 예약취소됨 이벤트 발송
			ReservationRepository reservationRepository = ReservationApplication.applicationContext.getBean(ReservationRepository.class);

			Optional<Reservation> reserveById = reservationRepository.findById(this.getReservationId());
			Reservation r = reserveById.get();
//			System.out.println(r.getCount());
			ReservationCancelled reservationCancelled = new ReservationCancelled(r);
			//			reservationCancelled.setReservationId(this.getReservationId());
			//			reservationCancelled.setReserveStatus(reservationCancelled.getReserveStatus());
			reservationCancelled.setFlightId(r.getFlightId());
			reservationCancelled.setPhone(r.getPhone());
			//			r.setCount(0);
			reservationCancelled.setCount(this.getCount());
			//						reservationCancelled.setPrice(r.getPrice());
			//			reservationCancelled.setReserveStatus(ReservationCancelled.class.getSimpleName());

			//			Product product = new Product();
			//			product.setId(orderPlaced.getProductId());

			ObjectMapper objectMapper = new ObjectMapper();
			String json = null;

			try {
				json = objectMapper.writeValueAsString(reservationCancelled);
			} catch (JsonProcessingException e) {
				throw new RuntimeException("JSON format exception", e);
			}

			Processor processor = ReservationApplication.applicationContext.getBean(Processor.class);
			MessageChannel outputChannel = processor.output();

			outputChannel.send(MessageBuilder
					.withPayload(json)
					.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
					.build());
		}
	}


	// Getters and Setters
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

	public String getReserveStatus() {
		return reserveStatus;
	}

	public void setReserveStatus(String status) {
		this.reserveStatus = status;
	}



}
