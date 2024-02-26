package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.BoardDAO;
import dto.BoardDTO;
import dto.FilesDTO;
import utils.FileUpload;
import utils.JSFunction;
import utils.SHA256;

@WebServlet("/board/write")
public class BoardWriteController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String saveDirectory = req.getServletContext().getRealPath("/uploads"); // 업로드 디렉토리의 물리적 경로 확인

		ServletContext application = getServletContext();
		int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize")); // 초기화 매개변수로 설정한 첨부 파일 최대 용량 확인

		PrintWriter out = resp.getWriter();

		try{
			MultipartRequest mr = FileUpload.uploadFile(req, saveDirectory, maxPostSize, new DefaultFileRenamePolicy()); // 파일 업로드

			BoardDTO bDto = new BoardDTO(); // 폼값을 DTO에 저장
			bDto.setWrter(mr.getParameter("nm")); // Board_write.jsp에서 입력한 게시글 작성자 값
			bDto.setTtl(mr.getParameter("ttl")); // Board_write.jsp에서 입력한 게시글 작성자 값
			bDto.setCntns(mr.getParameter("cntns"));

			SHA256 sha256 = new SHA256(); // 암호화 알고리즘 적용

			try{ // 비밀번호 sha256 암호화 시도
				String cryptogram = sha256.encrypt(mr.getParameter("pwd"));
				bDto.setPwd(cryptogram);
			}catch (NoSuchAlgorithmException e){
				e.printStackTrace();
			}

			BoardDAO dao = new BoardDAO();
			int result = dao.createBoard(bDto); // 성공 or 실패?

			Enumeration<String> files = mr.getFileNames();

			while (files.hasMoreElements()) {
				String file = files.nextElement();
				String file_name = mr.getFilesystemName(file);
				String ori_file_name = mr.getOriginalFileName(file);
				out.print("<br> 업로드된 파일명 : " + file_name);
				out.print("<br> 원본 파일명 : " + ori_file_name);
				out.print("<br><img src='upload/" + file_name + "'>");

				int recent_lst = dao.recentBord();

				FilesDTO fDto = new FilesDTO();
				fDto.setBbrd_lst(recent_lst);
				fDto.setFile_nm(file_name);
				fDto.setFile_path(saveDirectory);

				dao.uploadFile(fDto);
			}

			dao.close();

			System.out.println("게시글 입력 성공");

			if (result == 1) { // 글쓰기 성공
				resp.sendRedirect("../board/list");
			} else { // 글쓰기 실패
				resp.sendRedirect("../board/Board_write.jsp");
			}

		}catch (IOException e) {
			System.out.println("파일업로드실패");
			System.out.println(e.getMessage().isEmpty());
			JSFunction.alertLocation(resp, "첨부 파일이 제한 용량을 초과합니다.", "../board/write.jsp");
			return;
		}
	}
}