
let regPass  = /^.*(?=^.{5,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
$(function(){
	
	
	
	
	    $('.btnClose').click(function(){
			var backUrl = "/HelloPet/my/info";
			
	        $(this).closest('.popup').removeClass('on'); 
	        
	        window.location.href = backUrl;               
	    });
	    
	    $('button[name=pwChange]').click(function(e){
	        e.preventDefault();
	        
	         $('#popPw').addClass('on');
	         
	         var newUrl = "/HelloPet/my/pwChange";
    		window.history.pushState("", "", newUrl); // URL 변경
	         
	    });
	    
	    $('.pwForm').submit(function(e){
			e.preventDefault();
			
			let uid = $('.pwuid').text();
			let pass1 = $('input[name=pass1]').val();
			let pass2 = $('input[name=pass2]').val();
			
			console.log("hr1"+pass1);
			console.log("hr2"+pass2);
			
			if(pass1 != pass2){
				alert('비밀번호가 일치하지 않습니다.\n다시 입력해주세요.');
				return;
			}
			console.log("hr3");
			
			if(!pass2.match(regPass)){
				alert('영문, 숫자, 특수문자 조합하여 5자 이상이어야 합니다.');
				return;
			}
			
			let jsonData = {
					"uid": uid,
					"pass": pass2
			};
			console.log("hr4");
			
			$.ajax({
				url: '/HelloPet/my/pwChange',
				type: 'post',
				data: jsonData,
				dataType: 'json',
				success: function(data){
					if (data.result > 0) {
				      alert("비밀번호가 변경되었습니다.\n다시 로그인해주십시오.");
				      location.href = '/HelloPet/member/logout';
				      
				    } else {
				      alert("비밀번호 변경에 실패했습니다.");
				      var backUrl = "/HelloPet/my/info";
		        
			          window.location.href = backUrl;
		              $('#popPw').removeClass('on');
		              return false;
				    }
				}
			});
			
	});
});