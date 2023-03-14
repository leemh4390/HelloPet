$(document).ready(function(){
	    $('button[name=btnTap]:eq(1)').click(function(){
			$('input[name=medicalName]').val('');
			$('input[name=tel]').val('');
			$('input[name=zip]').val('');
			$('input[name=addr1]').val('');
			$('input[name=addr2]').val('');
			$('#pharmacy-trial').val(0);
			$('#pharmacy-county').empty();
			$('#pharmacy-county').append("<option value='0'>시·군·구</option>");
	        $('.change').remove();
	        $(this).addClass('on');
	        $('button[name=btnTap]:eq(0)').removeClass('on');
	        $('.change').remove();
	        let tags = "<tr class='change'>";
	            tags += "<td>약국명</td>";
	            tags += "<td>";
	            tags += "<input type='text' name='medicalName' required='required' placeholder='정보 입력하기 전에 미리 찾아보세요.' style='margin-right:9px'>";
	            tags += "<input type='button' name='searchPhar' id='btn-modal-pharmacy' value='약국찾기'>";
	            tags += "</td>";
	            tags += "</tr>";
	            tags += "<tr class='change'>";
	            tags += "<td>대표 약사</td>";
	            tags += "<td>";
	            tags += "<input type='text' name='ceo' placeholder='대표약사 입력해주세요'>";
	            tags += "</td>";
	            tags += "</tr>";
	        $('#pass').after(tags);
	    });
	
	    $('button[name=btnTap]:eq(0)').click(function(){
			$('input[name=medicalName]').val('');
			$('input[name=tel]').val('');
			$('input[name=zip]').val('');
			$('input[name=addr1]').val('');
			$('input[name=addr2]').val('');
			$('.trial').val(0);
			$('.county').empty();
			$('.county').append("<option value='0'>시·군·구</option>");
	        $('.change').remove();
	        $(this).addClass('on');
	        $('button[name=btnTap]:eq(1)').removeClass('on');
	        $('#change').remove();
	        let tags = "<tr class='change'>";
	            tags += "<td>병원명</td>";
	            tags += "<td>";
	            tags += "<input type='text' name='medicalName' required='required' placeholder='정보 입력하기 전에 미리 찾아보세요.' style='margin-right:9px'>";
	            tags += "<input type='button' name='searchHos' id='btn-modal-medical' value='병원찾기'>";
	            tags += "</td>";
	            tags += "</tr>";
	            tags += "<tr class='change'>";
	            tags += "<td>대표 수의사</td>";
	            tags += "<td>";
	            tags += "<input type='text' name='ceo' placeholder='대표수의사 입력해주세요'>";
	            tags += "</td>";
	            tags += "</tr>";
	        $('#pass').after(tags);
	    });
	});

	$(document).ready(function(){
	
	    // 기본 modal (병원찾기)
	    $('#btn-modal').click(function(){
	        $('#modal-medical').css('display','flex');
	        document.body.style.overflow = "hidden";
	    });
	
	    $('.close-area').click(function(){
	        $('#modal-medical').css('display','none');
	        document.body.style.overflow = "unset";
	    });
	
	    // 병원찾기
	    $(document).on('click','#btn-modal-medical',function(){
	        $('#modal-medical').css('display','flex');
	        document.body.style.overflow = "hidden";        
	    });
	
	    // 약국찾기
	    $(document).on('click','#btn-modal-pharmacy',function(){        
	        $('#modal-pharmacy').css('display','flex');
	        document.body.style.overflow = "hidden";
	    });
	
	    // 약국닫기
	    $('.close-area').click(function(){
	        $('#modal-pharmacy').css('display','none');
	        document.body.style.overflow = "unset";
	    });
	});