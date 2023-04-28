$(function(){
	

    $('.btnClose').click(function(){
		var urlParams = new URL(location.href).searchParams;
	    var uid = urlParams.get('uid');
		var backUrl = "/HelloPet/message/message";
		
        $(this).closest('.popup').removeClass('on'); 
        
        window.location.href = backUrl;               
    });
    
    $('.msg-div > .msg-list> li').click(function(e){
        e.preventDefault();
        
        var msgNo = $(this).find(".msgNo").text();
	    console.log(msgNo);
        
        var urlParams = new URL(location.href).searchParams;
	    var uid = urlParams.get('uid');
    		console.log(uid);
    	
    	var newUrl = "/HelloPet/message/view?msgNo="+msgNo;
    	window.history.pushState("", "", newUrl); // URL 변경
        
        $('#popMsg').addClass('on');
        
        $.ajax({
			url : "/HelloPet/message/view",
	    	method : 'get',
	    	data : {'msgNo': msgNo},
			success: function(data){	
				$('#popMsg .msgDiv > p').html(data.result.content);
			}	
    	});
    });

});