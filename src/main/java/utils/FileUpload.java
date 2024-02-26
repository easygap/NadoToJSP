/**
 * Description : 파일 업로드(multipart/form-data 요청) 처리
 * 작성일 : 2024.02.22
 * 작성자 : ljs
 * Input Argument:
 *      1) 
 */
package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

public class FileUpload {
	public static MultipartRequest uploadFile(HttpServletRequest req, String saveDirectory, int maxPostSize,
			FileRenamePolicy policy) {
		try { // 파일 업로드
			return new MultipartRequest(req, saveDirectory, maxPostSize, "UTF-8", policy);
		} catch (Exception e) { // 업로드 실패
			e.printStackTrace();
			return null;
		}
	}

	public static void download(HttpServletRequest req, HttpServletResponse resp, String directory, String filenm) throws IOException {    //명시한 파일을 찾아 다운로드합니다.
		String sDirectory = req.getServletContext().getRealPath(directory);
		try{
			File file = new File(sDirectory, filenm);    //파일 탐색
			InputStream iStream = new FileInputStream(file);    //입력 스트림 생성

			// 한글 파일명 깨짐 방지
			String client = req.getHeader("User-Agent");
			if (client.indexOf("WOW64") == -1) {
				filenm = new String(filenm.getBytes("UTF-8"), "ISO-8859-1");
			} else {
				filenm = new String(filenm.getBytes("KSC5601"), "ISO-8859-1");
			}

			// 파일 다운로드용 응답 헤더 설정
			resp.reset();
			resp.setContentType("application.octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=\"" + filenm + "\"");
			resp.setHeader("Content-Length", "" + file.length());

			// out.clear(); // 출력 스트림 초기화

			// response 내장 객체로부터 새로운 출력 스트림 생성
			OutputStream oStream = resp.getOutputStream();

			// 풀력 스트림에 파일 내용 출력
			byte b[] = new byte[(int) file.length()];
			int readBuffer = 0;
			while ((readBuffer = iStream.read(b)) > 0) {
				oStream.write(b, 0, readBuffer);
			}

			// 입/출력 스트림 닫음
			iStream.close();
			oStream.close();
		} catch (FileNotFoundException e) {
			System.out.println("예외가 발생하였습니다.");
			e.printStackTrace();
		}
	}

	// 지정한 위치의 파일을 삭제합니다.
	public static void deleteFile(HttpServletRequest req, String directory, String filename) {
		String sDirectory = req.getServletContext().getRealPath(directory);
		File file = new File(sDirectory + File.separator + filename);
		if (file.exists()) {
			file.delete();
		}
	}
}