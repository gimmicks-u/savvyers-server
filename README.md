## 프로젝트 개요

온라인 쇼핑 환경에서 가공식품의 영양 정보를 손쉽게 비교하여 건강한 식품 선택을 돕는 웹 서비스입니다. 사용자가 겪는 '여러 탭을 오가며 영양정보를 수기로 비교하는 불편함'을 해결하는 데 집중하였습니다.

원하는 가공식품을 검색하고, 최대 4개 제품의 영양성분을 레이더 차트, 상세 표를 통해 한눈에 시각적으로 비교할 수 있습니다. 현재 MVP 단계로, 가공식품 검색 및 영양정보 비교 기능을 중심으로 구현되었습니다.

기획부터 디자인, 프론트엔드, 백엔드 개발, 배포 및 운영까지 전 과정을 **1인으로 수행한 개인 프로젝트**입니다. 요구사항 정의, UX 설계, API 구현, 인프라 구성과 운영까지 서비스를 처음부터 끝까지 구축하는 경험을 목표로 진행했습니다.

## 링크

- **서비스 URL**: [https://savvyers.net](https://savvyers.net/)
- **API 서버**: [https://api.savvyers.net](https://api.savvyers.net/)
- **GitHub**
  - Frontend: https://github.com/gimmicks-u/savvyers-client
  - Backend: https://github.com/gimmicks-u/savvyers-server

## 기술 스택(BE)

### Backend

- Language: Java 17
- Framework: Spring Boot 3.5.5
- Build Tool: Gradle
- Search Engine: Elasticsearch
- API Documentation: SpringDoc OpenAPI (Swagger)
- Validation: Jakarta Bean Validation
- Test: JUnit 5

### Infrastructure

- Server: Ubuntu 24 홈서버 (Mini PC)
- Container: Docker
- Reverse Proxy: Nginx
- SSL/TLS: Let's Encrypt (Certbot)
- DNS/CDN: Cloudflare
- CI/CD: GitHub Actions

## 주요 기능 및 API 설계

### **검색 최적화 전략**

Elasticsearch를 활용한 고성능 검색 기능을 구현하였습니다.

- Nori 형태소 분석기를 통한 한국어 자연어 처리
- N-gram 인덱싱을 통한 부분 일치 검색 지원
- Cross-fields 쿼리를 통한 다중 필드 통합 검색
- 제품명(food_nm)에 가중치 부여를 통한 검색 정확도 향상

### 데이터

영양 정보 데이터는 한국 식품 데이터를 사용했습니다.

## 프로젝트 구조

```
savvyers-server/
├── src/main/java/com/savvyers/savvyersserver/
│   ├── SavvyersServerApplication.java
│   ├── config/
│   │   ├── CorsConfig.java
│   │   └── ElasticsearchConfig.java
│   ├── health/
│   │   └── HealthController.java
│   └── product/
│       ├── controller/ProductController.java
│       ├── service/ProductService.java
│       ├── repository/ProductRepository.java
│       ├── document/
│       │   ├── ProductDocument.java
│       │   └── HaccpData.java
│       └── dto/
│           └── request/
├── deploy/
│   ├── prod/
│   │   ├── docker-compose.yml
│   │   ├── dockerfile
│   │   ├── deploy-prod.sh
│   │   └── nginx/
│   │       ├── upstream-blue.conf
│   │       └── upstream-green.conf
│   ├── dev/
│   └── local/
└── .github/workflows/
    ├── deploy-prod.yml
    └── deploy-dev.yml
```

## 시스템 아키텍처

```
                                    [Cloudflare]
                                         |
                                    [HTTPS/SSL]
                                    (Let's Encrypt)
                                         |
                                      [Nginx]
                                   (Reverse Proxy)
                                         |
                          +--------------+--------------+
                          |                             |
                    [Blue Container]            [Green Container]
                     (Port 5201)                  (Port 5202)
                          |                             |
                          +--------------+--------------+
                                         |
                                  [Elasticsearch]
                                   (shared-elastic)
```

### 아키텍처 특징

1. **홈서버 기반 인프라 구축**
   - 미니 PC를 활용한 자체 서버 환경 구성
   - Docker 컨테이너를 통한 애플리케이션 격리 및 관리
   - 비용 효율적인 개인 프로젝트 운영 환경 구현
2. **네트워크 및 보안**
   - Cloudflare를 통한 DNS 관리 및 DDoS 방어
   - Let's Encrypt(Certbot)를 활용한 무료 SSL 인증서 자동 갱신

---

## 무중단 배포 (Blue/Green Deployment)

서비스 가용성을 보장하기 위해 Blue/Green 배포 전략을 사용했습니다.

### 배포 프로세스

```
1. Docker 이미지 빌드 (GitHub Actions)
     |
2. 이미지 tarball 생성 및 서버 전송
     |
3. 현재 활성 컨테이너 확인
     |
     +-- Blue 활성 --> Green에 배포
     |
     +-- Green 활성 --> Blue에 배포
     |
4. 신규 컨테이너 기동
     |
5. Health Check (최대 30회 재시도)
     |
6. Nginx upstream 설정 교체
     |
7. Nginx 설정 검증 및 reload
     |
8. 기존 컨테이너 종료
```

## CI/CD 파이프라인

GitHub Actions를 활용하여 테스트부터 배포까지 자동화된 파이프라인을 구축하였습니다.

### 파이프라인 구성

```yaml
name: Deploy Prod (Blue/Green)

on:
  push:
    branches: [main]

jobs:
  test:
    # JDK 17 환경에서 Gradle 테스트 실행
    # 테스트 결과 아티팩트 업로드

  deploy:
    needs: test
    # Docker 이미지 빌드
    # 서버로 이미지 전송
    # Blue/Green 배포 스크립트 실행
```

### 주요 기능

1. **자동 테스트**
   - main 브랜치 push 시 자동 테스트 실행
   - 테스트 실패 시 배포 중단
   - 테스트 결과 리포트 아티팩트 저장
2. **보안 관리**
   - GitHub Secrets를 통한 민감 정보 관리
   - SSH 키 기반 서버 접근
   - 환경 변수 파일 동적 생성
3. **배포 최적화**
   - 배포 스크립트 변경 감지 시에만 스크립트 재전송
   - Docker 이미지 캐시 비활성화를 통한 일관된 빌드
