let regUid    = /^[a-z]+[a-z0-9]{4,19}$/g;
let regName   = /^[가-힣]{2,4}$/;
let regNick   = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$/;
let regEmail  = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
let regHp	  = /^\d{3}-\d{3,4}-\d{4}$/;
let regTel 	  = /^\d{2,3}-\d{3,4}-\d{3,4}$/;
let regCeoHP  = /^\d{3}-\d{3,4}-\d{4}$/;
let regPass   = /^.*(?=^.{5,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
let isUidOk   		= false;
let isHpOk   		= false;
let isNameOk  		= false;
let isEmailOk 		= false;
let isPassOk  	 	= false;
let isMedicalNameOk = false;
let isCeoOk 		= false; 
let isTelOk   		= false;
let isCeoHpOk   	= false;
let isReserveOk 	= false;

 $(document).ready(function(){
	 
	 // uid 유효성 검사
	 $('input[name=uid]').keydown(function(){
		 isUidOk = false;
	 });
	 
	 $('input[name=btnUidCheck]').click(function(){
		 let uid = $('input[name=uid]').val();
		 
		 if(isUidOk){
			 return;
		 }
		 
		 if(uid == ''){
			 isUidOk = false;
			 alert('아이디를 입력해주세요.');
		 }else{
			 
			 if(!uid.match(regUid)){
				 isUidOk = false;
				 alert('영어 또는 영어 숫자 섞어 4글자 이상으로 해주세요.');
				 
			 }else{
				 
				 let jsonData = {'uid' : uid};
				 
				 $.ajax({
					url : '/HelloPet/member/countUid',
					method : 'GET',
					data : jsonData,
					dataType : 'json',
					success : function(data){
						if(data.result == 0){
							isUidOk = true;
							alert('사용가능한 아이디입니다.');
						}else{
							isUidOk = false;
							alert('사용중인 아이디입니다.');
						}
					}
				 });
			 }
		 }
	 });
	 
	 
	// hp 유효성 검사	 
	 $('input[name=hp]').keydown(function(){
		 isHpOk =false;
	 });
	 
	 $('input[name=btnHpCheck]').click(function(){
		 
		 let hp = $('input[name=hp]').val();
		 
		 if(hp == ''){
			 isHpOk = false;
			 alert('휴대폰 번호를 입력해주세요.');
		 }else{
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
							alert('사용 가능한 연락처입니다.');
						}else{
							isHpOk = false;
							alert('사용중인 전화번호입니다.');
						}			 
					 }
				 });
			 }
		 }
	 });
	 
	 // name 유효성 검사
	 $('input[name=name]').focusout(function(){
		 
		 let name = $(this).val();
		 
		 if(name == ''){
			 isNameOk = false;
		 }else{
			 if(!name.match(regName)){
				 isNameOk = false;
				 alert('이름은 한글 2글자 이상으로 해주세요.');
			 }else{
				 isNameOk = true;
			 }
		 }
	 });
	 
	 
	 // email 유효성 검사
	 $('input[name=btnEmailCheck]').click(function(){
		 
		 let email = $('input[name=email]').val();
		 
		 //유효성 검사

		 if(email == ''){
			 isEmailOk = false;
			 alert('이메일 입력해주세요.');
			 
		 }else{
			 
			 if(!email.match(regEmail)){
				 isEmailOk = false;
				 alert('유효하지 않은 이메일입니다.');
				 
			 }else{
				 
				 let jsonData = {'email' : email};
				 
				 $.ajax({
					url : '/HelloPet/member/countEmail',
					method : 'GET',
					data : jsonData,
					dataType : 'json',
					success : function(data){
						if(data.result == 0){
							isEmailOk = false;
							//인증번호 받기
							alert('인증번호가 전송되었습니다.');
							$('.Email-auth').css('display','table-row','block');
							$('.auth-Code').focus();
							let email = $('input[name=email]').val();
							let jsonData = {'email' : email};
							let check = $('.auth-Code') //인증번호 입력받는곳
							
							$.ajax({
								url : '/HelloPet/member/registerAuth',
								method : 'GET',
								data : jsonData,
								success : function(data) {
									check.attr('disabled',false);
									code = data;
									isEmailOk = false;
								}
							});
							
						}else{
							isEmailOk = false;
							alert('사용중인 이메일입니다.');
						}				
					}
				 });
			 }
		 }
	 });
	 
		$('input[name=email-auth]').click(function(){
			let inputCode = $('.auth-Code').val();
			
			if(inputCode != ''){
				
				if(inputCode == code){
					alert('인증번호 일치');
					$('.Email-auth').css('display','none');
					isEmailOk = true;
					$('input[name=pass1]').focus();
				}else{
					alert('인증번호가 일치하지 않습니다.');
					isEmailOk = false;
				}
				
			}else{
				alert('인증번호를 입력해주세요.');
				isEmailOk = false;
			}
		});
		
		
		 $('input[name=pass2]').keyup(function(){
			 let pass1 = $('input[name=pass1]').val();
			 let pass2 = $('input[name=pass2]').val();
			 
			if(pass1 == '' && pass == ''){
				isPassOk = false;
			}else{
				
			 if(pass1 == pass2){
					//비밀번호 일치여부 확인 
					if(pass2.match(regPass)){
						//비밀번호 유효성 검사
						isPassOk = true;
						$('.passwordMsg').css('color','green').text('사용 가능한 패스워드입니다..');
					}else{
						isPassOk = false;
						$('.passwordMsg').css('color','red').text('비밀번호가 유효하지 않습니다.');
					}
				 }else{
					 isPassOk = false;
					 $('.passwordMsg').css('color','red').text('비밀번호가 일치하지 않습니다.');
				 }
			}
		 });
		 
		 
		$('input[name=medicalName]').keyup(function(){
			
			let medicalName = $('input[name=medicalName]').val();
			
			if(medicalName != ''){
				//입력확인
				isMedicalNameOk = true;
			}else{
				isMedicalNameOk = false;
			}
		});
		
		$('input[name=ceo]').keyup(function(){
			
			let ceo = $(this).val();
			
			if(ceo != ''){
				//입력확인
				isCeoOk = true;
			}else{
				isCeoOk = false;
			}
		});
		
		
		$('input[name=tel]').keyup(function(){
			
			let tel = $(this).val();
			
			if(tel != ''){
				//입력확인
				isTelOk = true;
			}else{
				isTelOk = false;
			}
		});
		
		$('input[name=ceoHp]').keyup(function(){
			
			let ceoHp = $(this).val();
			
			if(ceoHp != ''){
				//입력확인
				isCeoHpOk = true;
			}else{
				isCeoHpOk = false;
			}
		});
		
		$('.medicalRegister').click(function(){
			
			let uid = $('input[name=name]').val();
			let hp = $('input[name=hp]').val();
			let name = $('input[name=name]').val();
			let email = $('input[name=email]').val();
			let pass = $('input[name=pass2]').val();
			let medicalName = $('input[name=medicalName]').val();
			let ceo = $('input[name=ceo]').val();
			let tel = $('input[name=tel]').val();
			let ceoHp = $('input[name=ceoHp]').val();
			let reserve = $('input[name=reserve]').val();
			
			// 아이디 검증
			if(!isUidOk){
				alert('아이디를 확인 하십시요.');
				$('input[name=uid]').focus();
				return false;
			}
			
			if(!isHpOk){
				alert('전화번호를 확인 하십시요.');
				$('input[name=hp]').focus();
				return false;
			}
			
			if(!isNameOk){
				alert('이름 확인 하십시요.');
				$('input[name=name]').focus();
				return false;
			}
			
			// 아이디 검증
			if(!isEmailOk){
				alert('이메일을 확인 하십시요.');
				$('input[name=email]').focus();
				return false;
			}
			
			// 아이디 검증
			if(!isPassOk){
				alert('비밀번호를 확인 하십시요.');
				$('input[name=pass2]').focus();
				return false;
			}
			
			if(medicalName == ''){
				alert('병원이름 확인 하십시요.');
				$('input[name=medicalName]').focus();
				return false;
			}
			
			if(!isCeoOk){
				alert('대표수의사를 확인 하십시요.');
				$('input[name=ceo]').focus();
				return false;
			}
			
			if(tel == ''){
				alert('병원 연락처를 확인 하십시요.');
				$('input[name=tel]').focus();
				return false;
			}
			
			if(!isCeoHpOk){
				alert('대표 연락처를 확인 하십시요.');
				$('input[name=ceoHp]').focus();
				return false;
			}
			
			if(reserve == ''){
				alert('사업자 등록증을 올려주세요.');
				$('input[name=reserve]').focus();
				return false;
			}
			
			// 최종 전송
			return true;
		});
 });