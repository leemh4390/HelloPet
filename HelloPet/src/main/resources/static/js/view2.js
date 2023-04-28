/**
 * 날짜 : 2023-03-21
 * 이름 : 장인화
 * 내용: view2에서 주소 새로 불러오기 스크립트 
 */

 
/**
 * 날짜 : 2023-03-21
 * 이름 : 장인화
 * 내용: view2에서 주소 새로 불러오기 스크립트 
 */
	function view2() {
		// 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
		var infowindow = new kakao.maps.InfoWindow({zIndex:1});
		
		var mapContainer = document.getElementById('map'); // 지도를 표시할 div 
		var mapOption = {
		    center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
		    level: 3 // 지도의 확대 레벨
		};  
		
		// 지도를 생성합니다    
		var map = new kakao.maps.Map(mapContainer, mapOption); 
		
		// 장소 검색 객체를 생성합니다
		var ps = new kakao.maps.services.Places(); 
		
		// 키워드로 장소를 검색합니다
		ps.keywordSearch(search, placesSearchCB); 
		
		
		
		// 키워드 검색 완료 시 호출되는 콜백함수 입니다
		function placesSearchCB (data, status, pagination) {
		    if (status === kakao.maps.services.Status.OK) {
		
		        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
		        // LatLngBounds 객체에 좌표를 추가합니다
		        var bounds = new kakao.maps.LatLngBounds();
		
		        for (var i=0; i<data.length; i++) {
		            displayMarker(data[i]);    
		            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
		        }       
		
		        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
		        map.setBounds(bounds);
		    } 
		}


	    
	    // 지도에 마커를 표시하는 함수입니다
	    function displayMarker(place) {
	        
	        // 마커를 생성하고 지도에 표시합니다
	        var marker = new kakao.maps.Marker({
	            map: map,
	            position: new kakao.maps.LatLng(place.y, place.x) 
	        });
	
	        // 마커에 클릭이벤트를 등록합니다
	        kakao.maps.event.addListener(marker, 'click', function() {
	            // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
	            infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
	            infowindow.open(map, marker);
	            
	            // 마커 정보 (이것저것~~ 제목,주소,전번) 밑에 출력하자
	            var content = "<img src='/HelloPet/img/search-지도.jpg' alt='지도아이콘'></br>";
	            
	            content += "<span class='placeName'>" + place.place_name + "</span></br>"
	            
	            if (place.road_address_name) {
	                content += "<span class='placeAddr'>" + place.road_address_name + "</span></br>"
	            } else if (place.address_name) {
	                content += "<span class='placeAddr'>" +  place.address_name + "</span></br>"
	            }
	            
	            if (place.phone) {
	                content += "<span class='placePh'>" + place.phone; + "</span></br>"
	            }
	            
	            console.log(content);
	            
	            $('.addr').empty();
	            $('.addr').append(content);
	            
	            
	            $('#click').click(function(){
			        $('#ns').hide();
			
			        let newAddr = $('input[name=NewAddr]').val();
			        search = newAddr + ' 동물병원'; 
			        console.log("newAddr : " + newAddr);
			        console.log("s2 : " + search);
			
			        // 장소 검색 다시 실행
			        ps.keywordSearch(search, placesSearchCB); 
			        
			     });   
	            
	        });
	    }
	
	}
