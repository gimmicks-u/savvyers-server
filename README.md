# Savvyers Server

한국 가공식품 영양정보 비교 서비스 **Savvyers**의 백엔드 API 서버입니다.

식품의약품안전처에서 제공하는 가공식품 영양성분 데이터를 기반으로, 사용자가 식품의 영양정보를 쉽게 검색하고 비교할 수 있는 REST API를 제공합니다.

### LIVE

- https://savvyers.net
- https://api.savvyers.net (API)

### 주요 기능

- 가공식품 영양정보 검색 (Elasticsearch 기반 고속 검색)
- 상품별 상세 영양성분 조회

## 기술 스택

- **Java** 17
- **Spring Boot** 3.5.5

### 주요 의존성

- **Spring Web MVC** - REST API 개발
- **Spring Data Elasticsearch** - Elasticsearch 연동
- **Spring Data JPA** - 데이터 영속성 (MySQL)
- **SpringDoc OpenAPI** - Swagger API 문서화 (`/swagger-ui.html`)
- **Lombok** - 보일러플레이트 코드 감소
- **Bean Validation** - 입력값 검증

## 프로젝트 구조

```
src/main/java/com/savvyers/savvyersserver/
├── SavvyersServerApplication.java    # 메인 애플리케이션
├── config/
│   ├── CorsConfig.java               # CORS 설정
│   └── ElasticsearchConfig.java      # Elasticsearch 설정
├── health/
│   └── HealthController.java         # 헬스체크 엔드포인트
└── product/
    ├── controller/
    │   └── ProductController.java    # 상품 API 컨트롤러
    ├── document/
    │   ├── HaccpData.java            # HACCP 데이터 문서
    │   └── ProductDocument.java      # 상품 Elasticsearch 문서
    ├── dto/
    │   └── request/
    │       ├── ProductIdRequest.java
    │       └── ProductSearchRequest.java
    ├── repository/
    │   └── ProductRepository.java    # Elasticsearch 레포지토리
    └── service/
        └── ProductService.java       # 비즈니스 로직
```

## 테스트

```bash
# 전체 테스트 실행
./gradlew test

# 테스트 리포트 확인
open build/reports/tests/test/index.html
```

## 빌드

```bash
# JAR 파일 빌드
./gradlew build

# 빌드 결과물
build/libs/savvyers-server-0.0.1-SNAPSHOT.jar
```

## 아키텍처

### 애플리케이션 아키텍처

```
┌─────────────────────────────────────────────────────────────────┐
│                          Client                                 │
└─────────────────────────────────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│                     Controller Layer                            │
│                    (REST Endpoints)                             │
└─────────────────────────────────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│                      Service Layer                              │
│                    (Business Logic)                             │
│                     ProductService                              │
└─────────────────────────────────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│                     Repository Layer                            │
│                      (Data Access)                              │
│                    ProductRepository                            │
└─────────────────────────────────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│                       Elasticsearch                             │
│                  (가공식품 영양정보 저장소)                           │
└─────────────────────────────────────────────────────────────────┘
```

### 배포 아키텍처

```
┌─────────────────────────────────────────────────────────────────┐
│                         GitHub Actions                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│   ┌──────────────┐    ┌──────────────┐    ┌──────────────┐      │
│   │    Test      │───▶│    Build     │───▶│   Deploy     │      │
│   │  (JUnit 5)   │    │ (Docker)     │    │              │      │
│   └──────────────┘    └──────────────┘    └──────────────┘      │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────────┐
│                        Production Server                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│   ┌────────────┐     ┌─────────────────────────────────────┐    │
│   │            │     │         Docker Containers           │    │
│   │   Nginx    │────▶│  ┌─────────┐      ┌─────────┐       │    │
│   │            │     │  │  Blue   │  OR  │  Green  │       │    │
│   │  (Reverse  │     │  │ :5201   │      │ :5202   │       │    │
│   │   Proxy)   │     │  └─────────┘      └─────────┘       │    │
│   │            │     │                                     │    │
│   └────────────┘     └─────────────────────────────────────┘    │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### 환경별 배포

#### Production: 무중단 배포 (Zero-downtime Deployment)

**Blue/Green 배포 방식**으로 무중단 배포를 수행합니다.

1. **새 버전 배포** - 비활성 컨테이너(Green)에 새 버전 배포
2. **Health Check** - `/health` 엔드포인트로 정상 동작 확인
3. **트래픽 전환** - Nginx upstream 설정 변경 후 reload
4. **정리** - 이전 컨테이너(Blue) 제거
