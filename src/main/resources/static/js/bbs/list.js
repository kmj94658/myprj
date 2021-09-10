/**
 * 
 */
 
 'use strict';

	const $writeBtn = document.getElementById('writeBtn');
	$writeBtn.addEventListener("click", e=>{
		const cate = e.target.dataset.cate;
		location.href=`/bbs?cate=${cate}`; //이렇게 쓰면 get방식으로 요청
	})