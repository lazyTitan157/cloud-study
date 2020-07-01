package com.example.pay;

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
public class PolicyHandler {

    @Autowired
    PayRepository Repository;

    @StreamListener(Processor.INPUT)
    public void onEventListen(@Payload String message){
//    	System.out.println(reservationCancelled.getStatus());
    	System.out.println("##### listener : " + message);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ReservationCancelled reservationCancelled = null;
        try {
        	
            reservationCancelled = objectMapper.readValue(message, ReservationCancelled.class);
            System.out.println(" #### type = " + reservationCancelled.getStatus());
        
            /**
             * 주문이 들어옴 -> 배송 시작 이벤트 발송
             */
//            if( orderPlaced.getEventType() != null && orderPlaced.getEventType().equals(OrderPlaced.class.getSimpleName())){
//
//                Delivery delivery = new Delivery();
//                delivery.setOrderId(orderPlaced.getOrderId());
//                delivery.setQuantity(orderPlaced.getQuantity());
//                delivery.setProductId(orderPlaced.getProductId());
//                delivery.setProductName(orderPlaced.getProductName());
//                delivery.setDeliveryAddress(orderPlaced.getCustomerAddr());
//                delivery.setCustomerId(orderPlaced.getCustomerId());
//                delivery.setCustomerName(orderPlaced.getCustomerName());
//                delivery.setDeliveryState(DeliveryStarted.class.getSimpleName());
//                deliveryRepository.save(delivery);

            /**
             * 주문이 취소됨 -> 배송 취소 이벤트 발송
             */
            if( reservationCancelled.getStatus().equals(ReservationCancelled.class.getSimpleName())){
            	System.out.println("pay cancelled");
                Repository = PayApplication.applicationContext.getBean(PayRepository.class);
                Optional<Pay> payByReserveId = Repository.findById(reservationCancelled.getReservationId());
    			Pay p = payByReserveId.get();
    			
    			p.setPayStatus(PayCancelled.class.getSimpleName());
    			
    			ObjectMapper objectSendMapper = new ObjectMapper();
    			String json = null;

    			try {
    				json = objectSendMapper.writeValueAsString(p);
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

        }catch (Exception e){

        }
//    	System.out.println(reservationCancelled.getStatus());
//        if( reservationCancelled != null && reservationCancelled.getStatus().equals("ReservationCancelled") ){
//        	System.out.println(reservationCancelled.getReservationId()+"is Cancelled");
//        	
//        }

//        if( PayApproved.class.getSimpleName().equals(payApproved.getEventType()) ){
//            System.out.println("=========요리시작 =========");
//            System.out.println(payApproved);
//            Store store = new Store();
//            store.setOrderId(payApproved.getOrderId());
//            storeRepository.save(store);
//            System.out.println("==================");
//        }
    }
}
