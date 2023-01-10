package kr.co.bookStore.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import kr.co.bookStore.vo.BookVO;

public class BookValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return BookVO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		System.out.println("BookValidator.validate() is called");
		
		ValidationUtils.rejectIfEmpty(errors, "bookname", "required");
	}

}
