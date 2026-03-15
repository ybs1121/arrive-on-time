# 작업 목록

## 현재 목표
추천 출발 시간 계산 MVP를 완성한다.

## 마일스톤 1. 프로젝트 초기 세팅
- [ ] Spring Boot 프로젝트 생성
- [ ] Java 17 설정
- [ ] Spring Web, JPA, H2, Validation 의존성 추가
- [ ] 기본 패키지 구조 생성
- [ ] 공통 예외 처리 구조 생성

## 마일스톤 2. 도메인 모델 설계
- [ ] ArrivalPolicy 생성
- [ ] TravelDuration 생성
- [ ] BufferMinutes 생성
- [ ] DepartureRecommendation 생성
- [ ] RecommendationEngine 생성
- [ ] BufferPolicy 생성

## 마일스톤 3. 도메인 테스트 작성
- [ ] 엄격 도착일 때 추천 출발 시간 계산 테스트
- [ ] 허용 지연 시간이 있을 때 계산 테스트
- [ ] 장거리일 때 추가 버퍼 반영 테스트
- [ ] 잘못된 입력값 예외 테스트

## 마일스톤 4. 애플리케이션 계층 구성
- [ ] RecommendDepartureCommand 생성
- [ ] RecommendDepartureService 생성
- [ ] RecommendDepartureUseCase 인터페이스 생성
- [ ] 입력 검증 흐름 정리

## 마일스톤 5. 외부 API 포트 정의
- [ ] PlaceResolver 인터페이스 정의
- [ ] RouteProvider 인터페이스 정의

## 마일스톤 6. 인프라 구현
- [ ] KakaoPlaceResolver 구현
- [ ] KakaoRouteProvider 구현
- [ ] 외부 응답 DTO 정의
- [ ] 외부 응답을 내부 모델로 변환하는 매핑 구현

## 마일스톤 7. 영속성 구현
- [ ] Recommendation 엔티티 생성
- [ ] RecommendationRepository 생성
- [ ] 추천 결과 저장 기능 구현
- [ ] 추천 이력 조회 기능 구현

## 마일스톤 8. API 구현
- [ ] POST /api/recommendations 구현
- [ ] GET /api/recommendations 구현
- [ ] 요청/응답 DTO 정의
- [ ] Validation 적용
- [ ] 예외 응답 포맷 정리

## 마일스톤 9. 최소 UI 구현
- [ ] 추천 요청 폼 화면
- [ ] 추천 결과 화면
- [ ] 최근 추천 목록 화면

## 이후 확장 후보
- [ ] 추천 기준 여러 개 제공
- [ ] 자주 가는 장소 저장
- [ ] 미래 시점 기준 추천 고도화
- [ ] 알림 기능
- [ ] 일정 연동