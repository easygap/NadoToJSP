<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<style>


</style>
<!-- 네비게이션 바 -->
<div>
		<jsp:include page="navbar.jsp"></jsp:include>
</div>
<div id="mainDiv">	
	<a href="/Nado/board/"> <img src="../resources/logo.png"
		id="logo2Img" alt="nadoIMG">
	</a>
</div>
<div id="btnDiv">
		<a href="/Nado/board/list">
			<button type="button" class="mainBtn">커뮤니티</button>
		</a>
</div>
</html>