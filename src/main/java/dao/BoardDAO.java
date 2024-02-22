/**
 * Description : 게시글 공통 클래스 
 *             암/복호화 함수 및 기타 공통 함수 정의
 * 작성일 : 2024.02.21
 * 작성자 : ljs
 * Input Argument:
 *      1) map : 게시글 키워드 검색을 위한 변수
 * 수정이력 :
 *      1) 2024.02.21 게시글 수 count 함수 추가
 *      2) 2024.02.21 게시글 List 저장 함수 추가
 * 확인 사항 : 
 *      1) 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import common.DBConnPool;
import dto.BoardDTO;

public class BoardDAO extends DBConnPool{
	DataSource dataSource;
	Connection con;
	Statement stmt;
	PreparedStatement psmt;
	ResultSet rs;
	DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();

	/**
	 * Description : 데이터베이스 연결
	 */
	public BoardDAO(){
		try{
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/dbcp_myoracle");
			con = dataSource.getConnection();
			System.out.println("BoardDAO DB 연동 성공");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("*** BoardDAO DB 연동 중 예외 발생 ***");
		}
	}
	
	/** 
	 * Description : 검색 조건에 맞는 게시글 수
	 * Parameter 
	 *     @param map
	 * Return
	 *     @return
	 */
	public int selectCount(Map<String, Object> map){
		int totalCount = 0;    //게시물 수를 담을 변수
		String query = "SELECT COUNT(*) FROM bbrd";
		if(map.get("searchWord") != null){
			query += " WHERE " + map.get("searchField") + " " + " LIKE '%" + map.get("searchWord") + "%'";
		}
		
		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			totalCount = rs.getInt(1);    //첫 번재 컬럼 값
			System.out.println("게시판 게시글 " + totalCount + "개 로드 성공");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("**게시글 카운트 중 예외 발생**");
		}
		return totalCount;    //게시글 갯수를 서블릿으로 반환
	}
	
	/** 
	 * Description : 게시글 목록
	 * Parameter
	 *     @param map
	 * Return
	 *     @return
	 */
	public List<BoardDTO> selectList(Map<String, Object> map){
		List<BoardDTO> bbs = new Vector<BoardDTO>();    //결과(게시물 목록)를 담을 변수
		String query = "SELECT * FROM bbrd ";
		if(map.get("searchWord") != null){
			query += " WHERE bbrd_" + map.get("searchField") + " " + " Like '%" + map.get("searchWord") + "%' ";
		}
		query += " ORDER BY bbrd_lst DESC ";
		
		try{
			stmt = con.createStatement();    //쿼리문 생성
			rs = stmt.executeQuery(query);    //쿼리 실행
			
			while(rs.next()) {    //결과를 순회하며 게시글 하나의 내용을 DTO에 저장
				BoardDTO dto = new BoardDTO();
				
				dto.setLst(rs.getInt("bbrd_lst"));
				dto.setTtl(rs.getString("bbrd_ttl"));
				dto.setCntns(rs.getString("bbrd_cntns"));
				dto.setWrter(rs.getString("bbrd_wrter"));
				dto.setPwd(rs.getString("bbrd_pwd"));
				dto.setVew(rs.getInt("bbrd_vew"));
				dto.setWr_date(rs.getDate("bbrd_wr_date"));
				dto.setYn(rs.getString("bbrd_yn"));
				
				bbs.add(dto);
			}
		}catch(Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		return bbs;
	}
	
	/**
	 * Description : 게시글 작성
	 */
	public void createBoard(BoardDTO dto) {
		String query = "INSERT INTO BBRD(BBRD_LST, BBRD_TTL, BBRD_CNTNS, BBRD_WRTER, BBRD_PWD "
				     + ", BBRD_VEW, BBRD_WR_DATE)";
	}
	

	/**
	 * Description : 데이터베이스 연결 해제
	 */
	public void close(){
		DBConnPool dbConnPool = new DBConnPool();
		try{
			if (rs != null)
				rs.close();
			if (psmt != null)
				psmt.close();
			if (con != null)
				con.close();
			if (stmt != null)
				stmt.close();
			System.out.println("게시판 자원 해제 성공");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("*** 게시판 자원 해제 중 예외 발생 ***");
		}
		dbConnPool.close();
	}
}