package com.kh.myprj.web.form.member;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ChangePwForm {

	@NotBlank
	private String prePw;
	@NotBlank
	private String postPw;
	@NotBlank
	private String postPwChk;
}
