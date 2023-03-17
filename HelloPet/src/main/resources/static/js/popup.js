$(function(){
    
    $('.reserve_div > table tr > td .btnReject').click(function(){
        
	    var revNo = parseInt($(this).closest("tr").find(".revNo").text());
	    console.log(revNo);
	     
	    var urlParams = new URL(location.href).searchParams;
	    var medNo = urlParams.get('medNo');
    		console.log(medNo);
    	var newUrl = "/HelloPet/admin/confirm/list?medNo="+medNo+"&revNo="+revNo;
    	window.history.pushState("", "", newUrl); // URL 변경
    	
    	$('#popReject').addClass('on');
    });

    $('.btnClose').click(function(){
		var urlParams = new URL(location.href).searchParams;
	    var medNo = urlParams.get('medNo');
		var backUrl = "/HelloPet/admin/confirm/list?medNo="+medNo;
		
        $(this).closest('.popup').removeClass('on'); 
        
        window.location.href = backUrl;               
    });
    
    $('.msg-div > .msg-list> li').click(function(e){
        e.preventDefault();
        $('#popMsg').addClass('on');
    });

});