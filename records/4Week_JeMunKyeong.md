# Title: [4Week] 제문경

### 미션 요구사항

- [필수1] 네이버클라우드플랫폼을 통한 배포, 도메인, HTTPS 까지 적용
- [필수2] 내가 받은 호감리스트(/usr/likeablePerson/toList)에서 성별 필터링기능 구현
- [선택1] 내가 받은 호감리스트(/usr/likeablePerson/toList)에서 호감사유 필터링기능 구현
- [선택2] 내가 받은 호감리스트(/usr/likeablePerson/toList)에서 정렬기능
- [선택3] 젠킨스를 통해서 리포지터리의 main 브랜치에 커밋 이벤트가 발생하면 자동으로 배포가 진행되도록

<br>

### 체크 리스트

**필수미션**

- [x]  네이버 클라우드 플랫폼을 통한 배포 및 도메인, HTTPS까지 적용
- [x]  내가 받은 호감리스트에서 성별 필터링 기능 구현

<br>

**선택 미션**

- [x]  내가 받은 호감리스트에서 호감 사유 필터링 기능 구현
- [x]  내가 받은 호감리스트에서 정렬기능
- [ ]  젠킨스를 통해서 리포지터리 main 브랜치에 커밋 이벤트가 발생하면 자동으로 배포가 진행되도록 구현

<br>

## 4주차 미션 요약
### **[접근 방법]**

### [필수1] 네이버 클라우드 플랫폼을 통한 배포 및 도메인, HTTPS까지 적용

- `https://codelike.site` 형태로 접속 가능
- 변경사항 구현 후 재배포
  <br>

### [필수2] 내가 받은 호감리스트에서 성별 필터링 기능 구현

- 파라미터 요청 값이 gender=”W” 이거나 gender”M”이면 리스트 중 해당 성별에 대한 데이터만 나오도록 필터링 기능 구현
- null이나 비어있지 않다면 필터링 적용

    ```java
    if (gender != null&& !gender.isBlank()) { // 성별로 필터링
                    filteredPeople = filteredPeople
                            .filter(person -> person.getFromInstaMember().getGender().equals(gender));
                }
    }
    ```

<br>

### [선택1] 내가 받은 호감 리스트에서 호감 사유로 필터링 기능 구현

- 성별 필터링과 마찬가지로 일치 하는 지의 여부로 필터링 기능 구현
- default 값이 0이기 때문에, 0 아니라면 필터링

```java
if (attractiveTypeCode != 0) { // 호감사유로 필터링
                filteredPeople = filteredPeople
                        .filter(person -> person.getAttractiveTypeCode() == attractiveTypeCode);
            }
}
```
<br>

### [선택2] 내가 받은 호감 리스트에서 정렬 기능 구현

- switch문을 사용하여 각 번호마다 기준에 맞게 구현

```java
switch (sortCode) {
                case 2: // 날짜순 (오래전에 받은 호감표시 우선)
                    primaryComparator = Comparator.comparing(LikeablePerson::getCreateDate);
                    break;
                case 3: // 인기 많은 순 member.instaMember.likes
                    primaryComparator = Comparator.comparing(LikeablePerson::getFromInstaMember,
                            Comparator.comparingLong(InstaMember::getLikes).reversed());
                    break;
                case 4: // 인기 적은 순
                    primaryComparator = Comparator.comparing(LikeablePerson::getFromInstaMember,
                            Comparator.comparingLong(InstaMember::getLikes));
                    break;
                case 5: // 성별순 (여성에게 받은 호감표시 먼저)
                    primaryComparator = Comparator.comparing(LikeablePerson::getFromInstaMember,
                            Comparator.comparing(InstaMember::getGender)).reversed();
                    break;
                case 6: // 호감사유순 (외모, 성격, 능력 순)
                    primaryComparator = Comparator.comparing(LikeablePerson::getAttractiveTypeCode);
                    break;
                default: // 기본값인 경우 최신순으로 정렬
                    primaryComparator = Comparator.comparing(LikeablePerson::getCreateDate).reversed();
                    break;
            }
```


<br>

## **[특이사항]**

### [아쉬웠던 점]

정렬 시 모든 데이터에 대한 정렬이 되지 않고 성별을 선택해야 조건에 맞게 정렬되어서 이 부분을 해결하기 위해 여러 번 시도를 해보았는데 해결이 되지 않아 아쉬웠습니다.  미션이 끝난다면 이 부분에 대해 리팩토링하여 해결할 예정입니다.

<br>

### [Refactoring]

젠킨스를 사용하여 자동 배포 구현하기

정렬기능 이슈사항 해결하기(성별 선택하지 않고 전체 데이터에 대해서도 정렬 되도록)

<br>

### [1Week Refactoring]

- [x]  호감 목록 중 선택 상대 삭제 메소드 코드 리팩토링
- [x]  호감 목록 삭제 테스트 코드 추가
- [x]  yml ouath파일로 나누어 보이지 않도록
- [x]  로그인 UI 변경

<br>

### [2 Week Refactoring]

- [x]  호감상대 10이상일 경우 test 코드
- [x]  이미 추가한 호감상대의 사유가 같다면 추가 안 되도록 test 코드 구현
- [x]  사유가 같지 않다면 update 되도록 test 코드 구현
- [x]  사유가 같지 않을 때 update가 실행될 수 있는 코드 리팩토링

### [3Week Refactoring]

- [x]  알림 기능 추가 및 테스트코드 추가
- [x]  타이머 부분 리팩토링