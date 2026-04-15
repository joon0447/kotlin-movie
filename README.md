# 기능 구현 사항

## 데이터베이스 저장
- [x] 데이터베이스 연결하기
- [x] 상영중인 영화 정보를 데이터베이스에 저장한다
  - movie 테이블
  - id, name, running_time_minutes
- [x] 영화 상영 정보를 데이터베이스에 저장한다
  - movie_screening 테이블
  - id, movie_id, screen_start, screen_end
- [ ] 예매 정보를 데이터베이스에 저장한다
  - reservation 테이블
  - id, screening_id, seat_row, seat_column, seat_grade, price, created_at