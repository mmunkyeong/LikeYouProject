# 2Week_제문경.md

## Title: [2Week] 제문경

### 미션 요구사항 분석 & 체크리스트 🔍

---
- [x]  이미 등록된 호감 상대의  호감 사유가 이전과 같다면 `insert`하지 못함
- [x]  이미 등록된 호감 상대의  호감 사유가 이전과 다르다면 `update` 수행
- [x]  호감 상대추가는 10명까지 가능
- [x]  호감 상대가 10명 이상이라면 경고창과 함께 추가 안 되도록 구현
- [ ]  각 기능에 대한 테스트 코드
- [x]  네이버 로그인

<br>

### N주차 미션 요약 📋

---

**[접근 방법]**

1. **목표**
- 이미 호감을 표시한 상대가 같은 사유로 호감표시를 했다면, 경고창과 함께 추가 안되도록 구현
    - likeable_person 테이블에서 insert가 되면 안됨
    - 구현 방법: from과 to InstamemberId를 repository에 추가, toInstaMemberId와 attractiveTypeCode를 repository에 추가하여 List로 받아와서 이미 존재하는 from, to id이고 attrativeTypeCode도 같다면 추가되지 않도록 구현
    - from,to InstaMemberId는 같지만 attractiveTypeCode가 다르다면 기존 attractiveTypeCode는 삭제하고 새로 들어온 attractiveTypeCode를 추가하여 구현

- 호감 상대는 최대 10명까지 등록가능
    - fromInstaMemberId를 List로 받아와 10명이상일 경우 경고창과 함께 추가 안되도록 구현

- 네이버 로그인
    - 1Week 미션 수행 당시 구현, 네이버는 다른 정보들이 같이 나오기 때문에 id=이후로 ,가 오기 전까지 잘라서 저장하여 id만 저장될 수 있도록 구현

---
<br>

**[특이사항]**

1. 궁금했던 점

   이미 호감을 표시한 상대의 attractiveTypeCode이 같은 경우라면 다시 set을 해주도록 처음에 구현하였지만, 작동은 하는데 db변경이 안되거나 이미 호감을 표시한 상대가 또 다른 사유로 db에 insert되어서 직접 delete 후에 다시 save를 해주었습니다. 왜 attractiveTypeCode를 set 했을 때 update가 안되는지 궁금했습니다.


2. 아쉬웠던 점

   위의 내용이 이번주 미션을 구현하면서 가장 아쉬웠던 부분 같습니다. 여러 방법을 시도 해봤는데 update가 안 되어서 직접 삭제하고 다시 save를 해주었습니다. update를 할 수 있는 좋은 방법이 있을 거 같아 아쉬웠습니다.

<br>

[Refactoring]

- 호감상대 10이상일 경우 test 코드
- 이미 추가한 호감상대의 사유가 같다면 추가 안 되도록 test 코드 구현
- 사유가 같지 않다면 update 되도록 test 코드 구현
- 사유가 같지 않을 때 update가 실행될 수 있는 코드 리팩토링

<br>

[1️⃣Week Refactoring ]

- [x]  호감 목록 중 선택 상대 삭제 메소드 코드 리팩토링
- [x]  호감 목록 삭제 테스트 코드 추가
- [x]  yml ouath파일로 나누어 보이지 않도록
- [x]  로그인 UI 변경