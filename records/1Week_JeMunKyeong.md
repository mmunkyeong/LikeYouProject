# 1Week_제문경.md

## Title: [1Week] 제문경

### 미션 요구사항 분석 & 체크리스트

---

☑ 호감 목록 선택 삭제 구현

☑ 삭제 시 로그인 여부 확인

☑ 삭제 후 redirect 구현

☑ 구글/네이버 로그인 구현

☑ 구글/네이버 로그인  성공 시 member 테이블에 정보 추가

☑ 최초 로그인시 가입, 이후는 가입 x

### N주차 미션 요약

---
<br>

**[접근 방법]**

1. **실습 및 참고 예제**
- 미션 진행 전 점프 투 스프링부트 2회 복습
- 점프 투 스프링부트 수정/삭제구현 부분 예제를 참고하여 개발

[링크 3-10 수정과 삭제](https://wikidocs.net/162416)

<br>

2. **목표**
- 호감 목록 중 삭제 버튼 클릭 시 해당 호감 상대를 삭제
    - likeable_person 테이블에서 해당 호감 상대가 delete 되어야 함
- 구글, 네이버 로그인 구현
    - 구글, 네이버  로그인시  member테이블에  providerTypeCode: GOOGLE, NAVER로 저장

<br>

3. **결과물**
- 1️⃣  **호감 상대 삭제 구현 화면**

  ![Untitled (14)](https://user-images.githubusercontent.com/62290451/230005732-d3497892-a956-4f9e-97cc-b64eb3549622.png)
  호감 상대 등록

  ![Untitled (15)](https://user-images.githubusercontent.com/62290451/230005792-8e9c4ac5-a124-47e2-8dc0-f50c7d32b704.png)
  1234223444 삭제

  ![Untitled (16)](https://user-images.githubusercontent.com/62290451/230005827-6320b850-21e1-455f-b3c0-e9bc4655f3db.png)
  DB에서도 삭제된 화면

    <br>


- 2️⃣ **구글 로그인 화면**

  ![Untitled (17)](https://user-images.githubusercontent.com/62290451/230005856-7b5b6358-a4be-4046-90a2-581e816cbbf8.png)

  ![Untitled (18)](https://user-images.githubusercontent.com/62290451/230005883-35cffe71-d57a-4449-b90c-2f1afac40ace.png)

  로그인 성공시 DB에 저장

    <br>


- 3️⃣ **네이버 로그인 화면**

  ![Untitled (19)](https://user-images.githubusercontent.com/62290451/230005910-a77db258-0ed8-4cfe-a868-3953eac611f4.png)

  ![Untitled (20)](https://user-images.githubusercontent.com/62290451/230005937-cf3de5ea-cb62-4506-a20a-0eb15b327ca5.png)

  로그인 성공시 DB에 저장


---
<br>


**[특이사항]**

1. 궁금했던 점

   delete 메서드 구현 당시 프로그램이 돌아가는데 왜 DB에서 삭제되지 않는지 궁금했지만, @Transactional 애너테이션을 추가하니 제대로 동작할 수 있었습니다.


2. 아쉬웠던 점

   네이버 로그인은 따로 id를 처리해주지 않으면 다른 프로필, 연락처 등 정보가 그대로 username에 저장되어 네이버 로그인 시 고유 key만 저장될 수 있도록 직접 id 부분만 찾아 저장 하였는데, 더 좋은 방법이 있을 거  같다는 생각에 조금 아쉬웠습니다.


[Refactoring]

- 호감 상대 선택 후 삭제 부분 테스트 코드
- 호감 상대 제한 인원 ex. 10명