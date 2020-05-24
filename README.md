# CouponRest
쿠폰 시스템 구축 

1. 개발환경 
 - 이클립스 버전: Spring Tool Suite 4 Version: 4.5.1.RELEASE
 - JAVA : 1.8.0_241
 - spring-boot 2.3.0
 - DB : H2
 - lombok
 - jackson
 - opencsv
 - jjwt 0.9.1
 - SpringSecurity 
 - postman

2. 개발 완료 
 - 선택 문제 완료 
 - 제약사항(선택)
    =JWT 사용완료
    =100억개 이상 쿠폰 완성 가능 조합 구성
    =10만개 이상 csv 완성 (파일명 : uploadTest.csv)
     10만개 업로드 30초
    =성능 테스트 
     100만개 생성 ( 1시간)
     

3. 사용방법 정리

 - 가입
POST
http://localhost:8080/login/signup

{
	"id" : "ccc@naver.com",
	"password" : "test123",
	"name" : "이름"
}

 - 로그인 
POST
http://localhost:8080/login/signin

{
	"id" : "ccc@naver.com",
	"password" : "test123"

}

 - 쿠폰만들기 
POST 
http://localhost:8080/coupon/makeCoupon

header 추가
X-AUTH-TOKEN 
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMDY1Iiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTU5MDIyMjA3OSwiZXhwIjoxNTkwMzA4NDc5fQ.hakqvn-vPQNqRR09kQ9aXGr1oMVd5Y0iNti7iQg-1xY
{
	"input":"10"
}

 - 할당 
PATCH
http://localhost:8080/coupon/allocate

header 추가
X-AUTH-TOKEN 
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMDY1Iiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTU5MDIyMjA3OSwiZXhwIjoxNTkwMzA4NDc5fQ.hakqvn-vPQNqRR09kQ9aXGr1oMVd5Y0iNti7iQg-1xY

 - 할당 목록 조회
GET
http://localhost:8080/coupon/list

header 추가
X-AUTH-TOKEN 
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMDY1Iiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTU5MDIyMjA3OSwiZXhwIjoxNTkwMzA4NDc5fQ.hakqvn-vPQNqRR09kQ9aXGr1oMVd5Y0iNti7iQg-1xY

 - 할당 사용
PATCH
http://localhost:8080/coupon/using

header 추가
X-AUTH-TOKEN 
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMDY1Iiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTU5MDIyMjA3OSwiZXhwIjoxNTkwMzA4NDc5fQ.hakqvn-vPQNqRR09kQ9aXGr1oMVd5Y0iNti7iQg-1xY
{
	"input":"YLRMOZ0639CV"
}

 - 할당 사용 취소
PATCH
http://localhost:8080/coupon/cancle

header 추가
X-AUTH-TOKEN 
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMDY1Iiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTU5MDIyMjA3OSwiZXhwIjoxNTkwMzA4NDc5fQ.hakqvn-vPQNqRR09kQ9aXGr1oMVd5Y0iNti7iQg-1xY
{
	"input":"YLRMOZ0639CV"
}

- 만기쿠폰 조회
http://localhost:8080/coupon/expirelist

header 추가
X-AUTH-TOKEN 
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMDY1Iiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTU5MDIyMjA3OSwiZXhwIjoxNTkwMzA4NDc5fQ.hakqvn-vPQNqRR09kQ9aXGr1oMVd5Y0iNti7iQg-1xY

 - 파일업로드
http://localhost:8080/coupon/uploadCoupon

header 추가
X-AUTH-TOKEN 
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMDY1Iiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTU5MDIyMjA3OSwiZXhwIjoxNTkwMzA4NDc5fQ.hakqvn-vPQNqRR09kQ9aXGr1oMVd5Y0iNti7iQg-1xY

