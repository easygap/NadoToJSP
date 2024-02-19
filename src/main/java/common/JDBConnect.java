package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletContext;

public class JDBConnect {
	public Connection con; // 데이터베이스 연결
	public Statement stmt; // 인파라미터 없는 (정적) 쿼리문 실행
	public PreparedStatement psmt; // 인파라미터 있는 (동적) 쿼리문 실행
	public ResultSet rs; // 쿼리문 결과 저장
	
	DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	
	public JDBConnect(ServletContext application) {
		try { // 데이터베이스 연결
			String driver = application.getInitParameter("OracleDriver");
			Class.forName(driver);
			
			String url = application.getInitParameter("OracleURL");
			String id = application.getInitParameter("OracleId");
			String pwd = application.getInitParameter("OraclePwd");
			con = DriverManager.getConnection(url, id, pwd);
			System.out.println("*** " + date.format(now) + " DB 연결 성공 ***");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("*** DB 연결 실패 ***");
		}
	}

	public void close() { // 데이터베이스 연결 해제
		try {
			if (con != null) con.close();
			if (stmt != null) stmt.close();
			if (psmt != null) psmt.close();
			if (rs != null) rs.close();
			System.out.println("*** " + date.format(now) + " JDBC 자원 해제 완료 ***");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("*** JDBC 자원 해제 실패! ***");
		}
	}
}