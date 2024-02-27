package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import dao.BoardDAO;
import dto.BoardDTO;
import utils.SHA256;

@WebServlet("/board/password")
public class PasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDTO dto = new BoardDTO();
		dto.setLst(Integer.parseInt(req.getParameter("lst")));
		dto.setTtl(req.getParameter("ttl"));
		dto.setCntns(req.getParameter("cntns"));

		SHA256 sha256 = new SHA256();    // 암호화 알고리즘 적용
		
		try{ // 비밀번호 sha256 암호화 시도
			String cryptogram = sha256.encrypt(req.getParameter("pwd"));
			dto.setPwd(cryptogram);
		}catch (NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		
		BoardDAO dao = new BoardDAO();
		
		Integer check = dao.checkPassword(dto);
		
		System.out.println("check의 값은 : " + check);    //올바른 값이 출력되는지 확인
		
		dao.close();
		
		JSONObject jObj = new JSONObject();
		
		jObj.put("check", check);
		resp.setContentType("application/x-json; charset=utf-8");
		resp.getWriter().print(jObj);
	}
}