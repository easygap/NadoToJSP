/**
 * Description : 게시글 목록화 서블릿
 * 작성일 : 2024.02.21
 * 작성자 : ljs
 * Input Argument:
 *      1) map : 게시글 키워드 검색을 위한 변수
 */
package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dto.BoardDTO;
import utils.BoardPage;

@WebServlet("/board/list")
public class BoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.getWriter().append("Served at: ").append(req.getContextPath());
		
		BoardDAO dao = new BoardDAO();    //DAO 생성
		
		Map<String, Object> map = new HashMap<String, Object>();    //뷰에 전달할 매개변수 저장용 맵 생성
		
		String searchField = req.getParameter("searchField");
		String searchWord = req.getParameter("searchWord");
		if(searchWord != null) {    //쿼리스트링으로 전달받은 매개변수 중 검색어가 있다면 map에 저장
			map.put("searchField", searchField);
			map.put("searchWord", searchWord);
		}
		int totalCount = dao.selectCount(map);    //게시물 개수
		
		ServletContext application = getServletContext();    //페이징 처리
		int pageSize = Integer.parseInt(application.getInitParameter("POSTS_PER_PAGE"));    //한 페이지에 출력할 게시글 수
		int blockPage = Integer.parseInt(application.getInitParameter("PAGES_PER_BLOCK"));    //한 화면에 출력할 페이지 번호 수
		
		int pageNum = 1;    //현재 페이지 기본값
		String pageTemp = req.getParameter("pageNum");
		if(pageTemp != null && !pageTemp.equals(""))
			pageNum = Integer.parseInt(pageTemp);
		
		int start = (pageNum - 1) * pageSize + 1;    //목록에 출력할 게시글 범위
		int end = pageNum * pageSize;
		map.put("start", start);
		map.put("end", end);
		
		List<BoardDTO> boardLists = dao.selectList(map);    //게시물 목록 받기
		
		dao.close();    //DB연결 닫기
		
		String pagingImg = BoardPage.pagingStr(totalCount, pageSize, blockPage, pageNum, "../board/list");    //바로가기 영역 HTML 문자열
		map.put("pagingImg", pagingImg);
		map.put("totalCount", totalCount);
		map.put("pageSize", pageSize);
		map.put("pageNum", pageNum);
		
		req.setAttribute("boardLists", boardLists);    //게시물 목록 request 영역에 저장
		req.setAttribute("map", map);    //페이징 처리를 위한 데이터 request 영역에 저장
		req.getRequestDispatcher("/board/Board_list.jsp").forward(req, resp);
	}
}
