/**
 * Description : 게시글 공통 클래스 
 * 작성일 : 2024.02.21
 * 작성자 : ljs
 * Input Argument:
 *      1) map : 게시글 키워드 검색을 위한 변수
 * 수정이력 :
 *      1) 2024.02.21 게시글 수 count 함수 추가
 *      2) 2024.02.21 게시글 List 저장 함수 추가
 *      3) 2024.02.22 게시글 작성 함수 추가
 * 확인 사항 : 
 *      1) 
 */
package domain;

public class PassCheckVO{
	
	Integer check;

	public Integer getCheck() {
		return check;
	}

	public void setCheck(Integer check) {
		this.check = check;
	}
}