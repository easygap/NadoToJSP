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
			
			<!-- Board_view.jsp 코드 시작 -->
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
						<button type="button" id="editBtn" class="ViewButton">수 정</button>
						<button type="button" class="List" onclick="location.href='../board/list';">목 록</button>
						<button type="button" id="delBtn" class="ViewButton" onclick="removeCheck();">삭 제</button>
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
<script>
    var ttl = "${dto.ttl}";
    var cntns = "${dto.cntns}";
    
    $('#editBtn').on("click", (e) => {
        password = prompt("게시글 수정을 위한 비밀번호를 입력하세요.");
        if (password != null) {
            $.ajax({
                type: 'post',
                url: 'http://localhost:8080/Nado/board/password',
                dataType: 'text',
                data: {
                    lst: ${param.lst},
                    ttl: ttl,
                    cntns: cntns,
                    pwd: password
                },
                success: function(data, textStatus) {
                	var responseData = JSON.parse(data); // JSON 문자열을 객체로 변환
                	let url = '../board/Board_edit.jsp?lst=${param.lst}'
                    if (responseData.check === 0) {    //비밀번호 불일치
                        console.log("check의 값은 : " + responseData.check);
                        alert("비밀번호 불일치!"); 
                    } else {    //비밀번호 일치
                        console.log("check의 값은 : " + responseData.check);
                        alert("비밀번호 일치! 게시글 수정 페이지로 이동합니다.");
                        location.replace(url);
                    }
                },
                error: function(data, textStatus) {
                	console.log('전체 데이터:', data);
                    console.log('check의 값은 : ' + data.check);
                    console.log('error');
                }
            })
        }
    });
    
    $('#delBtn').on("click", (e) => {
        password = prompt("게시글 삭제를 위한 비밀번호를 입력하세요.");
        if (password != null) {
            $.ajax({
                type: 'post',
                url: 'http://localhost:8080/Nado/board/password',
                dataType: 'text',
                data: {
                    lst: ${param.lst},
                    ttl: ttl,
                    cntns: cntns,
                    pwd: password
                },
                success: function(data, textStatus) {
                	var responseData = JSON.parse(data); // JSON 문자열을 객체로 변환
                	let url = '../board/delete?lst=${param.lst}'
                    if (responseData.check === 0) {    //비밀번호 불일치
                        console.log("check의 값은 : " + responseData.check);
                        alert("비밀번호 불일치!"); 
                    } else {    //비밀번호 일치
                    	console.log("check의 값은 : " + responseData.check);
                    	if (confirm("정말 삭제하시겠습니까?") == true) {    //비밀번호 일치 시 게시글 삭제 여부 확인
                    		location.replace(url);
                            } else {
                                return;
                        }                        
                    }
                },
                error: function(data, textStatus) {
                	console.log('전체 데이터:', data);
                    console.log('check의 값은 : ' + data.check);
                    console.log('error');
                }
            })
        }
    });
</script>

</html>