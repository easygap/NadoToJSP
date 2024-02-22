package servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import dto.BoardDTO;
import utils.FileUpload;
import utils.JSFunction;

@WebServlet("/board/write")
public class BoardWriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String saveDirectory = req.getServletContext().getRealPath("/Uploads");    //업로드 디렉토리의 물리적 경로 확인
		
		ServletContext application = getServletContext();
		int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize"));    //초기화 매개변수로 설정한 첨부 파일 최대 용량 확인
		
		MultipartRequest mr = FileUpload.uploadFile(req, saveDirectory, maxPostSize);    //파일 업로드
		if(mr == null) {    //파일 업로드 실패
			JSFunction.alertLocation(resp, "첨부 파일이 제한 용량을 초과합니다.", "../board/write.jsp");
			return;
		}
		
		BoardDTO dto = new BoardDTO();    //폼값을 DTO에 저장
		dto.setWrter(mr.getParameter("nm"));
		dto.setTtl(mr.getParameter("ttl"));
		dto.setCntns(mr.getParameter("content"));
		dto.setPwd(mr.getParameter("pass"));
		
		String fileName = mr.getFilesystemName("ofile");    //원본 파일명과 저장된 파일 이름 설정
		if(fileName != null) {
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
			String ext = fileName.substring(fileName.lastIndexOf("."));
			String newFileName = now + ext;
			
			File oldFile = new File(saveDirectory + File.separator + fileName);
			File newFile = new File(saveDirectory + File.separator + newFileName);
			oldFile.renameTo(newFile);    //파일명 변경
			
		}
	}
}