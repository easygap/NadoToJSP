package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.BoardDAO;
import dto.BoardDTO;

@WebServlet("/board/delete")
public class BoardDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = new BoardDTO();
		
		System.out.println("**lst의 값은 : " + req.getParameter("lst") + "입니다.**");

		dto.setLst(Integer.parseInt(req.getParameter("lst")));
		
		dao.deleteBoard(dto);
		
		dao.close();
		
		req.getRequestDispatcher("../board/list").forward(req, resp);
	}
}
