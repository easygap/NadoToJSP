<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<style>
#logo2Img {
	max-width: 90%; /* �̹����� �ִ� �ʺ� �θ� ����� mainDiv�� �°� �����մϴ�. */
	max-height: 90%; /* �̹����� �ִ� ���̵� �θ� ��ҿ� �°� �����մϴ�. */
	position: relative; /* ��ġ�� �����ϱ� ���� position�� �����մϴ�. */
}

#mainDiv {
	width: 100%; /* ȭ�� ��ü �ʺ� ����մϴ�. */
	height: 20em;
	background: #3F72AF;
	display: flex;
	justify-content: center; /* ���� ��Ҹ� ��� �����մϴ�. */
	align-items: center; /* ���� ��Ҹ� ��� �����մϴ�. */
	margin: 0;
	padding: 0;
}

.mainBtn {
	width: 15em;
	height: 15em;
	border-radius: 50%; /* ������ ���� ��ư ũ�⿡ ���� �����ϼ��� */
	background-color: #fff; /* ��ư ���� */
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
	text-align: center; /* ��ư�� ��� �����մϴ�. */
}

</style>
<div id="mainDiv">
		<!-- �׺���̼� �� -->
		<jsp:include page="navbar.jsp"></jsp:include>
	<a href="/Nado/main"> <img src="/images/logo.png"
		id="logo2Img" alt="nadoIMG">
	</a>
</div>
<div id="btnDiv">
		<a th:href="/nado/list">
			<button type="button" class="mainBtn">Ŀ�´�Ƽ</button>
		</a>
</div>
</html>