$(function(){
	
	$('.reserve_div > table tr > td .btnConfirm').click(function(){
        
	    var revNo = $(this).closest("tr").find(".revNo").text();
	    console.log(revNo);
	     
	    var urlParams = new URL(location.href).searchParams;
	    var medNo = urlParams.get('medNo');
    		console.log(medNo);
    	
    	var newUrl = "/HelloPet/admin/confirm/list?medNo="+medNo+"&revNo="+revNo;
    	window.history.pushState("", "", newUrl); // URL 변경
    	
    	$('#popConfirm').addClass('on');
    	
    	$.ajax({
			url : "/HelloPet/admin/confirm/ok",
	    	method : 'get',
	    	data : {'revNo': revNo},
	    	success: function(data){
				var revNum = data.result.revNo;
				var uid = data.result.uid;
				var medical = data.result.medicalName;
				var medNo = data.result.medNo;
				var coupon = data.result.coupon;
				
				if(data.result.petNum == 1){
					var html = '<td>';
					html += revNum + '</td>';
					html += '<td>' + data.result.oname + '</td>';
					html += '<td>' + data.result.oph + '</td>';
					html += '<td>' + data.result.petName1+'/'+data.result.petType1+'/'+data.result.petAge1 + '</td>';
					html += '<td>' + data.result.department + '</td>';
					html += '<td>' + data.result.memo + '</td>';
					html += '<td>' + data.result.revDate + '</td>';
					html += '<td>' + data.result.revTime.substring(0,5) + '</td>';
					html += '<td style="display:none;">' + uid + '</td>';
					html += '<td style="display:none;">' + medical + '</td>';
					html += '<td style="display:none;">' + medNo + '</td>';
					html += '<td style="display:none;">' + coupon + '</td>';
				
					$('.confirmResult').html(html);
				
				}else{
					var html = '<td>';
					html += revNum + '</td>';
					html += '<td>' + data.result.oname + '</td>';
					html += '<td>' + data.result.oph + '</td>';
					html += '<td>' + data.result.petName1+'/'+data.result.petType1+'/'+data.result.petAge1+', '+data.result.petName2+'/'+data.result.petType2+'/'+data.result.petAge2 + '</td>';
					html += '<td>' + data.result.department + '</td>';
					html += '<td>' + data.result.memo + '</td>';
					html += '<td>' + data.result.revDate + '</td>';
					html += '<td>' + data.result.revTime.substring(0,5) + '</td>';
					html += '<td style="display:none;">' + uid + '</td>';
					html += '<td style="display:none;">' + medical + '</td>';
					html += '<td style="display:none;">' + medNo + '</td>';
					html += '<td style="display:none;">' + coupon + '</td>';
				
					$('.confirmResult').html(html);
				}
			}
		});
    });
    
   
    
    $('.reserve_div > table tr > td .btnReject').click(function(){
        
	    var revNo = $(this).closest("tr").find(".revNo").text();
	    console.log(revNo);
	     
	    var urlParams = new URL(location.href).searchParams;
	    var medNo = urlParams.get('medNo');
    		console.log(medNo);
    	
    	var newUrl = "/HelloPet/admin/confirm/view?medNo="+medNo+"&revNo="+revNo;
    	window.history.pushState("", "", newUrl); // URL 변경
    	
    	$('#popReject').addClass('on');
    	
    	$.ajax({
			url : "/HelloPet/admin/confirm/view",
	    	method : 'get',
	    	data : {'revNo': revNo, 'medNo':medNo},
	    	success: function(data){
				var revNum = data.result.revNo;
				var uid = data.result.uid;
				var medical = data.result.medicalName;
				var medNo = data.result.medNo;
				
				if(data.result.petNum == 1){
					var html = '<td>';
					html += revNum + '</td>';
					html += '<td>' + data.result.oname + '</td>';
					html += '<td>' + data.result.oph + '</td>';
					html += '<td>' + data.result.petName1+'/'+data.result.petType1+'/'+data.result.petAge1 + '</td>';
					html += '<td>' + data.result.department + '</td>';
					html += '<td>' + data.result.memo + '</td>';
					html += '<td>' + data.result.revDate + '</td>';
					html += '<td>' + data.result.revTime.substring(0,5) + '</td>';
					html += '<td style="display:none;">' + uid + '</td>';
					html += '<td style="display:none;">' + medical + '</td>';
					html += '<td style="display:none;">' + medNo + '</td>';
				
					$('.rejectResult').html(html);
				
				}else{
					var html = '<td>';
					html += revNum + '</td>';
					html += '<td>' + data.result.oname + '</td>';
					html += '<td>' + data.result.oph + '</td>';
					html += '<td>' + data.result.petName1+'/'+data.result.petType1+'/'+data.result.petAge1+', '+data.result.petName2+'/'+data.result.petType2+'/'+data.result.petAge2 + '</td>';
					html += '<td>' + data.result.department + '</td>';
					html += '<td>' + data.result.memo + '</td>';
					html += '<td>' + data.result.revDate + '</td>';
					html += '<td>' + data.result.revTime.substring(0,5) + '</td>';
					html += '<td style="display:none;">' + uid + '</td>';
					html += '<td style="display:none;">' + medical + '</td>';
					html += '<td style="display:none;">' + medNo + '</td>';
				
					$('.rejectResult').html(html);
				}
				
			}
		});
    	
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