# 제9장 Spring REST 웹서비스
## 1. API란?

- API(Application Programming Interface)란 프로그램과 프로그램 사이의 연결이다. 일종의 소프트웨어 인터페이스이며 다른 종류의 소프트웨어에 서비스를 제공한다. *위키백과-*
- API는 손님(프로그램)이 주문할 수 있게 메뉴(명령 목록)를 정리하고, 주문(명령)을 받으면 요리사(응용 프로그램)와 상호작용하여 요청된 메뉴(명령에 대한 값)를 전달하는 즉, API는 프로그램들이 서로 상호작용하는 것을 도와주는 매개체
- 일반적으로 API는 애플리케이션에서 요청을 보내고 응답을 받기 위해 정의된 명세(URL)를 의미

![Untitled](https://user-images.githubusercontent.com/111489860/235750803-6de94113-1f01-40c8-9a6c-18e8d671a52a.png)

## 2. REST API란?

- REST(Representational State Transfer)는 2000년 Roy Fielding의 박사가 제안한 네트워크에서 클라이언트와 서버 사이의 통신을 구현하는 방법에 대한 이론으로 하나의 자원은 여러 형태의 Representation(json, xml, text, rss 등)으로 전달할 수 있다는 개념
- REST API는 URI + Method를 의미하고 URI를 통해 제어할 자원을 명시하고, Method를 통해 해당 Resource를 제어하는 명령을 내리는 방식의 아키텍처를 의미

![Untitled](https://user-images.githubusercontent.com/111489860/235750853-fbbf6103-50c9-478d-886f-cfde7fcc2437.png)
## 3. REST API 등장배경

- 웹은 기본적으로 클라이언트 요청에 대한 응답으로 화면 중심의 HTML을 제공하는 시스템이기 때문에 단순히 데이터를 주고받고자 하는 서비스에는 적합하지 않음
- 이에 따라 확장성이 뛰어나고 경량의 데이터 구조인 JSON이 주목을 받기 시작함
- REST는 현재 HTTP와 JSON을 함께 사용하여 Open API를 구현하는 형태로 많이 사용
- 일반적으로 REST 원칙을 따르는 서비스를 RESTful 서비스라고 말함

![Untitled](https://user-images.githubusercontent.com/111489860/235750885-24e7922b-40bf-4d7c-9f95-7942cae7f862.png)
