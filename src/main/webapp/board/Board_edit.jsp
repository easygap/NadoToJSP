<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 게시글 수정</title>
<!-- css 양식 가져오기 -->
    <link href="../css/styles.css" rel="stylesheet" />
</head>
<body>
<!-- 네비게이션 바 -->
	<div>
		<jsp:include page="navbar.jsp"></jsp:include>
	</div>
	<div id="page-content-wrapper">

		<div class="container-fluid">
			<div id="UND_DV"></div>
			
			<!-- Board_edit.jsp 코드 시작 -->
		<form name="writeFrm" method="post" enctype="multipart/form-date"
		action="../board/edit" onsubmit="return validateForm(this);">
		<input type="hidden" name="lst" value="${ dto.lst }" /> <input
			type="hidden" name="filenm" value="${ dto.file_nm }" /> 

		<table class="table" border="1" width="90%">
			<tr>
				<td>제목</td>
				<td><input type="text" name="name" style="width: 30%;"
					value="${dto.ttl}" /></td>
			</tr>
			<tr>
				<td>첨부 파일</td>
				<td><input type="file" name="filenm" /></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="content" style="width: 90%; height: 100px;">${ dto.cntns }</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<button type="button" class="ViewButton"
						onclick="location.href='../board/list';">목 록</button>
					<button type="submit" class="ViewButton">수 정</button>
				</td>
			</tr>
		</table>
	</form>
	</div>
	</div>
</body>
</html>