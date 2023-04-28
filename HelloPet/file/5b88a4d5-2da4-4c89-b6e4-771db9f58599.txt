<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="./header.jsp"></jsp:include>
<script>
	
	//포인트 작업
	$(document).ready(function(){
		
		$('input[name=all]').click(function() {
			if($(this).is(":checked")) $("input[name=check]").prop('checked', true);
			else $("input[name=check]").prop("checked", false);
		});
	
		$("input[name=check]").click(function() {
			
			var total = $("input[name=check]").length;
			var checked = $("input[name=check]:checked").length;
	
			if(total != checked) $('input[name=all]').prop("checked", false);
			else $('input[name=all]').prop("checked", true); 
		});
		
		
		//계산 필드
		$('input[class=checkprice]').prop('checked',true);
		
		var count = 0;
		var price = 0;
		var total = 0;
		var discount = 0;
		var delivery = 0;
		var point = 0;
		
		$('input[name=count]').each(function(){
			count += Number($(this).val());
		});
		
		$('input[name=price]').each(function(){
			price += Number($(this).val());
		});
		
		$('input[name=discount]').each(function(){
			discount += Number($(this).val());
		});
		$('input[name=delivery]').each(function(){
			delivery += Number($(this).val());
		});
		$('input[name=point]').each(function(){
			point += Number($(this).val());
		});
		
		$('input[name=total]').each(function(){
			total += Number($(this).val());
		});
		
		$('td[class=product_num]').empty("");
		$('td[class=product_num]').append(count);
		$('td[class=product_num]').append("<input type='hidden' name='product_num' value='"+ count +"'>");
		$('td[class=product_price]').empty("");
		$('td[class=product_price]').append(price.toLocaleString()+'원');
		$('td[class=product_price]').append("<input type='hidden' name='product_price' value='"+ price +"'>");
		$('td[class=product_discount]').empty("");
		$('td[class=product_discount]').append(discount.toLocaleString()+'원');
		$('td[class=product_discount]').append("<input type='hidden' name='product_discount' value='"+ discount +"'>");
		$('td[class=product_total]').empty("");
		$('td[class=product_total]').append(total.toLocaleString());
		$('td[class=product_total]').append("<input type='hidden' name='product_total' value='"+ total +"'>");
		$('td[class=product_savePoint]').empty("");
		$('td[class=product_savePoint]').append(Math.round((point * total / 100)).toLocaleString()+'점');
		$('td[class=product_savePoint]').append("<input type='hidden' name='product_savePoint' value='"+ Math.round(point * total / 100) +"'>");
		$('td[class=product_delivery]').empty("");
		$('td[class=product_delivery]').append(delivery.toLocaleString()+'원');
		$('td[class=product_delivery]').append("<input type='hidden' name='product_delivery' value='"+ delivery +"'>");
		
		let totprice = $('input[name=product_total]').val();
		
		let usePoint = $('input[id=usedPoint]').val();
		
		if(usePoint == ''){
			$('input[id=usedPoint]').val(0);
		}
		
		$('.btnPoint').click(function(){
			let userPoint = ${sessUser.point}
			let point = $('input[id=usedPoint]').val();
			let totPrice = $('input[name=product_total]').val();
			tot_price = totPrice - point; //최종금액
			
			if(point < 3000){
				alert('포인트는 3000점 이상 사용가능합니다.');
			}else if(userPoint < point){
				alert("현재 보유중인 포인트는 " + userPoint + " 입니다. " + "\n할인 받을 포인트를 확인해주세요." );
			}else if(userPoint >= point && point >= 3000){
				$('td[class=product_pointDiscount]').empty("");
				$('td[class=product_pointDiscount]').append(point);
				$('td[class=product_total]').empty("");
				$('td[class=product_total]').append(tot_price.toLocaleString());
				$('td[class=product_total]').append("<input type='hidden' name='product_total1' value='"+ tot_price +"'>");
				$('td[class=product_total]').append("<input type='hidden' name='product_total' value='"+ totPrice +"'>");
			}
		});
			
		//주문하기
		$('#order').click(function(e){
			e.preventDefault();
			
			console.log('here1');
			let uid = $('input[name=uid]').val();
			let ordCount = $('input[name=product_num]').val();
			let ordPrice = $('input[name=product_total]').val();
			let ordDiscount = $('input[name=product_discount]').val();
			let ordDelivery = $('input[name=product_delivery]').val();
			let savePoint = $('input[name=product_savePoint]').val();
			let usedPoint = $('input[id=usedPoint]').val();
			let ordTotPrice = $('input[name=product_total]').val();
			let recipName = $('input[name=orderer]').val();
			let recipHp = $('input[name=hp]').val();
			let recipZip = $('input[name=zip]').val();
			let recipAddr1 = $('input[name=addr1]').val();
			let recipAddr2 = $('input[name=addr2]').val();
			let ordPayment = $('input[name=payment]:checked').val();
			
			if(ordPayment == undefined){
				alert('결제 방법을 확인해주세요.');
			}
			
			console.log('here2');
			let jsonData = {
					'uid' : uid,
					'ordCount' : ordCount,
					'ordPrice' : ordPrice,
					'ordDiscount' : ordDiscount,
					'ordDelivery' : ordDelivery,
					'savePoint' : savePoint,
					'usedPoint' : usedPoint,
					'ordTotPrice' : ordTotPrice,
					'recipName' : recipName,
					'recipHp' : recipHp,
					'recipZip' : recipZip,
					'recipAddr1' : recipAddr1,
					'recipAddr2' : recipAddr2,
					'ordPayment' : ordPayment
			};
			
			console.log('here3');
			$.ajax({
				url : '/kmarket/product/order.do',
				method : 'POST',
				data : jsonData,
				dataType : 'json',
				success : function(data){
					console.log('here4');
					if(data.result > 0){
						location.href = '/kmarket/product/complete.do';		
				}			
			}
		});
	});
		
		$('#order').click(function(e){
			e.preventDefault();
			
			let arr = [];
			
			$('input[name=check]:checked').each(function(){
				arr.push($(this).val());
			});
			
			console.log("checkNo : " + arr);
			
			let jsonData = {
					"arr" : arr
			}
			
			$.ajax({
				url : '/kmarket/product/complete.do' ,
				type : 'POST' ,
				data : jsonData ,
				traditional : true ,
				dataType : 'json',
				success : function(data) {
					
				}
			});
			
			
		});
});

</script>
        <main id="product">
            <aside>
                <ul class="category">
                    <li><i class="fa fa-bars" aria-hidden="true"></i>카테고리</li>
                    <li>
                        <a href="#" id="clothes"><i class="fas fa-tshirt"></i>패션</a>
                        <ol class="cate1">
                            <li><a href="#">남성의류</a></li>
                            <li><a href="#">여성의류</a></li>
                            <li><a href="#">잡화</a></li>
                            <li><a href="#">뷰티</a></li>
                        </ol>
                    </li>
                    <li>
                        <a href="#" id="electric"><i class="fas fa-laptop"></i>가전·디지털</a>
                        <ol class="cate2">
                            <li><a href="#">노트북/PC</a></li>
                            <li><a href="#">가전</a></li>
                            <li><a href="#">휴대폰</a></li>
                            <li><a href="#">기타</a></li>
                        </ol>
                    </li>
                    <li>
                        <a href="#" id="food"><i class="fas fa-utensils"></i>식품·생필품</a>
                        <ol class="cate3">
                            <li><a href="#">신선식품</a></li>
                            <li><a href="#">가공식품</a></li>
                            <li><a href="#">건강식품</a></li>
                            <li><a href="#">생필품</a></li>
                        </ol>
                    </li>
                    <li>
                        <a href="#" id="deco"><i class="fas fa-home"></i>홈·문구·취미</a>
                        <ol class="cate4">
                            <li><a href="#">가구/DIY</a></li>
                            <li><a href="#">침구·커튼</a></li>
                            <li><a href="#">생활용품</a></li>
                            <li><a href="#">사무용품</a></li>
                        </ol>
                    </li>
                </ul>
            </aside>

            <section class="order">
                <nav>
                    <h1>상품목록</h1>
                    <p>HOME > <span>패션·의류·뷰티</span> > <strong>남성의류</strong></p>
                </nav>

                <form action="#" method="post" class="order" onsubmit="return false;">
                    <!-- 주문 상품 목록 -->
                    <table border="0">
                        <tr>
                            <th><input type="checkbox" name="all" class="checkprice" ></th>
                            <th>상품명</th>
                            <th>총수량</th>
                            <th>판매가</th>
                            <th>할인</th>
                            <th>포인트</th>
                            <th>배송비</th>
                            <th>소계</th>
                        </tr>
                        <c:choose>
                        <c:when test="${empty carts}">
                        <tr class="empty">
                            <td colspan="7">장바구니에 상품이 없습니다.</td>
                        </tr>
                        </c:when>
                        <c:otherwise>
                        <c:forEach var="cart" items="${carts }">
                        <tr>
                            <td><input type="checkbox" name="check" class="checkprice" value="${cart.cartNo }"></td>
                           <td>
                                <article>
                                    <a href="#"><img src="/kmarket/file/${cart.cate1 }/${cart.cate2}/${cart.thumb1}" alt="썸네일"></a>
                                    <div>
                                        <h2><a href="/kmarket/product/view.do?proNo=${cart.proNo }&cate1=${cart.cate1}&cate2=${cart.cate2}">${cart.proName }</a>
                                        	<input type="hidden" name="proNo" value="${cart.proNo }" >
                                        </h2>
                                        <p>${cart.descript }</p>
                                    </div>
                                </article>
                            </td>
                            <td>${cart.count }
                            	<input type=hidden name="count" value="${cart.count }"></td>
                            <td><fmt:formatNumber value="${cart.price }" pattern="#,###원" />
                            	<input type="hidden" name="price" value="${cart.price }">
                            </td>
                            <td><fmt:formatNumber value="${(cart.discount)*(cart.price)/100}" pattern="#,###원" />
                            	<input type="hidden" name="discount" value="${(cart.discount)*(cart.price)/100 }">
                            </td>
                            <td>${cart.point }%
                            	<input type="hidden" name="point" value="${cart.point }">
                            </td>
                            <td>${cart.delivery }
                            	<input type="hidden" name="delivery" value="${cart.delivery }">
                            </td>
                            <td><fmt:formatNumber value="${cart.total }" pattern="#,###원" />
                            	<input type="hidden" name="total" value="${cart.total }">
                            </td>
                        </tr>
                        </c:forEach>
                        </c:otherwise>
                        </c:choose>
                    </table>

                    <!-- 최종결제정보 -->
                    <div class="final">
                        <h2>최종결제정보</h2>
                        <table border="0">
                            <tr>
                                <td>상품수</td>
                                <td class="product_num">0</td>
                              </tr>
                              <tr>
                                <td>상품금액</td>
                                <td class="product_price">0</td>
                              </tr>
                              <tr>
                                <td>할인금액</td>
                                <td class="product_discount">0</td>
                              </tr>
                              <tr>
                                <td>배송비</td>
                                <td class="product_delivery">0</td>
                              </tr>              
                              <tr>
                                <td>할인 포인트</td>
                                <td class="product_pointDiscount">0</td>
                              </tr>
                              <tr>
                                <td>적립 포인트</td>
                                <td class="product_savePoint">0</td>
                              </tr>
                              <tr>
                                <td>전체주문금액</td>
                                <td class="product_total">
                                	<input type="hidden" name="product_total" value="">
                                </td>
                              </tr>
                        </table>
                        <input type="submit" name="order" id="order" value="결제하기">
                    </div>

                    <!-- 배송 정보 -->
                    <article class="delivary">
                        <h1>배송정보</h1>
                        <table border="0">
                            <tr>
                                <td>주문자</td>
                                <td><input type="text" name="orderer" value="${sessUser.name }" ></td>
                                <td><input type="hidden" name="uid" value="${sessUser.uid }" ></td>
                            </tr>
                            <tr>
                                <td>휴대폰</td>
                                <td><input type="text" name="hp" value="${sessUser.email }">
                                <span>- 포함 입력</span></td>
                            </tr>
                            <tr>
                                <td>우편번호</td>
                                <td><input type="text" name="zip" value="${sessUser.zip }">
                                <input type="button" value="검색"></td>
                            </tr>
                            <tr>
                                <td>기본주소</td>
                                <td><input type="text" name="addr1" value="${sessUser.addr1 }"></td>
                            </tr>
                            <tr>
                                <td>상세주소</td>
                                <td><input type="text" name="addr2" value="${sessUser.addr2 }"></td>
                            </tr>
                        </table>
                    </article>
                    <article class="discount">
                        <h1>할인정보</h1>
                        <div>
                            <p>
                                현재 포인트 : 
                                <span class="userPoint"> ${sessUser.point } </span>점
                            </p>
                            <label>
                                <input type="text" name="point" id="usedPoint">점
                                <input type="button" name="btn btnpoint" class="btnPoint" value="적용">
                            </label>
                            <span>
                                포인트는 최소 3000점 이상 사용가능합니다.
                            </span>
                        </div>
                    </article>
                    <!-- 결제 방법 -->
                    <article class="payment">
                        <h1>결제방법</h1>
                        <div class="card">
                            <span>신용카드</span>
                            <p>
                                <label>
                                    <input type="radio" name="payment" value="1">신용카드 결제
                                </label>
                                <label>
                                    <input type="radio" name="payment" value="2">체크카드 결제
                                </label>
                            </p>
                        </div>
                        <div class="account">
                            <span>계좌이체</span>
                            <p>
                                <label>
                                    <input type="radio" name="payment" value="3">실시간 계좌이체
                                </label>
                                <label>
                                    <input type="radio" name="payment" value="4">무통장 입금
                                </label>
                            </p>
                        </div>
                        <div class="etc">
                            <span>기타</span>
                            <p>
                                <label>
                                    <input type="radio" name="payment" value="type5">휴대폰결제
                                </label>
                                <label>
                                    <input type="radio" name="payment" value="type6">카카오페이
                                    <img src="/kmarket/img/product/ico_kakaopay.gif" alt="ico_kakaopay">
                                </label>
                            </p>
                        </div>
                    </article>
                    <article class="alert">
                        <ul>
                            <li>
                                <span>
                                    케이마켓 어쩌구
                                </span>
                            </li>
                            <li>
                                <span>
                                    어쩌구
                                </span>
                            </li>
                            <li>
                                <span>
                                    어쩌구
                                </span>
                            </li>
                        </ul>
                    </article>
                </form>
            </section>

        </main>
<jsp:include page="./footer.jsp"></jsp:include>