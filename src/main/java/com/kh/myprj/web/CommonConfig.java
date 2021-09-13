package com.kh.myprj.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kh.myprj.domain.common.dao.CodeDAO;
import com.kh.myprj.web.form.Code;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CommonConfig {

	private final CodeDAO codeDAO;
	
	@Bean
	public List<Code> cate() {
		List<Code> list = codeDAO.getCode("A05");
		return list;
	}
}
