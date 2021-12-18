# 회원 가입 API

### 실행 방법

``` bash
> ./gradlew build
> java -jar ./build/libs/member-api.jar 
```

### RestDocs 문서 보기

구현 된 API 스펙은 RestDocs 문서를 통해 확인이 가능 합니다.

서비스를 실행 한 후 아래 URL에 접속하여 문서를 확인이 가능합니다.

```
http://localhost:8080/docs/index.html
```

### 사용 기술

- Spring boot
- Spring Data JPA
- Spring Rest Docs
- H2 Database
- Junit5
- Checkstyle

### 구현 스펙

#### 전화번호 인증

[이슈보기](https://github.com/hi-phen/AB-HW/issues/1)
[PR보기](https://github.com/hi-phen/AB-HW/pull/8)

- 전화번호 인증을 담당하는 외부 서비스가 있다고 가정하고 작성하였습니다.
- 이 외부 서비스는 사용자에게 인증번호를 보내고, 검증이 완료되면 인증 토큰을 발급합니다.
- 본 서비스는 이 외부서비스를 이용하여 사용자의 요청을 받아 인증 토큰을 이용하여 관련 기능을 수행합니다.

#### 회원 가입

[이슈보기](https://github.com/hi-phen/AB-HW/issues/2)
[PR보기](https://github.com/hi-phen/AB-HW/pull/12)

- 요구사항에 맞게 값을 입력받아 회원가입을 진행합니다.
- 전화번호 인증 토큰을 전달받아 사전 인증 여부를 검증합니다. (인증 토큰 값이 있기만 하면 항상 검증 통과)
- 이메일과 전화번호는 중복이 불가 하고, 비밀번호는 `SHA-256` 으로 해싱하여 저장합니다.

#### 로그인

[이슈보기](https://github.com/hi-phen/AB-HW/issues/3)
[PR보기](https://github.com/hi-phen/AB-HW/pull/14)

- 이메일 또는 전화번호를 통해 로그인 할 수 있게 합니다.
- 로그인이 완료되면 인증 토큰을 전달합니다.
- 인증/인가와 관련된 기능은 `Spring Security` 를 써야 견고하지만 구현범위가 커지기
  때문에 [이슈](https://github.com/hi-phen/AB-HW/issues/6) 로 남겨두고 단순하게 구현하였습니다.

#### 내 정보 보기

[이슈보기](https://github.com/hi-phen/AB-HW/issues/4)
[PR보기](https://github.com/hi-phen/AB-HW/pull/15)

- 로그인 인증토큰을 전달받아 회원정보를 반환합니다.
- 인증토큰의 사용자 정보와 반환 할 사용자의 정보가 일치해야만 합니다.

#### 비밀번호 재설정

[이슈보기](https://github.com/hi-phen/AB-HW/issues/5)
[PR보기](https://github.com/hi-phen/AB-HW/pull/16)

- 전화번호 인증 토큰이 있어야 재설정이 가능합니다.
- 전화번호를 기반으로 회원을 조회 하여 비밀번호를 재설정 합니다.
- 인증 토큰의 전화번호 정보와 비밀번호를 재설정 할 전화번호가 동일해야 합니다.

#### 고려는 했지만 구현하지 않은 기능

[Open 된 이슈](https://github.com/hi-phen/AB-HW/issues) 를 통해 확인이 가능합니다.

### 더 말하고 싶은 것들

- 일감을 [이슈](https://github.com/hi-phen/AB-HW/issues) 로 관리하고
  관련 [PR](https://github.com/hi-phen/AB-HW/pulls?q=is%3Apr+is%3Aclosed) 로 완료하였습니다.
- 50개의 테스트를 작성하였습니다. [링크](https://github.com/hi-phen/AB-HW/blob/main/assets/Test_Results.html)
- `IntelliJ`를 사용하면 [http 파일](https://github.com/hi-phen/AB-HW/tree/main/http) 을 이용하여 실제 동작을 손쉽게 시험 해
  볼 수 있습니다.
- `Coverage`도 우수하게 나왔습니다. 근데 보통 이렇게까지는 안나옵니다.
  😇 [링크](https://github.com/hi-phen/AB-HW/blob/main/assets/coverage/index.html)
- `Git graph`를 깔끔하게 관리하였습니다. 아래 명령을 통해 확인 해 보세요.

```bash
  git log \
    --color --graph\
    --date=format:'%Y-%m-%d' \
    --pretty=format:'%C(cyan)%h%C(auto)%d %s %C(magenta)(%ad)%C(bold blue) %an'
```


