# 카카오페이 공채 개발 과제
[카카오페이 공채 개발 과제 문서](./reference.md)

## API
[Swagger-UI](http://localhost:8080/swagger-ui.html)

### distribution
- 뿌리기 API
  - [POST] /distributions
- 핵심 문제해결 전략
   - 뿌리기 시 잔액 감소 및 대화방에 메세지를 발송하는 부분.
     - 본 프로그램에는 없지만, 잔액을 관리 하는 시스템과 대화방에 메세지를 보내는 시스템에 있을 것 이라는 가정하에 Kafka를 통한 Message 전송 처리하였음. (application.yml의 'spring.kafka.use-flag' 를 true로 설정 할 시 pub/sub 동작함)

### lookup
- 조회 API
  - [GET] /distributions/{token}
- 핵심 문제해결 전략
  - 받기 완료된 금액, 받기 완료된 정보 ([받은 금액, 받은 사용자 아이디] 리스트) 데이터를 조회하는 부분.
    - 데이터 조회 시 filter를 통해 '받음' 상태인 데이터 리스트 만 반환함.
    - 위 처리 중 받기 완료된 금액도 계산함.
- Error Response
  - 400 (Bad Request)
  - message
    - 타인의 뿌리기건 이나 유효하지 않은 token 조회 시 
      - "Invalid userKey or roomKey. lookup is only allowed to owner"
    - 뿌린 후 7일 초과 건 조회 시
      - "Invalid lookup Time. lookup is only 7 days available"
  
### receive
- 받기 API
  - [PUT] /distributions/{token}/receive
- 핵심 문제해결 전략
  - 아직 할당되지 않은 분배건 중 하나를 할당하는 부분
    - 데이터 조회 시 filter를 통해 '받지않음' 상태인 데이터 리스트 만 반환함. 
    - 위에서 반환한 리스트의 개수 범위 내에서 Random 함수를 이용해 분배건을 할당함.
- Error Response
  - 400 (Bad Request)
  - message
    - 이미 한 번 받은 사용자에 의한 받기 요청 시
      - "Invalid userKey. receive is allowed only one time"
    - 본인의 뿌리기 건에 대한 받기 요청 시
      - "Invalid userKey. receive is not allowed to owner"
    - 다른 방의 뿌리기 건에 대한 받기 요청 시
      - "Invalid roomKey. receive is only allowed to same room"
    - 10분 초과 뿌리기 건에 대한 받기 요청 시
      - "Invalid receive Time. receive is only 10 minutes available"
    - 받기가 모두 완료된 뿌리기 건에 대한 받기 요청 시 
      - "Distribution's Receive is end."

