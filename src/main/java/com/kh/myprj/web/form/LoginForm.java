package com.kh.myprj.web.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginForm {
	
	@NotBlank//(message = "필수입력항목입니다")
	@Email
	private String email;
	
	@NotBlank//(message = "필수입력항목입니다")
	@Size(min=4, max=12)
	private String pw;
}
