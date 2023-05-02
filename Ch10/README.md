## 1. TDD란?

- TDD(Test Driven Development)란 ‘테스트 주도 개발’을 의미
- TDD는 Agile 개발 방법론에 기반한 짧은 개발 주기의 반복에 의존하는 개발 프로세스로 단위 테스트를 작성하고 이를 통과하는 코드를 추가하는 단계를 반복하여 구현하는 개발 방식
- TDD 개발을 진행하면 디버깅 시간이 단축되고, 생산성이 향상되는 등 여러가지 이점을 얻을 수 있지만 기존 개발 방식에 익숙한 상황에서 생산성 저하의 우려로 TDD 도입이 쉽지 않음

![Untitled](https://user-images.githubusercontent.com/111489860/235751557-7941e785-8871-41ad-ac05-fd8ee103ad22.png)

## 2. JUnit 이란?

- JUnit은 TDD의 가장 대표적인 Java 단위 테스트 프레임워크
- 어노테이션 기반 단위 테스트(Unit Test)를 위한 도구 제공
- Spring Boot 2.2 부터 JUnit 5 사용

![Untitled](https://user-images.githubusercontent.com/111489860/235751573-071dcb6c-10e7-41ca-92d6-50a2acff9030.png)

## 3. Log 개요

- 로그(log)는 시스템이나 프로그램이 실행 중에 발생하는 모든 행위와 이벤트 정보 등을 시간의 경과에 따라 기록한 데이터
- 로깅(logging)은 애플리케이션이 동작하는 동안 시스템의 상태나 동작 정보를 시간순으로 기록하는 행위
- 스프링 부트는 logback 라이브러리 내장하고 있으며 @slf4j 사용하여 logger 사용
- 로그는 상태 및 목적에 따라 5단계로 나누어 로깅(log4j 기준)

![Untitled](https://user-images.githubusercontent.com/111489860/235751611-37f2a500-6d23-478e-ac77-15e142cc1f9e.png)
