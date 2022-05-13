<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/style.css">
<title>장바구니 목록</title>
</head>
<script type="text/javascript">

/*
 *  [ 체크박스 ] -------------------------------------------------------------------
 */
 
 /* 전체 체크 */
 function checkAll(thrForm) {
 	if(theForm.remove.length == undefined){ // 장바구니 항목을 선택하는 체크박스가 1개 일때(즉, 개 상품이 1개만 담겼을 때) length는 undefined가 된다.(이유? length는 배열에만 존재한다.)
		theForm.remove.checked = theForm.allCheck.checked; // 체크하면 true, 체크해제하면 false
	}else{ // 장바구니 항목을 선택하는 체크박스가 여러개일 때 (=배열객체(remove가 배열이됨)일때. 즉, 개상품이 2개이상 담겼을 때) (장바구니 객체가 여러개일 경우 배열객체로 전달받음 -> length가 존재함)
		for(var i=0; i<theForm.remove.length; i++){
			theForm.remove[i].checked = theForm.allCheck.checked; // 장바구니항목에 있는 remove체크박스 값들을 전체 체크
		}
	}
}

function checkQtyUp(m_id) {
	location.href = "menuCartQtyUp.kiosk?m_id=" + encodeURIComponent(m_id);
}

function checkQtyDown(m_id, qty) {
	if(qty != 1){ // 현재 수량이 1보다 클때만 장바구니에 있는 현재수량을 감소시킬수 있음 (0은 존재하지 않음)
		location.href = "menuCartQtyDown.kiosk?m_id=" + encodeURIComponent(m_id);
	}
}

/*
 *  [ 삭제 ] -------------------------------------------------------------------
 */
/* 전체 삭제 */
function removeCartAll() {
	if(confirm("정말 모두 삭제하시겠습니까?\n 삭제 후 다시 복원되지 않습니다. ") == true){
		location.href = "menuCartRemoveAll.kiosk";
	}else{
		return false;
	}
}

/* 해당하는 상품만 삭제 */
function removeCart(m_id) {
	if(confirm(m_id + "을(를) 삭제하시겠습니까?\n 삭제 후 다시 복원되지 않습니다. ") == true){
		/* get방식으로 m_id를 전달해줘야함  (encodeURIComponent : 모든문자를 알아서 utf-8로 인코딩하는 함수)*/
		location.href = "menuCartRemove.kiosk?m_id=" +encodeURIComponent(m_id);
	}else{
		return false;
	}
}
</script>
<body>

<section>

	<c:if test="${cartList != null && cartList.size() > 0 }">
		<h2>장바구니 목록</h2>
		<form method="post" name="f">
			<table>
				<tr>
					<td> <!-- 전체 체크박스 -->
						<input type="checkbox" name="allCheck" onclick="checkAll(this.form)">
					</td>
					<td>메뉴번호</td>
					<td>메뉴이미지</td>
					<td>메뉴명</td>
					<td>가격</td>
					<td>수량</td>
					<td>
						<input type="button" value="전체삭제" onclick="removeCartAll()">
					</td>
				</tr>
				<!-- 향상된 for문 시작 -->
					<c:forEach var="cart" items="${cartList }" varStatus="status">
						<tr>
							<td><input type="checkbox" name="remove" value="${cart.encoding_m_id }"></td>
							<td>${status.count }</td> <!-- <td>${status.index+1}</td> -->
							<td><img src="images/${cart.image }">
							<td>${cart.name }</td>
							<td>
								${cart.price }원
								<input type="hidden" name="priceList" value="${cart.price }">
							</td>
							<td> <!-- ★★ 함수 호출 시 주의사항 : 함수('숫자 타입이 아닌 경우'), 함수(숫자타입) -->
								<a href="javascript:checkQtyUp('${cart.m_id}')">
									<img src="${pageContext.request.contextPath}/images/up.jpg" id="upImage" border="0" alt="수량 1씩증가">
								</a>
								<br>
								
								<!-- 현재 수량 -->
								${cart.qty }
								<input type="hidden" name="qtyList" value="${cart.qty }">
								
								<br>
								<a href="javascript:checkQtyDown('${cart.m_id}', ${cart.qty })">
									<img src="${pageContext.request.contextPath}/images/down.jpg" id="downImage" border="0" alt="수량 1씩감소">
								</a>
							</td>
							<td>
								<input type="button" value="삭제" onclick="removeCart('${cart.m_id}')">
							</td>
						</tr>
					</c:forEach>
					<!-- 향상된 for문  끝 -->
					<tr>
						<th colspan="7">
							총금액 : ${totalMoney }원
							<input type="hidden" name="totalMoney" value="${totalMoney}">
						</th>
					</tr>
					<tr>
						<th colspan="7" ><!-- ${totalMoney}값을 get방식으로 전달 -->
							<a href="menuCartOrder.kiosk?totalMoney=${totalMoney}">[구매하기]</a>
						</th>
					</tr>
			</table>
		</form>
	</c:if>
	
<!----------------------------------------------------------------------->	
	
	<c:if test="${cartList == null }">
		<div>장바구니가 비어있습니다.</div>
	</c:if>
	
</section>

</body>
</html>