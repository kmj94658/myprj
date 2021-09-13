package com.kh.myprj.domain.common.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindCriteria extends PageCriteria {

   private String searchType;
   private String keyword;
   
   public FindCriteria(RecordCriteria rc, int pageCount) {
      super(rc, pageCount);
   }
   
}