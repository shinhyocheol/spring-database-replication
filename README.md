spring-database-replication
===========================

### Tech Stack 
> Java8, SpringBoot2.5.2, Mysql5.7 

### 구조도
<img width="799" alt="database-replication-구조도" src="https://user-images.githubusercontent.com/51734699/126442867-09b543e5-88a2-4d05-bb66-c1a49be99dba.png">

### description
1. database replication 설정을 통해 조회와 쓰기,수정의 분기를 나누어 각각 설정된 정보의 데이터베이스로 접속하는 방식입니다.
2. 각 서비스에 @Transactional 어노테이션을 달아주었고, readOnly가 true인경우 slave로 그렇지 않은경우(readOnly 기본값은 false) master로 접속합니다.
