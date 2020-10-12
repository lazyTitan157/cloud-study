package com.example.pay;

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
    PayRepository payRepository;
	private Object payById;

    @StreamListener(Processor.INPUT)
    public void onEventListen(@Payload String message){
    	System.out.println("##### listener : " + message);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//        ReservationCancelled reservationCancelled = null;
        Pay reservationCancelled = null;
        
        try {
        	PayApproved p = objectMapper.readValue(message, PayApproved.class);
        	System.out.println("type = payApproved");
        } catch (Exception e) {
			// TODO: handle exception
		}
        try {
        	PayCancelled payCancelled = objectMapper.readValue(message, PayCancelled.class);
        	System.out.println("type = payCancelled");
        } catch (Exception e) {
			// TODO: handle exception
		}
        try {
        	
            reservationCancelled = objectMapper.readValue(message, Pay.class);
            System.out.println(" #### type = " + reservationCancelled.getReserveStatus());
        
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
            if( reservationCancelled.getReserveStatus().equals(ReservationCancelled.class.getSimpleName())){
            	System.out.println(reservationCancelled.getPayId());
            	
                payRepository = PayApplication.applicationContext.getBean(PayRepository.class);
                Iterable<Pay> pays = payRepository.findAll();
//                pays.iterator()
                Pay p = null;
                for (Pay pay : pays) {
					if(pay.getReservationId().equals(reservationCancelled.getReservationId())) {
						p = pay;
						break;
					}
				}
//                Optional<Pay> payById = payRepository.findById(reservationCancelled.getReservationId());
//    			Pay p = payById.get();
    			p.setPayStatus(PayCancelled.class.getSimpleName());
    			p.setCount(reservationCancelled.getCount());
    			System.out.println(p.getCount());
    			p.setPrice(0);
    			payRepository.save(p);
    			PayCancelled payCancelled = new PayCancelled(p);
    			
    			ObjectMapper objectSendMapper = new ObjectMapper();
    			String json = null;

    			try {
    				json = objectSendMapper.writeValueAsString(payCancelled);
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
        	System.out.println(reservationCancelled.getReserveStatus());
            if( reservationCancelled != null && reservationCancelled.getReserveStatus().equals("ReservationCancelled") ){
            	System.out.println(reservationCancelled.getReservationId()+"is Cancelled");	
            }

        }catch (Exception e){
        	System.out.println("no object mapping");
        }

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
