package utils;

public class BoardPage {
	public static String pagingStr(int totalCount, int pageSize, int blockPage, int pageNum, String reqUrl){
		String pagingStr = "";
		
		int totalPages = (int) (Math.ceil(((double) totalCount / pageSize)));    //전체 페이지 수 계산
		
		int pageTemp = (((pageNum - 1) / blockPage) * blockPage) + 1;    //'이전 페이지 블록 바로가기' 출력
		if(pageTemp != 1){
			pagingStr += "<a href='" + reqUrl + "?pageNum=1'>[처 음]</a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + (pageTemp - 1)
					+ "'>[이 전]</a>";
		}
		
		int blockCount = 1;
		while(blockCount <= blockPage && pageTemp <= totalPages){    //각 페이지 번호 출력
			if(pageTemp == pageNum){    //현재 페이지는 링크를 걸지 않음
				pagingStr += "&nbsp;" + pageTemp + "&nbsp;";
			}else{
				pagingStr += "&nbsp;<a href='" + reqUrl + "?pageNum=" + pageTemp
						+ "'>" + pageTemp + "</a>&nbsp;";
			}
			pageTemp++;
			blockCount++;
		}
		
		if(pageTemp <= totalPages){    //'다음 페이지 블록 바로가기' 출력
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + pageTemp
					+ "'>[다 음]</a>";
			pagingStr += "&nbsp;";
			pagingStr += "<a href='" + reqUrl + "?pageNum=" + totalPages
					+ "'>[맨 끝]</a>";
		}
		return pagingStr;                            
	}
}