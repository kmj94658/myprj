package com.kh.myprj.web.form.bbs;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class WriteForm {

	@NotBlank
	private String bcategory;
	@NotBlank
	private String btitle;
	@NotNull
	private Long bid;
	@NotBlank
	private String bemail;
	@NotBlank
	private String bnickname;
	@NotBlank
	private String bcontent;
	
	private List<MultipartFile> files; //html파일 input태그 name속성이 files여야 한다.
}
