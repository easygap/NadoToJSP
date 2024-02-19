<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<style>
#logo2Img {
	max-width: 90%; /* 이미지의 최대 너비를 부모 요소인 mainDiv에 맞게 설정합니다. */
	max-height: 90%; /* 이미지의 최대 높이도 부모 요소에 맞게 설정합니다. */
	position: relative; /* 위치를 조정하기 위해 position을 설정합니다. */
}

#mainDiv {
	width: 100%; /* 화면 전체 너비를 사용합니다. */
	height: 20em;
	background: #3F72AF;
	display: flex;
	justify-content: center; /* 내부 요소를 가운데 정렬합니다. */
	align-items: center; /* 내부 요소를 가운데 정렬합니다. */
	margin: 0;
	padding: 0;
}

.mainBtn {
	width: 15em;
	height: 15em;
	border-radius: 50%; /* 반지름 값을 버튼 크기에 따라 조절하세요 */
	background-color: #fff; /* 버튼 배경색 */
	color: #000;
	border: none;
	text-align: center;
	text-decoration: none;
	font-size: 16px;
	margin: 3.5em 21.5em 0.5em 21.5em;
}

.mainBtn:hover {
	box-shadow: 5px 5px 20px #B1CBE1;
	font-weight: bold;
}

#btnDiv {
	width: 100%;
	float: left;
	justify-content: space-between;
	margin-top: 8em;
	text-align: center; /* 버튼을 가운데 정렬합니다. */
}

</style>
<div id="mainDiv">
		<!-- 네비게이션 바 -->
		<jsp:include page="navbar.jsp"></jsp:include>
	<a href="/Nado/main"> <img src="/images/logo.png"
		id="logo2Img" alt="nadoIMG">
	</a>
</div>
<div id="btnDiv">
		<a th:href="/nado/list">
			<button type="button" class="mainBtn">커뮤니티</button>
		</a>
</div>
</html>