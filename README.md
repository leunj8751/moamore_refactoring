# moamore_refactoring
JPA로 리팩토링한 가계부 프로젝트


이전 프로젝트 : https://github.com/leunj8751/gith2_moaandmore

&nbsp;


* Front : Jsp  ->  Thymeleaf
* DB : Oracle -> Mysql
* Framework : Spring > SpringBoot
* Mybatis -> Jpa
* login : AOP -> SpringSecurity
* TDD 도입

&nbsp;
&nbsp;

<h4>ERD</h4>
&nbsp;

<img width="500" alt="image" src="https://user-images.githubusercontent.com/68139286/178101315-a0b28262-f772-4c92-bc68-f74df8ae21ae.png">

&nbsp;

<h4>핵심기능</h4>
&nbsp;

<img width="700" alt="image" src="https://user-images.githubusercontent.com/68139286/178101011-d6718445-36b7-48e2-8720-08f552ea355e.png">

* 7일, 14일, 30일 단위로 예산을 설정할 수 있다.
* 예산을 설정할때는 카테고리 별로 금액을 정해야 한다.
* 만약 아직 진행중인 예산이 있는데 새로운 예산을 등록하면 이전 예산은 종료된다.

&nbsp;

<img width="400" alt="image" src="https://user-images.githubusercontent.com/68139286/178101121-ab347417-b4dd-473e-bae7-32deb1240942.png">

* 예산 내 지출, 예산외 수입, 지출을 입력할 수 있다.
* 예산 내 지출을 등록하면 해당 날짜의 카테고리 예산에서 금액이 차감된다.
* 만약 설정한 카테고리 예산을 초과하면 자동으로 예산 외 지출로 입력된다.


&nbsp;

프로젝트 상세정보 : https://leun-j.tistory.com/11



