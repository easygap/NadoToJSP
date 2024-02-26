<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 게시글 상세보기</title>
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
			<!-- View.jsp 코드 시작 -->

			<table class="table"
				style="text-align: left; border: 1px solid #dddddd">
				<colgroup>
					<col width="10%" />
					<col width="*" />
				</colgroup>

				<!-- 게시글 제목 -->
				<tr>
					<td>제목</td>
					<td colspan="3">${ dto.ttl }</td>
				</tr>
				<!-- 첨부파일 -->
				<tr>
					<td>첨부파일</td>
					<td>
						<c:if test="${ not empty dto.file_nm }">
						<a href="../board/download?filename=${ dto.file_nm }&idx=${ dto.lst }">${ dto.file_nm }</a>
						</c:if>
					</td>
				</tr>
				<!-- 게시글 내용 -->
				<tr>
					<td>내용</td>
					<td colspan="5" height="100">
					<br />${ dto.cntns }</td>
				</tr>
				
				<!-- 하단 메뉴(버튼) -->
				<tr>
					<td colspan="4" align="center">
						<button type="button" class="ViewButton" onclick="location.href='../board/Edit.jsp?num=${ param.lst }&title=${ dto.ttl }&content=${ dto.cntns }';">수 정</button>
						<button type="button" class="List" onclick="location.href='../board/list';">목 록</button>
						<button type="button" class="ViewButton" onclick="removeCheck();">삭 제</button>
					</td>
				</tr>
			</table>

			<form name="commentForm" id="commentForm" method="post"
				action="../board/CommWrite.do">
				<table class="table table-striped"
					style="text-align: center; border: 1px solid #dddddd; width: 100%;">
					<%-- 홀,짝 행 구분 --%>
					<thead>
						<tr>
							<th colspan="3"
								style="background-color: #eeeeeee; text-align: center;">댓글</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td style="text-align: left;"></td>
							<td style="text-align: right;">
							<a
								href="javascript:setEditMode('');"
								class="btn">수정</a> <a
								href="../board/CommEdit.do?action=delete&commNum="
								class="btn">삭제</a></td>
						</tr>

						<tr>
							<td style="text-align: left; width: 80%;"><textarea
									type="text" class="form-control" placeholder="댓글을 입력하세요."
									id="commContent" name="commContent" style="width: 100%;"
									maxlength="1024"></textarea></td>
							<td style="text-align: center; vertical-align: middle;"> <input
								type="hidden" id="num" name="num" value=""> <input
								type="hidden" id="commNum" name="commNum" value=""> <input
								type="hidden" id="commAction" name="action" value=""> <input
								type="hidden" id="commId" name="id" value=""> <input
								type="submit" class="btn btn-sm" id="commSubmitButton"
								value="댓글입력"></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>