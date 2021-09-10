/**
 * 
 */
  const $delFiles = document.querySelectorAll('i.fas.fa-trash-alt');
	const $listBtn = document.getElementById('listBtn');
	
	const handler = (res,target) => {
		//console.log(e);
		if(res.rtcd == '00') {
			//삭제가 제대로 됐으면 돔요소 제거
			const $parent = target.closest('div');
			const $child = target.closest('p');
			$parent.removechild($child);
		} else {
			//alert('삭제오류');
			return false;
		}
	}
	
	//타겟 요소가 항상 있으면 상관이 없으나 없을때가 있으면 ?로 optional확인한다.	
	
	//파일 삭제
	if($delFiles) {
		Array.from($delFiles).forEach(ele => {
			ele.addEventListener("click", e=> {
				const sfname = e.target.dataset.sfname;
				const url = `/bbs/attach/${sfname}`;
				
				if(confirm('삭제하시겠습니까?')) {
					request.delete(url)
					 .then(res => res.json())
					 .then(res => handler(res))
					 .catch(err=>console.log(err));
				}	
			})
		}
	)}
	
	//목록
	$listBtn.addEventListener("click", e=> {
		const cate = e.target.dataset.cate;
		location.href=`/bbs/list?cate=${cate}`;
	});
