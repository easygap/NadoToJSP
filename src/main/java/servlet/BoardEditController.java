package servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dto.BoardDTO;

@WebServlet("/board/edit")
public class BoardEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String lst = req.getParameter("lst");
		
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = dao.selectView(lst);
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("../board/Board_edit.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String saveDirectory = req.getServletContext().getRealPath("/uploads");    //업로드 디렉토리의 물리적 경로 확인
		
		ServletContext application = getServletContext();    //설정 확인
		int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize"));    //첨부 파일 최대 용량 확인
		
	}
}
