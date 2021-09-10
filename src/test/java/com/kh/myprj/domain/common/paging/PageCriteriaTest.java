package com.kh.myprj.domain.common.paging;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageCriteriaTest {

	@Test
	void calPage() {
		
		RecordCriteria rc = new RecordCriteria(10);
		rc.setReqPage(5); //요청페이지
				
		PageCriteria pc = new PageCriteria(rc, 10);
		pc.setTotalRec(324); //총레코드수
		
		log.info("시작레코드:{}, 종료레코드:{}", pc.getRc().getStartRec(), pc.getRc().getEndRec());
		log.info("시작페이지:{}, 종료레코드:{}", pc.getStartPage(), pc.getEndPage());
		log.info("최종페이지:{}", pc.getFinalEndPage());
	}
}
