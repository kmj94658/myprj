package com.kh.myprj.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kh.myprj.domain.common.paging.PageCriteria;
import com.kh.myprj.domain.common.paging.RecordCriteria;

@Configuration
public class PagingConfig {

	static final int REC10 = 10; //한페이지에 보여줄 레코드수
	static final int PAGE10 = 10; //한페이지에 보여줄 페이지수
	
	static final int REC5 = 5; //한페이지에 보여줄 레코드수
	static final int PAGE5 = 5; //한페이지에 보여줄 페이지수
	
	//Bean: 스프링 프레임워크에서 객체의 생명주기를 관리하는 객체. @configuration으로 수동으로 적거나
	@Bean(name = "rec10")
	public RecordCriteria rc10() {
		return new RecordCriteria(REC10);
	}
	
	@Bean(name = "pc10")
	public PageCriteria pc10() {
		return new PageCriteria(rc10(), PAGE10);
	}
	@Bean(name = "rec5")
	public RecordCriteria rc5() {
		return new RecordCriteria(REC5);
	}
	@Bean(name = "pc5")
	public PageCriteria pc5() {
		return new PageCriteria(rc5(), PAGE5);
	}	
}
