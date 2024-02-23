<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
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

				<form name="writeFrm" method="post" enctype="multipart/form-data"
					action="../board/write" onsubmit="return validateForm(this);">

					<div class="mb-3">
    					<label for="name" style="display: inline-block;">닉네임</label>
    					<input type="text" style="width:400px; display: inline-block;" maxlength="10" class="form-control" name="nm" id="name" placeholder="10자 이하로 입력.">
    					<label for="title" style="display: inline-block;">제목</label>
    					<input type="text" style="width:1100px; display: inline-block;" maxlength="30" class="form-control" name="ttl" id="title" placeholder="30자 이하로 입력.">
					</div>

					
					<div class="filebox">
						<input type="file" name="ofile" onchange="fileCheck(this)" id="ofile">
					</div>

					<div class="mb-3">
						<label for="content">내용</label>
						<textarea class="form-control" rows="11" name="cntns"
							maxlength=1000 id="content" placeholder="내용을 1000자 이하로 입력"></textarea>
					</div>
					
					<div class="mb-3">
							<label for="password">비밀번호</label> <input type="text"
							style="width:300px;" class="form-control" name="pwd" id="password"
							maxlength=20 placeholder="특수문자를 포함한 20자 이하로 입력.">
					</div>
					
					<div class="mybtn">
						<input
							type="button" onclick="location.href='../board/list';"
							class="List" value="취 소">
						<input type="submit" class="write"
							value="등 록">
					</div>
				</form>
			</div>
		</div>
</body>
</html>