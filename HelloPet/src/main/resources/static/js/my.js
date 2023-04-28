$(document).ready(function(){
	
	//nav css - header
	
		let url = window.location.href;
		console.log('url : ' + url);
		
		let i = url.lastIndexOf('/');
		console.log('i : ' + i);
		
		let currentUrl = url.substring(i+1);
			
		if(currentUrl == 'info'){
			$('.mypage-nav_list:nth-child(1) > a').addClass("active");
		}else if(currentUrl == 'myReserve'){
			$('.mypage-nav_list:nth-child(2) > a').addClass("active");
		}else if (currentUrl.indexOf('myReserve') > -1){
		    $('.mypage-nav_list:nth-child(2) > a').addClass("active");
		}else if(currentUrl == 'myArticle'){
			$('.mypage-nav_list:nth-child(3) > a').addClass("active");
		}else if(currentUrl == 'myQna'){
			$('.mypage-nav_list:nth-child(4) > a').addClass("active");
		}else if(currentUrl == 'coupon'){
			$('.mypage-nav_list:nth-child(5) > a').addClass("active");
		}else if (currentUrl.indexOf('myArticle') > -1){
		    $('.mypage-nav_list:nth-child(3) > a').addClass("active");
		}else if(currentUrl.indexOf('myQna') > -1){
			$('.mypage-nav_list:nth-child(4) > a').addClass("active");
		}else if(currentUrl.indexOf('coupon') > -1){
			$('.mypage-nav_list:nth-child(5) > a').addClass("active");
		}
	
	
	
	
	
let regName   = /^[가-힣]{2,4}$/;
let regNick   = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$/;
let regEmail  = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
let regHp	  = /^\d{3}-\d{3,4}-\d{4}$/;
let isNameOk  		= true;
let isHpOk   		= true;
let isEmailOk 		= true;
let isNickOk 		= true;	
	
	
		$('input[name=name]').focusout(function(){
			
			let orgName = $('input[name=ori-Name]').val();
			let name 	= $('input[name=name]').val();
			
			// 기존 이름과 일치 여부 확인하기
			if(orgName != name){
				// 유효성 검사하기
				if(!name.match(regName)){
					isNameOk = false;
					alert('이름은 한글 2글자 이상으로 해주세요.');
				}else{
					isNameOk = true;
				}
			}else{
				isNameOk = true;
			}
		});
		
		
		$('input[name=email]').focusout(function(){
				
			let email = $(this).val();
			let orgEmail = $('input[name=ori-Email]').val();
			
			// 기존 이메일과 일치 여부 확인하기
			if(email != orgEmail){
				
				// 유효성 검사
				if(!email.match(regEmail)){
					 isEmailOk = false;
					 alert('유효하지 않은 이메일입니다.');
				}else{
					// 중복조회하기
					
					let jsonData = {'email' : email};
					
					 $.ajax({
							url : '/HelloPet/member/countEmail',
							method : 'GET',
							data : jsonData,
							dataType : 'json',
							success : function(data){
								if(data.result == 0){
									isEmailOk = true;
									// 이메일 인증까지 해야할까?
								}else{
									// 중복
									isEmailOk = false;
									alert('사용중인 이메일입니다.');
								}
							}
					 });
				}
			}else{
				isEmailOk = true;
			}
		});
		
		
		$('input[name=nick]').focusout(function(){
			
			let nick = $(this).val();
			let oriNick = $('input[name=ori-Nick]').val();
			
			// 기존 데이터와 일치여부 확인하기
			if(nick != oriNick){
				
				// 유효성 검사하기
				if(!nick.match(regNick)){
					isNickOk = false;
					alert('유효하지 않은 닉네임입니다.');
				}else{
					isNickOk = false;
					
					 let jsonData = {'nick' : nick};
					 
					 $.ajax({
						url : '/HelloPet/member/countNick',
						method : 'GET',
						data : jsonData,
						dataType : 'json',
						success : function(data){
							if(data.result == 0){
								//중복 없음
								isNickOk = true;
							}else{
								isNickOk = false;
								alert('사용중인 닉네임입니다.');
							}
						}					
					 });
				}
			}else{
				isNickOk = true;
			}
		});
		
		
		$('input[name=hp]').focusout(function(){
			
			let hp = $(this).val();
			let oriHp = $('input[name=ori-Hp]').val();
			
			if(hp != oriHp){
				
				 if(!hp.match(regHp)){
					 isHpOk = false;
					 alert('- 포함 13자리 입력해주세요.');
				 }else{
					let jsonData = {'hp' : hp};
					
					 $.ajax({
						 url : '/HelloPet/member/countHp',
						 method : 'GET',
						 data : jsonData,
						 dataType : 'json',
						 success : function(data){
							if(data.result == 0){
								isHpOk = true;
							}else{
								isHpOk = false;
								alert('사용중인 전화번호입니다.');
							}			 
						 }
					 });
				 }
				
			}else{
				isHpOk = true;
			}
		});
		
		// 회원 정보 update 기능 구현 완료
		const form = $('input[name=infoModify]');
		
		form.onsubmit = function(){
			
			let name 	 = $('input[name=name]').val();
			let orgName  = $('input[name=ori-Name]').val();
			let email 	 = $('input[name=email]').val();
			let orgEmail = $('input[name=ori-Email]').val();
			let nick 	 = $('input[name=nick]').val();
			let oriNick  = $('input[name=ori-Nick]').val();
			let hp 		 = $('input[name=hp]').val();	
			let oriHp 	 = $('input[name=ori-Hp]').val();
			
			// 일치여부
			if(name == orgName && email == orgEmail &&  nick == oriNick && hp == oriHp){
				alert('기존 회원 정보와 동일합니다.');
			}
			
			if(!isNameOk){
				alert('이름 확인 하십시요.');
				$('input[name=name]').focus();
				return false;
			}
			
			// 이메일 검증
			if(!isEmailOk){
				alert('이메일을 확인 하십시요.');
				$('input[name=email]').focus();
				return false;
			}
			
			// 닉네임 검증
			if(!isNickOk){
				alert('닉네임을 확인해주세요.');
				$('input[name=nick]').focus();
				return false;
			}
			
			// 연락처 검증
			if(!isHpOk){
				alert('전화번호를 확인 하십시요.');
				$('input[name=hp]').focus();
				return false;
			}
			
			// 최종 전송
			return true;
		}
		
		// 회원 삭제 - 기능구현 완료
		$('button[name=btnWithdrawMember]').click(function(e){
			e.preventDefault();

			let uid = $('input[name=uid]').val();

			let jsonData = {'uid' : uid};

			let aws = confirm('탈퇴하시겠습니까?');

			if(aws){

				$.ajax({
					url : '/HelloPet/my/withdrawMember',
					method : 'GET',
					data : jsonData,
					dataType : 'json',
					success : function(data){
						console.log(data);
						if(data == 1){
								$.ajax({
		                        url: '/HelloPet/member/logout',
		                        method: 'GET',
		                        success: function(){
		                            alert('탈퇴 완료 되었습니다.');
		                            location.href = '/HelloPet/';
		                        }
	                    	});
						}else{
							// 실패
							alert('실패하였습니다. 관리자에게 문의해주세요.');
						}
					}
				});
			}
		});
	
		$('.reserve_cancel').click(function(){
			
			// 배열 생성
			let arr_rev   = [];			
			
			// 데이터를 배열에 넣기
			$('.rev_No:checked').each(function(){
				arr_rev.push($(this).val());
			});
			
			// 크기 확인
			let length = arr_rev.length;
			let size = $('input[type=checkbox]').length;
			let pg = $('input[name=currentPage]').val();
			let lastPageNum = $('input[name=lastPageNum]').val();
			
			if(length > 0){
				
				let jsonData = {'rev_No' : arr_rev};
				
				alert('size : ' + size + ' length :' + length)
				// ajax 처리..
				$.ajax({
					url : '/HelloPet/my/myReserveCancel',
					method : 'GET' ,
					data : jsonData ,
					dataType : 'json' ,
					traditional : true ,
					success : function(data) {
						if(data.result > 0){
							alert(data.result + " 건이 삭제되었습니다.");
							//마지막 페이지인지?
							if(pg == lastPageNum){
								
								// 삭제하는 게시글 갯수와 전체 게시물 갯수가 같은지?
								if(size == length){
									pg -= 1
									location.href='/HelloPet/my/myReserve?pg='+pg;
								}else{
									location.href='/HelloPet/my/myReserve?pg='+pg;
								}
								
							//아니라면
							}else{
								location.href='/HelloPet/my/myReserve?pg='+pg;
							}
						}
					}
				});
				
			}else{
				alert('취소하고 싶은 게시글을 체크해주세요.');
			}
		});
});