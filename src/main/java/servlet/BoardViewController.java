package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import dto.BoardDTO;

@WebServlet("/board/view")
public class BoardViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDAO dao = new BoardDAO();
		
		String lst = req.getParameter("lst");
		dao.updateVisitCount(lst);    //조회수 1 증가
		BoardDTO dto = dao.selectView(lst);
		dao.close();
		
		String cntns = dto.getCntns();
		if(dto.getCntns() != null)
			dto.setCntns(cntns.replaceAll("\r\n", "<br/>"));    //줄바꿈 처리
		
		req.setAttribute("dto", dto);
		System.out.println("컨텐츠 내용 : " + dto.getCntns());
		req.getRequestDispatcher("../board/Board_view.jsp").forward(req, resp);
	}

}
