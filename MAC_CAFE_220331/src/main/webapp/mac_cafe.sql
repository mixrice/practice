/* '정보' 저장 - 사용자, 관리자 */
CREATE TABLE user_table(
/* 아래 6개는 회원가입 폼에 있음 */
u_id varchar(45) PRIMARY KEY,
u_grade Nvarchar(10) NOT NULL, /*회원가입 폼에서 숨김 - 첫 회원가입때 hidden으로 Normal값 전달 */
u_password varchar(256) NOT NULL,
u_name Nvarchar(20) NOT NULL,
u_email varchar(45) NOT NULL,
u_call varchar(40) NOT NULL,

/* 아래 3개는 회원가입 폼에 없음 */
u_joindate DATE NOT NULL,
order_quantity int NOT NULL default 0, /* 향후 삭제 예정*/
order_money int default 0 /* 향후 삭제 예정*/
);

select * from user_table;
select * from address_table;

/* '주소' 저장 - 사용자, 관리자 */
CREATE TABLE address_table(
addr_index int auto_increment PRIMARY KEY,
u_id varchar(45) NOT NULL,

postcode int NOT NULL, /* 우편번호 */
address1 Nvarchar(60) NOT NULL,
address2 Nvarchar(60) NOT NULL
);

/* 등급별 세일율 -> 등급별 포인트율로 수정해서 사용해도 됨*/
CREATE TABLE grade_table(
grade varchar(15) PRIMARY KEY,
salerate int NOT NULL
);

INSERT INTO grade_table VALUES('normal', 5); /* 5% */
INSERT INTO grade_table VALUES('vip', 10); /* 10% */
INSERT INTO grade_table VALUES('admin', 0); /* 0% */


/* 메뉴 정보 */
CREATE TABLE menu_table(
m_id Nvarchar(30) PRIMARY KEY, /* 메뉴id */
category Nvarchar(50) NOT NULL, /* 종류 = 구분 */
m_name Nvarchar(45) NOT NULL, /* 메뉴명 */
m_price int NOT NULL, /* 메뉴가격 */
m_detail Nvarchar(200), /* 메뉴 설명*/
m_status varchar(1) NOT NULL, /* y : 판매할 수 있는 상태, n : 판매불가 상태 */
m_date date,  /* 관리자가 메뉴 올린 날짜 */
image Nvarchar(100) NOT NULL
);


insert into menu_table values('2','burger','버거','1','1','y',now(),'img');
select * from menu_table;

/*  [ 주문 정보 ]
 *   - 사용자모드 : 지난달 매출정보 조회-> 등급조정, 구매하기 
 *   - 관리자모드 : 실시간 주문현황 - 주문정보
 * 
 *   ※ 주의할 사항
 *      홍길동 id가 aaa111로 가입 후
 *      여러 상품 주문 후 회원탈퇴하면
 *      관리자모드에는 주문한 내역들이있으나,
 *      이후에 이순신이 같은 id인 aaa111로 가입 후
 *      [주문내역보기]를 해도 이순신이 주문한 것만 보이도록 하기위해
 *      (즉, 홍길동의 주문내역이 보이지 않도록 하기위해) 'u_email'을 추가함
 *       => u_id, u_email이 같아야 이전 주문한 내역들 볼수 있음
 */

CREATE TABLE order_table(
order_num int auto_increment PRIMARY KEY, /* 주문번호 */
u_id varchar(45) NOT NULL, /* 주문한 사용자의 id */

u_email varchar(45) NOT NULL, /* 주문한 사용자의 u_email */

order_date timestamp NOT NULL, /* 주문한 날짜(하루매출조회) */
order_status varchar(25) NOT NULL, /* 주문상태 -> 사용자모드 - 상태 : order(구매하기- 주문한 상태) / 관리자모드 - 상태 : get(주문승인), cancel(주문취소) */
totalmoney int NOT NULL /* (한번 주문할 때마다의)주문한 총금액 */
);

/* 주문번호 클릭하면 '주문상세보기' 정보 */
CREATE TABLE orderDetail_table(
detail_index int auto_increment PRIMARY KEY,
m_id Nvarchar(20), /* 메뉴 id */
order_num int NOT NULL, /* 주문번호 */
quantity int NOT NULL, /* 주문 수량 */
m_name Nvarchar(45) NOT NULL, /* 메뉴명 */
m_price int NOT NULL /* 메뉴가격 */
);

/* 리뷰정보 */
CREATE TABLE review_table(
review_num int auto_increment PRIMARY KEY,
u_id varchar(45), /* 리뷰를 단 사용자 id */
m_id Nvarchar(20) NOT NULL, /* 메뉴 id*/
rating int NOT NULL, /* 평점 */
text Nvarchar(200) /* '한줄평'적기는 NULL 허용 */
);

select * from review_table;

/* -------------------------------------------------------------------- */
/* MySQL 에서 datetime과 timestamp 차이에 대해
 *   1. datetime과 timestamp 두가지 타입을  가진 테이블 생성
 */
CREATE TABLE datedemo(
mydatetime datetime,
mytimestamp timestamp
);

/* 2. 현재 시간을 넣어보면 ? */
INSERT INTO datedemo VALUES(now(), now()); /* 오라클의 sysdate = now() */
SELECT * FROM datedemo; /* 같은 결과 */

/* 3. 그런데 시스템의 타입존을 변경하면 ? */
set TIME_ZONE = "america/new_york";

/* 4. 다시 조회하면 datetime는 우리나라 날짜와 시간, timestamp는 미국 날짜와 시간*/
SELECT * FROM datedemo;


/* -------------------------------------------------------------------- */

/*
 * -- LIMIT : '정렬 후' 결과에서 원하는 행수를 가져옴(※ Mysql문에서만 사용가능)
 * 
 * 오라클에서는 ROWNUM이 있지만
 * 쿼리가 완전히 수행되지 않는 원 데이터의 정렬 순서대로(정렬전) 번호를 매기기 때문에
 * 전혀 다른 결과가 나옴
 * 
 * ※ ROWNUM은 <, <= 두가지 연산자만 사용가능하다. (단, 1행은 예외, =1, >=1, <=1) 
 * 
 * 오라클에서는 LIMIT와 동일한 결과를 얻기 위해서는
 * SELECT절로 한번 감싼 후에 ROWNUM으로 조건을 주면된다.
 */

/* 테이블에서 5개 가져오기*/
SELECT * FROM 테이블명 LIMIT 5;

/* 테이블에서 5부터 시작해서 10개 가져오기*/
SELECT * FROM 테이블명 LIMIT 4,10; /* LIMIT 시작, 가져올 갯수 (단, 0부터 시작) */

/* Mysql */
SELECT * FROM employee
where 


