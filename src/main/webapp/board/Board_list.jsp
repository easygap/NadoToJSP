<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 게시글 목록</title>
	<!-- css 양식 가져오기 -->
    <link href="../css/styles.css" rel="stylesheet" />
</head>
<body>
	<!-- 네비게이션 바 -->
	<div>
			<jsp:include page="navbar.jsp"></jsp:include>
	</div>
				<div id="UND_DV">
    				<button type="button" class="write" onclick="location.href='Board_write.jsp';">글 쓰 기</button>
				</div>

				
				<!-- 검색 폼 -->
				<form method="get">
					<table class="table">
						<tr>
							<td align="center"><select class="selectpicker" style="height:27px" name="searchField">
									<option value="ttl">제목</option>
									<option value="cntns">내용</option>
									<option value="wrter">글쓴이</option>
							</select> <input type="text" name="searchWord" /> <input type="submit"
								value="검 색 하 기" class="searchB"/></td>
						</tr>
					</table>
				</form>
				
				<!-- 목록 테이블 -->
				<table class="table table-hover">
				<thead>
					<tr>
						<th width="8%" align="center">No.</th>
						<th width="*">Title</th>
						<th width="13%">User</th>
						<th width="8%">View</th>
						<th width="13%">Date</th>
					</tr>
		
					<c:choose>
						<c:when test="${ empty boardLists }">
							<!-- 게시물이 없을 때 -->
							<tr>
								<td colspan="6" align="center">게시글이 존재하지 않습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<!-- 게시물이 있을 때 -->
							<c:forEach items="${ boardLists }" var="row" varStatus="loop">
								<tr>
									<td style="padding-left: 20px;">
										<!-- 번호 --> ${ map.totalCount - (((map.pageNum-1) * map.pageSize) + loop.index)}
									</td>
									<td>
										<!-- 제목(링크) --> <a href="../board/view?lst=${ row.lst }">${ row.ttl }</a>
									</td>
									<!-- 작성자 -->
									<td>${ row.wrter }</td>
									<!-- 조회수 -->
									<td>${ row.vew }</td>
									<!-- 날짜 -->
									<td style="padding-left: 25px;">${ row.wr_date }</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
					</thead>
				</table>

				<!-- 하단 메뉴(바로가기) -->
				<table class="table">
					<tr align="center">
						<td>${ map.pagingImg }</td>
					</tr>
				</table>
</body>
</html>