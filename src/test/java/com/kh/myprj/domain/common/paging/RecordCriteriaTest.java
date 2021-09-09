package com.kh.myprj.domain.common.paging;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecordCriteriaTest {

	@Test
	void calRec10() {
		RecordCriteria rc = new RecordCriteria(10);
		rc.setReqPage(11);
		
		Assertions.assertThat(101).isEqualTo(rc.getStartRec());
		Assertions.assertThat(110).isEqualTo(rc.getEndRec());
	}
	
	@Test
	void calRec15() {
		RecordCriteria rc = new RecordCriteria(15);
		rc.setReqPage(11);
		
		Assertions.assertThat(151).isEqualTo(rc.getStartRec());
		Assertions.assertThat(165).isEqualTo(rc.getEndRec());
	}
}
