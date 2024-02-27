/**
 * Description : 게시글 공통 클래스 
 * 작성일 : 2024.02.21
 * 작성자 : ljs
 * Input Argument:
 *      1) map : 게시글 키워드 검색을 위한 변수
 *      2) lst : BBRD 테이블 조회를 위한 게시글 번호 변수
 *      3) dto : GETTER, SETTER를 통해 게시글 정보를 DB와 교환하는 객체 변수
 * 수정이력 :
 *      1) 2024.02.21 게시글 수 count 함수, List 저장 함수 추가
 *      2) 2024.02.22 글 작성, 최신 게시글 번호 불러오기, 파일 업로드 함수 추가
 *      3) 2024.02.23 글 상세보기, 조회수 증가 함수 추가
 *      4) 2024.02.26 게시글 작성 함수 추가
 *      5) 2024.02.27 게시글 비밀번호 검증, 글 삭제 함수 추가
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
import dto.FilesDTO;

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
			query += " WHERE bbrd_" + map.get("searchField") + " " + " LIKE '%" + map.get("searchWord") + "%'";
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
		String query = "SELECT * FROM ( "
					 + "SELECT Tb.*, ROWNUM rNum FROM ( "
					 + "SELECT * FROM bbrd ";
		if(map.get("searchWord") != null){    // 검색 조건이 있다면 WHERE절로 추가
			query += " WHERE bbrd_" + map.get("searchField") + " Like '%" + map.get("searchWord") + "%' ";
		}
		query += " ORDER BY bbrd_lst DESC "
			   + ") Tb "
			   + ") "
			   + "WHERE rNum BETWEEN ? AND ?";    // 게시물 구간은 인파라미터로
		try{
			psmt = con.prepareStatement(query);    // 동적 쿼리문 생성
			psmt.setString(1, map.get("start").toString());    // 인파라미터 설정
			psmt.setString(2, map.get("end").toString());
			rs = psmt.executeQuery();    //쿼리 실행
			
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
	public int createBoard(BoardDTO dto) {
		int result = 0;
		String query = "INSERT INTO BBRD(BBRD_LST, BBRD_TTL, BBRD_CNTNS, BBRD_WRTER, BBRD_PWD "
				     + ", BBRD_WR_DATE) "
				     + "VALUES(bbrd_seq.NEXTVAL, ?, ?, ?, ?, sysdate)";
		try{
			psmt = con.prepareStatement(query);    //동적 쿼리문 생성
			psmt.setString(1, dto.getTtl());
			psmt.setString(2, dto.getCntns());
			psmt.setString(3, dto.getWrter());
			psmt.setString(4, dto.getPwd());
			psmt.executeUpdate();    //Commit!
			
			result = 1;
		} catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Description : 가장 최근 업로드한 게시글 번호
	 */
	public int recentBord() {
		int lst = 0;
		String query = "SELECT BBRD_LST FROM BBRD WHERE bbrd_wr_date = (SELECT MAX(bbrd_wr_date) FROM BBRD)";
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			rs.next();
			lst = rs.getInt(1);    //첫 번재 컬럼 값
		}catch(Exception e) {
			e.printStackTrace();
		}
		return lst;
	}
	
	/**
	 * Description : 파일 업로드
	 */
	public void uploadFile(FilesDTO dto) {
		String query = "INSERT INTO FILES(FILE_LST, BBRD_LST, FILE_NM, FILE_PATH) "
			     + "VALUES(file_seq.NEXTVAL, ?, ?, ?)";
	try{
		psmt = con.prepareStatement(query);    //동적 쿼리문 생성
		psmt.setInt(1, dto.getBbrd_lst());
		psmt.setString(2, dto.getFile_nm());
		psmt.setString(3, dto.getFile_path());
		psmt.executeUpdate();    //Commit!
		} catch(Exception e){
			e.printStackTrace();
			}
	}
	
	/**
	 * Description : 게시글 상세보기
	 */
	public BoardDTO selectView(String lst){    //게시글 번호에 해당하는 게시글을 DTO에 담아 반환합니다.
		BoardDTO dto = new BoardDTO();    //DTO 객체 생성
		String query = "SELECT b.BBRD_TTL, b.BBRD_CNTNS, f.file_nm "
					 + "FROM bbrd b LEFT JOIN files f "
					 + "ON b.bbrd_lst=f.bbrd_lst "
					 + "WHERE b.bbrd_lst = ?";
		try{
			psmt = con.prepareStatement(query);    //쿼리문 준비
			psmt.setString(1, lst);    //인파라미터 설정
			rs = psmt.executeQuery();    //쿼리문 실행
			
			if(rs.next()){    //결과를 DTO 객체에 저장
				dto.setTtl(rs.getString(1));
				dto.setCntns(rs.getString(2));
				dto.setFile_nm(rs.getString(3));
			}
		}catch(Exception e){
			System.out.println("**게시글 상세보기 중 예외 발생**");
			e.printStackTrace();
		}
		return dto;
	}
	
	/**
	 * Description : 조회수 1 증가
	 */
	public void updateVisitCount(String lst){    //게시물 No.값에 해당하는 게시물의 조회수를 1 증가.
		String query = "UPDATE bbrd SET "
					 + "bbrd_vew = bbrd_vew + 1 "
					 + "WHERE bbrd_lst = ?";
		try{
			psmt = con.prepareStatement(query);
			psmt.setString(1, lst);
			psmt.executeQuery();
		}catch(Exception e){
			System.out.println("**게시글 조회수 증가 중 예외 발생**");
			e.printStackTrace();
		}
	}
	
	/**
	 * Description : 게시글 수정/삭제 시 비밀번호 검증
	 */
	public Integer checkPassword(BoardDTO dto){
		Integer check = 0;    //비밀번호 일치 여부 리턴 값, 0은 비밀번호 불일치, 1은 비밀번호 일치
		String query = "SELECT bbrd_ttl, bbrd_cntns "
					 + "FROM bbrd WHERE bbrd_lst = ? "
					 + "AND (bbrd_pwd IS NULL OR bbrd_pwd = ?) ";
		try{
			psmt = con.prepareStatement(query);
			psmt.setInt(1, dto.getLst());
			psmt.setString(2, dto.getPwd());
			rs = psmt.executeQuery();
			
			if(rs.next()){    //DB조회 결과 확인
				check = 1;    //값이 조회된다면 비밀번호가 일치한 경우, check 값을 1로 변경
			}
			
		}catch(Exception e){
			System.out.println("**비밀번호 검증 중  예외 발생**");
			e.printStackTrace();
		}
		return check;
	}
	
	/**
	 * Description : 게시글 삭제
	 */
	public void deleteBoard(BoardDTO dto){
		String query = "UPDATE BBRD SET BBRD_YN = 'N' WHERE BBRD_LST = ?";
		try{
			psmt = con.prepareStatement(query);
			psmt.setInt(1, dto.getLst());
			psmt.executeQuery();
		}catch(Exception e){
			System.out.println("**게시글 삭제 중 예외 발생**");
			e.printStackTrace();
		}
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