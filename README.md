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
1. 전체 좌석의 10%를 제외한 나머지 좌석수를 고객은 확인할수 있다.(VIP 및 긴급용)
1. 고객이 항공권을 예약하고 결재한다.
1. 항공사로 구매내역이 전달된다.
1. 항공사는 구매내역을 확인하여 해당 항공편의 좌석을 감소한다.
1. 고객이 항공권을 취소할 수 있다.
1. 고객이 항공권 취소를 요청하면, 자동으로 결재가 취소되며 항공사에 관련 내용을 전달한다.
1. 고객이 항공권 구매내역을 조회할 수 있다.

비기능적 요구사항
1. 트랜잭션
    1. 결제가 되지 않은 주문건은 아예 거래가 성립되지 않아야 한다  Sync 호출
1. 장애격리
    1. 항공사 관리 기능이 수행되지 않더라도 주문은 365일 24시간 받을 수 있어야 한다  Async (event-driven), Eventual Consistency
    1. 결제시스템이 과중되면 사용자를 잠시동안 받지 않고 결제를 잠시후에 하도록 유도한다  Circuit breaker, fallback
1. 성능
    1. 사용자는 항공권 예약내역을 확인할 수 있다. > CQRS
    
    
# 분석/설계

## Event Storming 결과

![flightsystem](https://user-images.githubusercontent.com/63759253/86202972-af63c680-bb9e-11ea-9d1f-0d3e79b46113.jpg)

```
# 도메인 서열
- Core : Reservation
- Supporting : flight
- General : Payment
```
