# based
![images](https://user-images.githubusercontent.com/63759273/86201241-fbf8d300-bb99-11ea-97e1-4bd24ba1e095.jpg)

# 예제 - 항공권 예약

본 예제는 MSA/DDD/Event Storming/EDA 를 포괄하는 분석/설계/구현/운영 전단계를 커버하도록 구성한 예제입니다.
이는 클라우드 네이티브 애플리케이션의 개발에 요구되는 체크포인트들을 통과하기 위한 예시 답안을 포함합니다.
- 체크포인트 : https://workflowy.com/s/assessment-check-po/T5YrzcMewfo4J6LW


# Table of contents

- [예제 - 항공권 예약](#---)


# 서비스 시나리오

항공권예약 커버하기 - <항목 삭제 예정

1. 항공사 관리자가 여행 가능 항공편 및 좌석을 등록한다.
1. 고객이 여행가능한 항공편 리스트를 확인하고 선택한다.
1. 고객이 항공권을 예약하고 결재한다.
1. 항공사로 구매내역이 전달된다.
1. 항공사는 구매내역을 확인하여 해당 항공편의 좌석을 감소한다.
1. 좌석 감소는 항공편 리스트에 반영되어 해당 항공의 남은 좌석수를 보여준다.
1. 고객이 항공권을 취소할 수 있다.
1. 고객이 항공권 취소를 요청하면, 자동으로 결재가 취소되며 항공사에 관련 내용을 전달한다.
1. 항공권 취소로 인한 좌석증가는 항공편 리스트에 반영되어 해당 항공의 남은 좌석수를 보여준다.
1. 고객이 항공권 구매내역을 조회할 수 있다.

비기능적 요구사항
1. 트랜잭션
    1. 결제가 되지 않은 주문건은 아예 거래가 성립되지 않아야 한다  Sync 호출
1. 장애격리
    1. 항공사 관리 기능이 수행되지 않더라도 주문은 365일 24시간 받을 수 있어야 한다  Async (event-driven), Eventual Consistency
1. 성능
    1. 사용자는 항공권 리스트을 확인할 수 있다. > CQRS


# 분석/설계

## Event Storming 결과

![flightsystem](https://user-images.githubusercontent.com/63759253/86202972-af63c680-bb9e-11ea-9d1f-0d3e79b46113.jpg)

```
# 도메인 서열
- Core : Reservation
- Supporting : flight
- General : Payment
```

## 헥사고날 아키텍처 다이어그램 도출
    
![team4_kafka](https://user-images.githubusercontent.com/63759253/86447123-d57c9880-bd4f-11ea-89e9-56807b34f8a9.jpg)



## 완성본에 대한 기능적/비기능적 요구사항을 커버하는지 검증

![flightsystem3](https://user-images.githubusercontent.com/63759253/86463639-de2d9880-bd68-11ea-952d-4161dbbf8017.jpg)

- 모델은 모든 요구사항을 커버함


# 사용 방법
```zookeeper-server-start.bat ../../config/zookeeper.properties
kafka-server-start.bat ../../config/server.properties
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic f7 --from-beginning'''
```


- 항공편 등록
(항공사CMD) http http://localhost:8082/flights flightName=CH777 destination=korea price=300000 seat=100
- 예약
(사용자CMD) http POST localhost:8081/reservations flightId=1 reserveStatus="place" count=1 price=300000 phone="01097770779"
(REQ/RES) reservationPlaced > payapproved
(REQ/RES) payApproved > flightSeatRequested
- 항공편 조회
(사용자CMD) http http://localhost:8081/flightStatuses
- 예약취소
(사용자CMD) http POST localhost:8081/reservations reservationId=2 count=1 reserveStatus="cancel"
(REQ/RES) reservationCancelled > payCancelled
(REQ/RES) payCancelled > flighSeatReturned
- 항공편 조회
(사용자CMD) http http://localhost:8081/flightStatuses

## 시나리오 테스트결과

| 기능 | 이벤트 Payload |
|---|:---:|
| 관리자가 항공을 등록한다. | ![항공기 등록](https://user-images.githubusercontent.com/63759253/86438699-0f46a280-bd42-11ea-8404-da13ccbd3467.jpg) (key) eventType: FlightAdded Request to Pay |
| 사용자가 항공기를 예약한다.</br>예약 시, 결제가 요청되며 좌석이 줄어든다. | ![예약](https://user-images.githubusercontent.com/63759253/86438860-4d43c680-bd42-11ea-884b-e7d2e91cc684.jpg) (key) [예약서비스]reserveStatus: ReservationPlaced > [결제서비스]payStatus: PayApproved > [서비스]eventType: FlightRequested |
| 사용자가 항공기를 조회한다. | ![항공편 현황판1](https://user-images.githubusercontent.com/63759253/86438755-27b6bd00-bd42-11ea-9052-e6c4e1b57aaa.jpg) |
| 사용자가 항공기를 예약을 취소한다.</br>취소 시, 결제가 취소되며 좌석이 증가한다. | ![예약 cancel](https://user-images.githubusercontent.com/63759253/86439023-af043080-bd42-11ea-9660-ab3bf9d4cbd5.jpg) (key) reserveStatus: ReservationCancelled, payStatus: PayCancelled, eventType:FlightSeatReturned |
| 사용자가 항공기를 조회한다. | ![항공편 현황판2](https://user-images.githubusercontent.com/63759253/86439067-c3e0c400-bd42-11ea-8eef-30ed2b3b2443.jpg) |

## Gateway 적용
```spring:
  profiles: docker
  cloud:
    gateway:
      routes:
        - id: reservation
          uri: http://reservation:8080
          predicates:
            - Path=/reservations/**
        - id: flight
          uri: http://flight:8080
          predicates:
            - Path=/flights/**
        - id: pay
          uri: http://pay:8080
          predicates:
            - Path=/pays/**
        - id: cqrs
          uri: http://cqrs:8080
          predicates:
            - Path=/cqrs/**
```

## 폴리글랏 퍼시스턴스
Flight 서비스만 DB를 MySQL로 구분하여 적용함. 나머지 서비스는 인메모리 DB인 hsqldb 사용.
```
<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>8.0.19</version>
		</dependency>
		<!-- <dependency> <groupId>org.springframework.boot</groupId> <artifactId>spring-boot-starter-web</artifactId>
			</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>-->
```
application.yml
```
 ##mysql connection test
  datasource: 
    driverClassName: com.mysql.cj.jdbc.Driver
    password: "${_DATASOURCE_PASSWORD:dddddddd}"
    url: "jdbc:mysql://${_DATASOURCE_ADDRESS:team4-mysql.chakwspaezvo.ap-northeast-1.rds.amazonaws.com:3306}/${_DATASOURCE_TABLESPACE:team4?characterEncoding=UTF-8&serverTimezone=UTC}"
    username: "${_DATASOURCE_USERNAME:admin}"
  jpa: 
    hibernate: 
      ddl-auto: update
      naming: 
        physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
    properties: 
      hibernate: 
        dialect: org.hibernate.dialect.MySQL8Dialect
        format_sql: true
        show_sql: true 
```


## 동기식 호출 과 Fallback 처리
예약 > 결제 간의 호출은 동기식 일관성을 유지하는 트랜잭션으로 처리
- 동기식 호출 (Reservation.java)
```
    @PostPersist
	public void onCheck(){

		if(reserveStatus != null && reserveStatus.equals("place")) {
			// 1. 예약됨 이벤트 발송
            ...
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
```

## 비동기식 호출 과 Fallback 처리
- 비동기식 발신 구현 (Pay.java)
```
    @PostPersist
    public void onCreated(){
        PayApproved payApproved = new PayApproved(this);
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
```
- 비동기식 수신 구현 (pay > PolicyHandler.java)
```
    @StreamListener(Processor.INPUT)
    public void onEventListen(@Payload String message){
    	System.out.println("##### listener : " + message);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ...
        if( reservationCancelled.getReserveStatus().equals(ReservationCancelled.class.getSimpleName())){
            	System.out.println(reservationCancelled.getPayId());

                payRepository = PayApplication.applicationContext.getBean(PayRepository.class);
                Iterable<Pay> pays = payRepository.findAll();
                Pay p = null;
                for (Pay pay : pays) {
					if(pay.getReservationId().equals(reservationCancelled.getReservationId())) {
						p = pay;
						break;
					}
				}
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
            ...

```

## CQRS 구현
Flight 서비스와 Pay 서비스 정보를 한번에 가져오는 CQRS서비스 작성 (FlightStatusViewHandler.java)
- Flight 서비스의 FlightSeatReturned 이벤트에 대한 리스너
```
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
```
- Pay 서비스의 PayApproved 이벤트에 대한 리스너
```
    @StreamListener(Processor.INPUT)
    public void whenseatPayed_then_UPDATE_1 (@Payload PayApproved payApproved) {
        try {
            if ( payApproved.getPayStatus() != null && payApproved.getPayStatus().equals(PayApproved.class.getSimpleName())) {
               Optional<FlightStatus> flightStatusOptional  = flightStatusRepository.findById(payApproved.getFlightId());
               if (flightStatusOptional.isPresent()) {
                  FlightStatus flightStatus = flightStatusOptional.get();
                   flightStatus.setStatus("Recently Booked!");
                   flightStatusRepository.save(flightStatus);
               }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
```

# 운영
## CI/CD 설정
- CodeBuild 기반으로 파이프라인 구성
![pipeline](https://user-images.githubusercontent.com/63759273/86444155-4e2d2600-bd4b-11ea-97d6-95b1a977f7e0.PNG)

- Git Hook 연결
![git hook](https://user-images.githubusercontent.com/63759273/86444561-de6b6b00-bd4b-11ea-8c4e-8e700b7201dc.PNG)

## 서킷 브레이 / 장애격리

## 오토스케일 아웃
- 현재 상태 확인
- 오토스케일 설정
- 부하 수행
- 모니터링
- 스케일 아웃 확인


## 무정지 재배포
![무정지재배포](https://user-images.githubusercontent.com/63759273/86442676-20df7880-bd49-11ea-9fe3-c84393b14390.PNG)

## S3 & CloudFront 적용
| 적용과정 | 캡쳐화면 |
|---|:---:|
| Terraform으로 S3생성 | ![image](https://user-images.githubusercontent.com/8167433/86443475-48831080-bd4a-11ea-9ad6-ab932e443b6a.png)|
| S3와 CloudFront 연결 | ![image](https://user-images.githubusercontent.com/8167433/86443777-b596a600-bd4a-11ea-9322-5a878df4cf0a.png)|
| 서비스 UI에서 CloudFront이미지 사용 | ![image](https://user-images.githubusercontent.com/8167433/86443882-db23af80-bd4a-11ea-83c4-e73f5c0a671a.png) ![image](https://user-images.githubusercontent.com/8167433/86444002-0d351180-bd4b-11ea-8eed-61a067c330a5.png) |
