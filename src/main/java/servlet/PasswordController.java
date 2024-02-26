package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDAO;
import domain.PassCheckVO;
import dto.BoardDTO;

@WebServlet("/board/password")
public class PasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDTO dto = new BoardDTO();
		dto.setLst(Integer.parseInt(req.getParameter("lst")));
		dto.setTtl(req.getParameter("ttl"));
		dto.setCntns(req.getParameter("cntns"));
		dto.setPwd(req.getParameter("pwd"));
		
		String lst = req.getParameter("lst");
		String ttl = req.getParameter("ttl");
		String cntns = req.getParameter("cntns");
		String pwd = req.getParameter("pwd");
		
		System.out.println("lst의 값은 : " + " " + lst + "ttl의 값은 : " + ttl + " " + "cntns의 값은 : " + cntns + " " + "pwd의 값은 : " + pwd);
		
		BoardDAO dao = new BoardDAO();
		
		Integer check = dao.checkPassword(dto);
		
		System.out.println("check의 값은 : " + check);
		
		dao.close();
		
		return check;
	}

}
