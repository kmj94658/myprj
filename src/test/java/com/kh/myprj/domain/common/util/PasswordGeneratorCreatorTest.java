package com.kh.myprj.domain.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PasswordGeneratorCreatorTest {

	@Test
	@DisplayName("비밀번호 생성")
	void generatePassword() {
		log.info(PasswordGeneratorCreator.generator(7));
	}
}
