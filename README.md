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

#### .sql 파일에서 환경변수 불러올 때

- db 이름과 테이블 이름은 각각 \`${}\` 형식을 쓴다.
- 문자열 안에 넣고자 할때 '${}' 을 쓴다

#### nginx.conf 환경마다 달리 가져가기

만약 `/usr/share/nginx/html/nginx.conf` 에 설정파일이 위치되어 있다고 하면 `Dockerfile` 에서 아래와 같이 가져가면 될 듯하다.

```bash
CMD ["sh", "-c", "nginx -c /usr/share/nginx/html/nginx-${PROFILE}.conf -g 'daemon off;'"]
```

### 라이선스

이 프로젝트는 [MIT 라이선스](./LICENSE) 하에 배포됩니다.