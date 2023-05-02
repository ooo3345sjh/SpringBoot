# **7장 JPA**

## 1. JPA의 개요

- JPA(Java Persistence API)는 자바 ORM 기술에 대한 API 표준으로 Java를 이용해서 데이터를 유지관리(Data Access) 할 수 있는 기술
- Spring에서는 JPA를 쉽게 사용할 수 있도록 Spring Data JPA 라이브러리를 지원
- JPA는 특정 DBMS에 종속되지 않고, 객체지향적인 설계로 좀 더 직관적이고 비즈니스 로직에 집중
- JPA는 복잡한 SQL 실행이 어렵다는것과 JPA를 애플리케이션 개발에 제대로 활용하기 위해서는 많은 경험과 학습을 요구

![Untitled](https://user-images.githubusercontent.com/111489860/235747314-e0fd267b-0c23-4e92-91bf-82698035e78b.png)

## 2. JPA 주요 컴포넌트

![Untitled](https://user-images.githubusercontent.com/111489860/235747377-e4c46950-421c-4113-89b6-d8d6ba7145d8.png)

## 3. JPA Entity와 Entity Manager

- Entity는 Database Table에 대응하는 Java 클래스
- Java 클래스에 @Entity 선언으로 Entity 객체 생성
- Entity Manager는 영속성 컨텍스트(Persistence Context)에서 여러 Entity를 관리하는 객체
- 영속성 컨텍스트는 Entity를 1차 Cache 에 저장하고 Entity Manager를 통해 접근제어

![Untitled](https://user-images.githubusercontent.com/111489860/235747423-6bb49b2e-5008-4f72-9a11-c808bc2f923e.png)

## 4. Spring JPA 실습

1. VO 생성

```java
package kr.co.ch07.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

// 아래의 두 태그를 선언해주어야 한다.(@Entity, @Table)
@Entity
@Table(name = "user1") // name값은 DB 테이블명으로 작성 
public class User1VO {
	
	@Id

 // 해당 에너테이션은 컬럼 값에 Auto_increment가 적용되었을때 선언한다.
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String uid;
	private String name;
	private String hp;
	private int age;
}
```

2. Repository 생성

```java
package kr.co.ch07.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.co.ch07.vo.User1VO;

@Repository // 생략가능 - @Repository가 선언된 부모 클래스를 상속받기 때문에
public interface User1Repo extends JpaRepository<User1VO, String>{ // <"VO객체타입", "PK키 타입">

	// JPA 쿼리메서드
	
	// 1. SELECT * FROM `user1` WHERE `uid`=?;
	public User1VO findUser1VOByUid(String uid);
	
	// 2. SELECT * FROM `user1` WHERE `name`=?;
	public List<User1VO> findUser1VOByName(String name);

	// 3. SELECT * FROM `user1` WHERE `name`!=?;
	public List<User1VO> findUser1VOByNameNot(String name);
	
	// 4. SELECT * FROM `user1` WHERE `uid`=? and `name`=?;
	public User1VO findUser1VOByUidAndName(String uid, String name);

	// 5. SELECT * FROM `user1` WHERE `uid`=? OR `name`=?;
	public List<User1VO> findUser1VOByUidOrName(String uid, String name);
	
	// 6. SELECT * FROM `user1` WHERE `age` > ?;
	public List<User1VO> findUser1VOByAgeGreaterThan(int age);
	
	// 7. SELECT * FROM `user1` WHERE `age` >= ?;
	public List<User1VO> findUser1VOByAgeGreaterThanEqual(int age);

	// 8. SELECT * FROM `user1` WHERE `age` < ?;
	public List<User1VO> findUser1VOByAgeLessThan(int age);
	
	// 9. SELECT * FROM `user1` WHERE `age` <= ?;
	public List<User1VO> findUser1VOByAgeLessThanEqual(int age);
	
	// 10. SELECT * FROM `user1` WHERE `name` LIKE '?'
	public List<User1VO> findUser1VOByNameLike(String name); // name 앞 또는 뒤에 %, _명시

	// 11. SELECT * FROM `user1` WHERE INSTR(`name`, ?);  
	public List<User1VO> findUser1VOByNameContains(String name);

	// 12. SELECT * FROM `user1` WHERE `name` LIKE '?%';
	public List<User1VO> findUser1VOByNameStartsWith(String name);

	// 13. SELECT * FROM `user1` WHERE `name` LIKE '%?';
	public List<User1VO> findUser1VOByNameEndsWith(String name);
	
	// 14. SELECT * FROM `user1` ORDER BY `name` DESC;
	public List<User1VO> findUser1VOByOrderByName();

	// 15. SELECT * FROM `user1` ORDER BY `age` ASC;
	public List<User1VO> findUser1VOByOrderByAgeAsc();

	// 16. SELECT * FROM `user1` ORDER BY `age` DESC;
	public List<User1VO> findUser1VOByOrderByAgeDesc();

	// 17. SELECT * FROM `user1` WHERE `age` > ? ORDER BY `age` DESC;  
	public List<User1VO> findUser1VOByAgeGreaterThanOrderByAgeDesc(int age);
	
	// 18. SELECT COUNT(*) FROM `user1` WHERE `uid` = ?;
	public int countUser1VOByUid(String uid);

	// 19. SELECT COUNT(*) FROM `user1` WHERE `name` = ?;
	public int countUser1VOByName(String name);
	
	// JPQL
	@Query("SELECT u1 FROM User1VO as u1 WHERE age < 30")
	public List<User1VO> selectUserUnderAge30();
	
	@Query("SELECT u1 from User1VO as u1 WHERE u1.name = ?1")
	public List<User1VO> selectUserByName(String name);
	
	@Query("SELECT u1 from User1VO as u1 WHERE u1.name = :name")
	public List<User1VO> selectUserByNameWithParam(@Param("name") String name);
	
}
```

3. Service 생성

```java
package kr.co.ch07.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ch07.repository.User1Repo;
import kr.co.ch07.vo.User1VO;

@Service
public class User1Service {

	@Autowired
	private User1Repo repo;
	
	public void insertUser1(User1VO vo) {
		repo.save(vo);
	}

	public User1VO selectUser1(String uid) {
		User1VO user = repo.findById(uid).get(); 
		return user;
	}
	
	public List<User1VO> selectUser1s() {
		List<User1VO> users = repo.findAll();
		return users;
	}
	
	public void updateUser1(User1VO vo) {
		repo.save(vo);
	}

	public void deleteUser1(String uid) {
		repo.deleteById(uid);
	}
}
```

4. Controller 생성

```java
package kr.co.ch07.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.ch07.service.User1Service;
import kr.co.ch07.vo.User1VO;

@Controller
public class User1Controller {

	@Autowired
	private User1Service service;
	
	@GetMapping("/user1/list")
	public String list(Model model) {
		model.addAttribute("users", service.selectUser1s()); 
		return "/user1/list";
	}
	
	@GetMapping("/user1/register")
	public String register() {
		return "/user1/register";
	}
	
	@PostMapping("/user1/register")
	public String register(User1VO vo) {
		service.insertUser1(vo);
		return "redirect:/user1/list";
	}
	
	@GetMapping("/user1/modify")
	public void modify(Model model, String uid) {
		model.addAttribute("user", service.selectUser1(uid));
	}

	@PostMapping("/user1/modify")
	public String modify(User1VO vo) {
		service.updateUser1(vo);
		return "redirect:/user1/list";
	}
	
	@GetMapping("/user1/delete")
	public String delete(String uid) {
		service.deleteUser1(uid);
		return "redirect:/user1/list";
	}
	
	
}
```

6. 화면 출력
    - list.html
    
    ```java
    <!DOCTYPE html>
    <html xmlns:th="http://www.thymeleaf.org">
    	<head>
    		<meta charset="UTF-8">
    		<title>user1::list</title>
    	</head>
    <body>
    	<h3>user1 목록</h3>
    	<a th:href="@{/}">Ch07 메인</a>
    	<a th:href="@{/user1/register}">user1 등록</a>
    	
    	<table border="1">
    		<tr>
    			<th>아이디</th>
    			<th>이름</th>
    			<th>휴대폰</th>
    			<th>나이</th>
    			<th>관리</th>
    		</tr>
    		<tr th:each="user:${users}">
    			<td>[[${user.uid}]]</td>
    			<td>[[${user.name}]]</td>
    			<td>[[${user.hp}]]</td>
    			<td>[[${user.age}]]</td>
    			<td>
    				<a th:href="@{/user1/modify(uid=${user.uid})}">수정</a>			
    				<a th:href="@{/user1/delete(uid=${user.uid})}">삭제</a>			
    			</td>
    		<tr>
    	</table>
    </body>
    </html>
    ```
    
    - register.html
        
        ```java
        <!DOCTYPE html>
        <html xmlns:th="http://www.thymeleaf.org">
        	<head>
        		<meta charset="UTF-8">
        		<title>user1::register</title>
        	</head>
        <body>
        	<h3>user1 등록</h3>
        	<a th:href="@{/}">Ch07 메인</a>
        	<a th:href="@{/user1/list}">user1 목록</a>
        	
        	<form th:action="@{/user1/register}" method="post">
        		<table border="1">
        			<tr>
        				<td>아이디</td>
        				<td><input type="text" name="uid"></td>
        			</tr>
        			<tr>
        				<td>이름</td>
        				<td><input type="text" name="name"></td>
        			</tr>
        			<tr>
        				<td>휴대폰</td>
        				<td><input type="text" name="hp"></td>
        			</tr>
        			<tr>
        				<td>나이</td>
        				<td><input type="number" name="age"></td>
        			</tr>
        			<tr>
        				<td colspan="2" align="right"><input type="submit" value="등록"></td>
        			</tr>
        		</table>
        	</form>	
        </body>
        </html>
        ```
        
    
    - modify.html
