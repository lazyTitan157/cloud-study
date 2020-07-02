package com.example.pay;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostPersist;

import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class Pay {

    @Id
    @GeneratedValue
    Long payId;
	String payStatus;

	Long reservationId;

	Long flightId;
	int count;
	int price;
	String reserveStatus;

    @PostPersist
    public void onCreated(){
		// 0. parsing
		if(payStatus != null && payStatus.equals(PayCancelled.class.getSimpleName())) {
//			// 1. 예약취소됨 이벤트 발송
//			PayRepository payRepository = PayApplication.applicationContext.getBean(PayRepository.class);
//			
//	        PayCancelled payCancelled = new PayCancelled();
////	        reservationCancelled.setReservationId(this.getReservationId());
//	        payCancelled.setPayStatus(payCancelled.getPayStatus());
//	        
//	        Optional<Pay> payById = payRepository.findById(this.getPayId());
//	        Pay p = payById.get();
//			p.setCount(0);
//			p.setReserveStatus(payCancelled.getPayStatus());
////			r.setFlightId(this.getFlightId());
//			p.setPrice(0);
//			//			Product product = new Product();
////			product.setId(orderPlaced.getProductId());
//
//		    ObjectMapper objectMapper = new ObjectMapper();
//		    String json = null;
//
//		    try {
//		        json = objectMapper.writeValueAsString(p);
//		    } catch (JsonProcessingException e) {
//		        throw new RuntimeException("JSON format exception", e);
//		    }
//		    Processor processor = PayApplication.applicationContext.getBean(Processor.class);
//		    MessageChannel outputChannel = processor.output();
//
//		    outputChannel.send(MessageBuilder
//		            .withPayload(json)
//		            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
//		            .build());
//		    
//		    System.out.println("Cancelled");
		} else {
    	
        PayApproved payApproved = new PayApproved(this);
//        payApproved.setPayId(this.getPayId());
//        payApproved.setCount(this.getCount());
//        payApproved.setReservationId(this.getReservationId());
//        payApproved.setPrice(this.getPrice());
        
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(payApproved);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }

        Processor processor = PayApplication.applicationContext.getBean(Processor.class);
        MessageChannel outputChannel = processor.output();

        outputChannel.send(MessageBuilder
                .withPayload(json)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
		}
    }

    // Get set
	public Long getPayId() {
		return payId;
	}
	public void setPayId(Long payId) {
		this.payId = payId;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public Long getReservationId() {
		return reservationId;
	}
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
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
	public void setReserveStatus(String reserveStatus) {
		this.reserveStatus = reserveStatus;
	}
}
