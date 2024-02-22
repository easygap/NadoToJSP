/**
 * Description : 파일 업로드(multipart/form-data 요청) 처리
 * 작성일 : 2024.02.22
 * 작성자 : ljs
 * Input Argument:
 *      1) 
 */
package utils;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;

public class FileUpload {
	public static MultipartRequest uploadFile(HttpServletRequest req, String saveDirectory, int maxPostSize) {
		try {    //파일 업로드
			return new MultipartRequest(req, saveDirectory, maxPostSize, "UTF-8");
		}catch(Exception e){    //업로드 실패
			e.printStackTrace();
			return null;
		}
	}
}