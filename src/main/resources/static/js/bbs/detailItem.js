/**
 * 
 */
 
	const $replyBtn = document.getElementById('replyBtn');
	const $modifyBtn = document.getElementById('modifyBtn');
	const $delBtn = document.getElementById('delBtn');
	const $listBtn = document.getElementById('listBtn');
	
	const handler = e => {
		//console.log(e);
		if(e.rtcd == '00') {
			location.href="/bbs/list"; //삭제가 제대로 됐으면 목록으로
		} else {
			alert('삭제오류');
			return false;
		}
	}
	
	//답글
	$replyBtn?.addEventListener("click", e=> {
		//th:data-bnum="*{bnum}" 읽어오기
		const bnum = e.target.dataset.bnum;
		location.href=`/bbs/reply/${bnum}`;
	});
	
	//수정
	$modifyBtn?.addEventListener("click", e=> {
		//th:data-bnum="*{bnum}" 읽어오기
		const bnum = e.target.dataset.bnum;
		location.href=`/bbs/${bnum}/edit`;
	});
	//삭제
	$delBtn?.addEventListener("click", e=> {
		//th:data-bnum="*{bnum}" 읽어오기
		const bnum = e.target.dataset.bnum;
		//요청메소드가 delete (폼태그 이용 or ajax)
		const url = `/bbs/${bnum}`;
		
		if(confirm('삭제하시겠습니까?')) {
			request.delete(url)
					 .then(res => res.json())
					 .then(res => handler(res))
					 .catch(err=>console.log(err));
		};
	//타겟 요소가 항상 있으면 상관이 없으나 없을때가 있으면 ?로 optional확인한다.	
		
	});
	//목록
	$listBtn.addEventListener("click", e=> {
		location.href="/bbs/list";
	});
