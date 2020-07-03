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

# 사용 방법
```zookeeper-server-start.bat ../../config/zookeeper.properties
kafka-server-start.bat ../../config/server.properties
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic f7 --from-beginning'''

- 항공편 등록
(항공사CMD) http http://localhost:8082/flights flightName=CH777 destination=korea price=300000 seat=40
- 항공편 조회
(사용자CMD) http http://localhost:8081/flightStatuses
- 예약
(사용자CMD) http POST localhost:8081/reservations flightId=1 reserveStatus="place" count=1 price=300000 phone="01097770770"
(REQ/RES) reservationPlaced > payapproved
(REQ/RES) payApproved > flightSeatRequested
- 예약취소
(사용자CMD)  http POST localhost:8080/reservations reservationId=1 count=1 reserveStatus="cancel"
(REQ/RES) reservationCancelled > payCancelled
(REQ/RES) payCancelled > flighSeatReturned

##시나리오 테스트결과

| 기능 | 이벤트 Payload |
|---|:---:|
| 관리자가 항공을 등록한다. | ![image](https://user-images.githubusercontent.com/62231786/85086806-aa099200-b216-11ea-8ca4-50eb47c3b02b.JPG) |
| 사용자가 항공기를 조회한다. | ![image](https://user-images.githubusercontent.com/62231786/85086808-aa099200-b216-11ea-895e-3a7dcfeb4b71.JPG) |
| 사용자가 콘서트를 예약한다.</br>예약 시, 결제가 요청된다. | ![image](https://user-images.githubusercontent.com/62231786/85086809-aaa22880-b216-11ea-9d5c-fcf88fbd2a27.JPG) |
| 사용자가 예약한 콘서트를 결제한다.</br>결제가 완료되면 콘서트예약이 승인된다.</br>콘서트예약이 승인되면 티켓 수가 변경된다. (감소)| ![image](https://user-images.githubusercontent.com/62231786/85086811-aaa22880-b216-11ea-96aa-5ec29cd8a5d6.JPG) | 
| 사용자가 예약 취소를 하면 결제가 취소된다.</br>결제가 취소되면 티켓 수가 변경된다. (증가) | ![image](https://user-images.githubusercontent.com/62231786/85086805-a8d86500-b216-11ea-900a-be7c1555e61d.JPG) |
| 사용자가 콘서트 예약내역 상태를 조회한다. | [{"id":1,"bookingId":6659,"concertId":1,"userId":1,"status":"BookingRequested"},</br> {"id":2,"bookingId":6660,"concertId":3,"userId":1,"status":"PaymentCanceled"}] |
