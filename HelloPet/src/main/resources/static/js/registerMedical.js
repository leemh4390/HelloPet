$(document).ready(function(){
	
	//지역 카테고리 2023/03/10
	let seoul = ['종로구', '중구', '용산구', '성동구', '광진구', '동대문구', ' 중랑구', '성북구', '강북구', '도봉구', '노원구', '은평구', '서대문구', '마포구', '양천구', '강서구', '구로구', '금천구', '영등포구', '동작구', '관악구', '서초구', '강남구', '송파구', '강동구'];
    let busan = ['중구', '서구', '동구', '영도구', '부산진구', '동래구', '남구', '북구', '강서구', '해운대구', '사하구', '금정구', '연제구', '수영구'];
    let daegu = ['중구', '동구', '서구', '남구', '북구', '수성구', '달서구', '달성구'];
    let incheon = ['중구', '동구', '미추홀구', '연수구', '남동구', '부평구', '계양구', '서구', '강화군', '옹진군'];
    let gwangju = ['동구', '서구', '남구', '북구', '광산구', '고흥군'];
    let daejun = ['동구', '중구', '서구', '유성구', '대덕구'];
    let ulsan = ['중구', '남구', '동구', '북구'];
    let sejong = [];
    let gyeongi = ['수원시 장안구', '수원시 권선구', '수원시 팔달구', '수원시 영통구', '성남시 수정구', '성남시 중원구', '성남시 분당구', '안양시 동안구', '안양시 만안구', '안산시 상록구', '안산시 단원구',
    	'용인시 처인구', '용인시 기흥구', '용인시 수지구', '부천시', '광명시', '평탱시', '과천시', '오산시', '시흥시', '군포시', '의왕시', '하남시', '이천시', '안성시', '김포시', '화성시', '광주시', '여주시', '양평군', '고양시 덕양구', '고양시 일산동구', '고양시 일산서구', '의정부시', '동두천시',
    	'구리시', '남양주시', '파주시', '양주시', '포천시', '연천군', '가평군'];
    let gangwon = ['춘천시', '원주시', '강릉시', '동해시', '태백시', '속초시', '삼척시', '홍천군', '횡성군', '영월군', '평창군', '정선군', '철원군', '화천군', '양구군', '인제군', '고성군', '양양군'];
    let chungcheongNorth = ['청주시 상당구', '청주시 서원구', '청주시 청원구', '청주시 흥덕구','충주시', '제천시', '보은군', '옥천군', '영동군', '증평군', '진천군', '괴산군', '음성군', '단양군'];
    let chungcheongSounth = ['천안시 동남구', '천안시 서북구','계룡시', '공주시', '논산시', '보령시', '서산시', '아산시', '금산군', '당진시', '부여군', '서천군', '연기군', '예산군', '청양군', '태안군', '홍성군'];
    let jeonraNorth = ['전주시 완산구', '전주시 덕진구', '군산시', '익산시', '정읍시', '남원시', '김제시', '완주군', '진안군', '무주군', '장수군', '임실군', '순창군', '고창군', '부안군'];
    let jeonraSouth = ['목포시', '여수시', '순천시', '나주시', '광양시', '담양군', '곡성군', '구례군', '고흥군', '보성군', '화순군', '장흥군', '강진군', '해남군', '영암군', '무안군', '함평군', '영광군', '장성군', '완도군', '진도군', '신안군'];
    let gyeongsangNorth = ['포항시 남구','포항시 북구','경산시', '경주시', '고령군', '구미시', '군위군', '김천시', '문경시', '봉화군', '상주시', '성주군', '안동시', '영덕군', '영양군', '영주시', '영천시', '예천군', '울릉군', '울진군', '의성군', '청도군', '청송군', '칠곡군'];
    let gyeongsangSounth = ['창원시 의창구', '창원시 성산구', '창원시 진해구', '창원시 마산합포구', '진주시', '통영시', '사천시', '김해시', '밀양시', '거제시', '양산시', '의령군', '함양군', '창녕군', '고성군', '남해군', '하동군', '산청군', '함안군', '울릉군' ];
    let jeju = ['제주시', '서귀포시'];
    
    $('.trial').change(function(){
    	let trial = $(this).val();
    	
    	if(trial == '서울'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of seoul){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	
    	if(trial == '부산'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of busan){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	
    	if(trial == '대구'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of daegu){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	
    	if(trial == '인천'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of incheon){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	
    	if(trial == '광주'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of gwangju){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	
    	if(trial == '대전'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of daejun){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	
    	if(trial == '울산'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of ulsan){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	
    	if(trial == '경기 '){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of gyeongi){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	
    	if(trial == '강원'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of gangwon){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	
    	if(trial == '충청북도'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of chungcheongNorth){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	
    	if(trial == '충청남도'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of chungcheongSounth){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	
    	if(trial == '전라북도'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of jeonraNorth){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	
    	if(trial == '전라남도'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of jeonraSouth){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	if(trial == '경상북도'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of gyeongsangNorth ){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	
    	if(trial == '경상남도'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of gyeongsangSounth ){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}
    	
    	if(trial == '제주'){
    		$('.county').empty();
    		$('.county').append("<option value='0'>시·군·구</option>");
    		for(let values of jeju ){
    			let tags = "<option>"+values+"</option>";
    			$('.county').append(tags);
    		}
    	}	    	
    	    	
    });
    
    
    //병원 검색시 result button 클릭시 실행되는 이벤트... 2023/03/10
    $('button[name=btnSearch-Hospital]').click(function(){
    	let trial = $('.trial').val();
	    let county = $('.county').val();
	    let name = $('.SearchName').val();
	    
	    if(trial == 0){
	    	alert('지역을 선택');
	    }else if(name == ''){
	    	alert('이름을 입력하세요.');
	    }else{
	    //alert('trial : ' + trial + ' county : ' + county);
	    
	    let jsonData = {
	    		'trial'  : trial , 
	    		'county' : county,
	    		'name'   : name 
	    };
	    
	    $.ajax({
	    	url : '/HelloPet/member/SearchHospital',
	    	method : 'GET',
	    	data : jsonData,
	    	dataType : 'json',
	    	success : function(data){
	    		$('.medical-result').empty();
	    		
	    		let size = data.result.length;
	    		
	    		if(size == 0){
	    			alert('조회 결과가 없습니다.');		 
	    		}
	    		
	    		for(let i = 0; i < data.result.length; i++){
	    			let tags  = "<table class='medical-result-table'>";
		    			tags += "<input type='hidden' class='input-zip' value='"+data.result[i].zip+"'>";
		    			tags += "<input type='hidden' class='input-tel' value='"+data.result[i].tel+"'>";
		    			tags += "<tr>";
	    				tags += "<th>번호</th>"
	    				tags += "<th>병원이름</th>"
	    				tags += "<th>병원주소</th>"
	    				tags += "<th></th>"
	    				tags += "</tr>";
	    				tags += "<tr>";
	    				tags += "<td>"+(i+1)+"</td>"
	    				tags += "<td><input type='hidden' class='input-hosName' value='"+data.result[i].hosName+"'>"+data.result[i].hosName+"</td>"
	    				tags += "<td><input type='hidden' class='input-hosAddr' value='"+data.result[i].hosAddr+"'>"+data.result[i].hosAddr+"</td>"
	    				tags += "<td><button class='btnMedicalSelect'>선택하기</button></td>"
	    				tags += "</tr>";
	    				tags += "</table>";
	    			$('.medical-result').append(tags);
	    		}
	    	}
	    });
	   }
    });
	
    //버튼 클릭시 table 내용 비우기
    $('.medical-result').on('click','.btnMedicalSelect',function(){
		let zip     = $(this).closest('table').find('.input-zip').val();	    	
		let tel     = $(this).closest('table').find('.input-tel').val();	    	
    	let hosName = $(this).closest('tr').find('.input-hosName').val();
    	let hosAddr = $(this).closest('tr').find('.input-hosAddr').val();
    	
    	$('input[name=zip]').val(zip);
    	$('input[name=medicalName]').val(hosName);
    	$('input[name=tel]').val(tel);
    	$('#addr1').val(hosAddr);
    	$('#modal-medical').css('display','none');
		$('.trial').val(0);
		$('.county').empty();
		$('.county').append("<option value='0'>시·군·구</option>");
		$('.SearchName').val('');
		$('.medical-result-table tr').empty();
		document.body.style.overflow = "unset";
		$('input[name=addr2]').focus();
    });
    
    //버튼태그 클릭시 파일찾기 실행
    $('.btnFile').click(function(){
    	$('input[name=reserve]').click();	
    });
	
	
	
	
	    $('button[name=btnSearch-Pharmacy]').click(function(){
	    //$('select[class=trial]').change(function(){
	    	let trial  = $('#pharmacy-trial').val();
	    	let county = $('#pharmacy-county').val();
	    	let name  = $('#pharmacy-name').val();
	    	
		    /*alert('trial : ' + trial + ' county : ' + county + 'name : ' + name);*/
	    	
	    	let jsonData = {
	    					'trial'  : trial, 
			    			'county' : county, 
			    			'name'   : name
   			};
	    	
	    	$.ajax({
	    		url : '/HelloPet/member/SearchPharmacy',
	    		method : 'GET',
	    		data : jsonData,
	    		dataType : 'json',
	    		success : function(data){
	    			$('.pharmacy-result').empty();
	    			
	    			let size = data.result.length;
	    			
	    			if(size == 0){
	    				alert('조회 결과가 없습니다.');
	    			}else{
	    				for(let i = 0; i < data.result.length; i++){
			    			let tags  = "<table class='pharmacy-result-table'>";
				    			tags += "<input type='hidden' id='input-zip' value='"+data.result[i].zip+"'>";
				    			tags += "<input type='hidden' id='input-tel' value='"+data.result[i].tel+"'>";
				    			tags += "<tr>";
			    				tags += "<th>번호</th>"
			    				tags += "<th>약국이름</th>"
			    				tags += "<th>약국주소</th>"
			    				tags += "<th></th>"
			    				tags += "</tr>";
			    				tags += "<tr>";
			    				tags += "<td>"+(i+1)+"</td>"
			    				tags += "<td><input type='hidden' class='input-pharName' value='"+data.result[i].pharName+"'>"+data.result[i].pharName+"</td>"
			    				tags += "<td><input type='hidden' class='input-pharAddr' value='"+data.result[i].pharAddr+"'>"+data.result[i].pharAddr+"</td>"
			    				tags += "<td><button class='btnPharmacySelect'>선택하기</button></td>"
			    				tags += "</tr>";
			    				tags += "</table>";
		    				$('.pharmacy-result').append(tags);
						}	    			
	    			}
    			}
    		});
	    });
	    
    $('.pharmacy-result').on('click','.btnPharmacySelect',function(){
    	  let zip      = $(this).closest('table').find('#input-zip').val();
    	  let tel      = $(this).closest('table').find('#input-tel').val();
	      let pharName = $(this).closest('tr').find('.input-pharName').val();
	      let pharAddr = $(this).closest('tr').find('.input-pharAddr').val();
	      
	      $('#zip').val(zip);
    	  $('input[name=medicalName]').val(pharName);
    	  $('input[name=tel]').val(tel);
    	  $('#addr1').val(pharAddr);
    	  $('#modal-pharmacy').css('display','none');
		  $('.trial').val(0);
		  $('.county').empty();
		  $('.county').append("<option value='0'>시·군·구</option>");
		  $('.SearchName').val('');
		  $('.pharmacy-result-table tr').empty();
		  document.body.style.overflow = "unset";
		  $('input[name=addr2]').focus();
	});
    
    
    $('button[name=btnTap]:eq(1)').click(function(){
    	$('.change_level').val('');
		$('.change_level').val('3');		    	
    });
    
    $('button[name=btnTap]:eq(0)').click(function(){
    	$('.change_level').val('');
		$('.change_level').val('2');		    	
    });	
	
});