package kr.co.sboard.vaildator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import kr.co.sboard.service.UserService;
import kr.co.sboard.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserVaildator implements Validator {
	
	private Errors errors;
	private UserVO user;
	
	private UserService userService;
	private Map<String, String> regexMap;
	
	public UserVaildator(UserService userService) {
		this.userService = userService;
		setRegexMap();
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserVO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		log.info("UserVaildator.validate() is called...");
		this.user = (UserVO)target;
		this.errors = errors;
		
		try {
		
			// 유효성 체크
			checkField(user);
		
			// 중복 체크
			List<UserVO> users = userService.checkDuplicate(user); // 아이디, 닉네임, 이메일, 전화번호가 중복되는 회원이 있는지 확인
			if(users.size() == 0) return;
			
			checkDuplicate(user, users.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// UserVo의 필드값을 돌며 유효성 검사
	private void checkField(UserVO user) throws Exception {
		
		Field[] fields = user.getClass().getDeclaredFields();
		int count = 1;
		
		for(Field field : fields) {
			field.setAccessible(true);
			String fieldName = field.getName();	// 필드명
			Object fieldVal = field.get(user);	// 필드 값
			
			if(fieldVal == null || String.valueOf(fieldVal).isBlank()) {
				errors.rejectValue(fieldName, "required");
			}
			
			else {
				boolean regex = Pattern.matches(regexMap.get(fieldName), String.valueOf(fieldVal));
				if(!regex) errors.rejectValue(fieldName, "required.user." + fieldName);
			} 
			
			count++;
			if(count > regexMap.size()) return;
		} 
	}
	
	// UserVo의 필드값을 돌며 중복 검사
	private void checkDuplicate(UserVO user, String usersStr) throws Exception {
		
		Field[] fields = user.getClass().getDeclaredFields();
		
		for(Field field : fields) {
			
			field.setAccessible(true);
			String fieldName = field.getName();	// 필드명
			Object fieldVal = field.get(user);	// 필드 값
			
			if(fieldName.equals("name") || fieldName.equals("pass")) continue;
			
			if(fieldVal == null || String.valueOf(fieldVal).isBlank()) continue;
			
			if(usersStr.contains(fieldName + "=" + fieldVal)) {
				errors.rejectValue(fieldName, "required.user.duplicate." + fieldName);
			}
			
			if(fieldName.equals("hp")) return;
		} 
	}
	
	// 정규식 표현 
	private void setRegexMap() {
		regexMap = new HashMap<>();
		regexMap.put("uid", "^[a-z0-9_-]{5,20}$");
		regexMap.put("pass", "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$");
		regexMap.put("name", "^[가-힣]+$");
		regexMap.put("nick", "^[a-zA-Z가-힣0-9][a-zA-Zㄱ-힣0-9]*$");
		regexMap.put("email", "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$");
		regexMap.put("hp", "^01(?:0|1|[6-9])-(?:\\d{4})-\\d{4}$");
	}

}
