package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.FileUpload;

@WebServlet("/board/download")
public class DownloadController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 매개변수 받기
		String filenm = req.getParameter("filename");	// 원본 파일명
		String idx = req.getParameter("idx");		// 게시물 일련번호
		
		// 파일 다운로드
		FileUpload.download(req, resp, "/uploads", filenm);
	}

}
