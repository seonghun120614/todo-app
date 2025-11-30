# todo-app

멋사 19기 실습으로 todo 서비스를 spring-boot 기반 웹서비스를 제공하는 미니 프로젝트입니다.

## 목차

- [Deploy](#deploy)
- [API](#api)
- [시나리오](#시나리오)
- [기술 스택](#기술-스택)
- [배운 점](#배운-점)
- [라이선스](#라이선스)

### Deploy

**구동 방법**

```bash
./CICD.sh
```

### API

- `GET /api/todos` (전체 조회)
- `POST /api/todos` (생성)
- `PUT /api/todos/{id}` (수정)
- `DELETE /api/todos/{id}` (삭제)

### 시나리오



### 배운 점

#### Spring Boot 빌드를 위한 Gradle 의 필요 파일

- `build.gradle`
- `settings.gradle`
- `src/main/*`
- `src/test/*` -> 테스트 하고 싶을때
- `gradlew`
- `gradle/`

혹은 

```gradle
tasks.named('bootBuildImage') {
    imageName = "todo-app:0.1"
    environment = [
            "BP_JVM_VERSION": "21"
    ]
}
```

```bash
./gradlew bootBuildImage    # 이미지 빌드
docker images               # 확인
```

또한 gradle 문법을 사용하면 다음과 같이 인자로도 받을 수 있다.

```gradle
tasks.named('bootBuildImage') {
    def imgName = project.hasProperty('imageName')
            ? project.property('imageName')
            : 'todo-back:latest'
    imageName = imgName
    environment = [
            "BP_JVM_VERSION": "21"
    ]
}
```

project 는 예약된 변수인 듯하다(추후에 공부 더 필요).

#### Nginx 의 기본 홈페이지 경로

`/usr/share/nginx/html/`

#### Docker Container 의 자동 실행 경로

`/docker-entrypoint-initdb.d/` 폴더에 있는 `.sql`, `.sh`, `.sql.gz` 를 자동으로 실행

#### nginx.conf 환경마다 달리 가져가기

만약 `/usr/share/nginx/html/nginx.conf` 에 설정파일이 위치되어 있다고 하면 `Dockerfile` 에서 아래와 같이 가져가면 될 듯하다.

```bash
CMD ["sh", "-c", "nginx -c /usr/share/nginx/html/nginx-${PROFILE}.conf -g 'daemon off;'"]
```

#### DBeaver(디비버) Public Key Retrieval is not allowed

MySQL JDBC 연결 문자열에 붙여 쓰는 파라미터들이며, `DBeaver` 에서 MySQL 접속 시 자주 보는 오류인 `Public Key Retrieval is not allowed` 를 해결하는 설정이다.

오류 원인은 MySQL 8.x 이상에서 `caching_sha2_password` 인증 방식을 사용할 때, 클라이언트(`DBeaver`, ...) 가 서버로부터 암호화용 public key를 자동으로 받아오는 기능이 기본적으로 금지되어 있다.

아래 처럼 public key 자동 요청이 필요한 상황이면 오류가 뜬다:
- `useSSL=false` 또는 SSL 이 제대로 설정 안되어 있으면 에러
- MySQL 서버가 RSA 공개키 파일을 제공하지 않으면 에러

이를 다음과 같이 해결한다.

```yml
allowPublicKeyRetrieval=true&useSSL=true
```

- `allowPublicKeyRetrieval=true` : 서버에서 RSA 공개키를 자동으로 가져오는 것을 허용, public key 를 받아와서 암호를 암호화한 수 전송 가능, 보안성이 떨어지기 때문에 SSL 을 켜지 않은 경우 위험
- `useSSL=true`: MySQL 클라이언트와 서버 사이 통신 시 SSL/TLS 암호화 사용

#### DB 와 Back Container 간의 실행 시각 조정

시간 상 DB 가 구동되는 시간보다 Back Container 가 구동되는 시간이 더 클 수 있다.  
이때 사용할 수 있는게 healthcheck 이며, back 은 db 가 service healthy 한지 체크 후에  
정상적으로 구동이 시작된다.

```docker
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 5s
      timeout: 3s
      retries: 10
...
    depends_on:
      db:
        condition: service_healthy
```

#### WebMvcTest

테스트를 하기 위해 다른 layer 를 올릴 필요 없이 테스트를 수행하고 싶다면 다음 애노테이션을 사용하자.

```java
@WebMvcTest(TodoController.class)
```

### 라이선스

이 프로젝트는 [MIT 라이선스](./LICENSE) 하에 배포됩니다.