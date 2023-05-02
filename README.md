# 6장 SpringBoot

## 1. Spring Boot 개요

- Spring Boot는 기존 Spring MVC 프로젝트를 보다 편리하고 가볍게 사용할 수 있도록 해주며 특히 웹 애플리케이션 개발에 최적화 된 프레임워크
- Spring Boot는 복잡한 Spring 설정을 자동화하고 내장 WAS를 통해 단독 웹 애플리케이션으로 실행
- Spring5 버전 이후로 Spring MVC 는 Spring Boot로 대체되는 추세
- Spring Boot 기본 빌드 도구는 Gradle

![Untitled](https://user-images.githubusercontent.com/111489860/235744243-9b6b1cf1-869f-4f43-8780-794a74683290.png)

## 2. 템플릿 엔진 개요

- 템플릿 엔진은 데이터와 이 데이터를 표현해줄 템플릿을 결합해주는 도구이다.
- 템플릿은 HTML과 같은 마크업 문서이고 데이터는 DB에 저장된 데이터를 의미한다.
- 템플릿 엔진을 이용해서 사용자에게 제공되는 화면과 데이터를 분리하여 관리할 수 있다.
- Spring Boot가 지원하는 템플릿 엔진은 Jsp, Thymeleaf, Freemarker, Mustache, Groovy Templates 등이 있다.
- Spring Boot 에서 기본 템플릿 엔진으로 JSP 대신 Thymeleaf 사용을 권장

![Untitled](https://user-images.githubusercontent.com/111489860/235744286-dc18108c-992b-492f-823d-748ba4fa78ed.png)

## 3. Thymeleaf

- Spring Boot에서 지원하는 기본 템플릿 엔진
- HTML 태그에 Thymeleaf 속성(th:xxx)을 추가해 페이지에 동적으로 값을 추가하거나 처리 한다.
- HTML 태그에 Thymeleaf 선언 속성(xmlns:th="http://www.thymeleaf.org")을 추가

![Untitled](https://user-images.githubusercontent.com/111489860/235744325-cea12cac-d5f8-425c-9dd6-00009d637887.png)

## 4. Lombok

- Lombok은 Java 프로그래밍에서 반복되는 getter, setter, toString 등 반복 되는 메서드 로직 작성을 줄여주는 코드 다이어트 라이브러리
- Lombok은 Spring Boot 에서 사용하는 필수 라이브러리
- Lombok 라이브러리를 사용하기 위한 기본 설치

## 5. Spring Boot 실습

- 환경 설정
    1. 프로젝트 생성 : new → new Spring Starter Project
    
    ![Untitled](https://user-images.githubusercontent.com/111489860/235744364-5109bdaf-f401-4958-a438-00a1509ae907.png)
    
    1. next 를 누르고 아래와 같이 라이브러리 추가 → finish
        
        ![Untitled](https://user-images.githubusercontent.com/111489860/235744400-935a41f3-13d1-4cc8-bd2c-0819c3533630.png)
        
    
    3. application.xml 설정
    
    ```xml
    server.servlet.context-path=/Ch06  // 컨택스트 루트
    server.port=80                     // 포트 번호
    spring.thymeleaf.cache=false       
    // 캐시 저장을 하지않아서 CSS, JS 파일을 저장하지 않으므로 매번 캐시 비우기를 하지 않아도 됨
    // 해당 설정은 개발시에만 한다.
    ```
    

1. Eclipse Market 에서 플러그인 설치
    1. Eclipse Web Developer Tools 3.28 설치
        
              - HTML, CSS 파일 등을 생성할 수 있다.
        
        ![Untitled](https://user-images.githubusercontent.com/111489860/235744447-75e7e1b6-8d16-427e-ac7a-5bc4a8c20158.png)
        
    2. Thymeleaf Plygin For Eclipse 3.0.1 설치(적용 안됨)
        - <th:> 태그를 자동 완성시켜준다.
    
    ![Untitled](https://user-images.githubusercontent.com/111489860/235744500-a49fd0c4-c463-4e5e-a7c7-bf94125bfefc.png)
    
2. Lombok 라이브러리 설치 (Setter, getter 등.. 에노테이션 제공)   [라이브러리 다운로드](https://projectlombok.org/download)
- 다운로드를 한 뒤에 jar 파일을 sts가 저장된 폴더에 이동시킨다.

![Untitled](https://user-images.githubusercontent.com/111489860/235744537-44ca74ab-4b04-4318-a14f-7b4e83f48ea3.png)

- Git Bash를 열고 아래와 같이 명령어를 실행
    
    ![Untitled](https://user-images.githubusercontent.com/111489860/235744581-ce168532-b542-448d-88d6-f84955736df6.png)
    
- 설치를 진행하고 하면 아래와 같이 파일이 생성된다.
    
    ![Untitled](https://user-images.githubusercontent.com/111489860/235744645-3be09c72-9c38-4e93-99f5-55eec687596e.png)
    

- Setter, Getter, 생성자 메서드를 자동으로 생성해준다. (@Data 선언으로 모두 생성할 수 있음)

![Untitled](https://user-images.githubusercontent.com/111489860/235744667-f355f36b-d304-4db5-b9be-51250fee6b15.png)

