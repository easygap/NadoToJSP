package common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnPool {
	private DataSource dataSource;

	DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
	public DBConnPool() {
		try {
			// 커넥션 풀(DateSource) 얻기
			Context iniCtx = new InitialContext();
			Context ctx = (Context) iniCtx.lookup("java:comp/env");
			dataSource = (DataSource) ctx.lookup("dbcp_myoracle");

			System.out.println(date.format(now) + " DB 커넥션 풀 연결 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("*** DB 커넥션 풀 연결 실패 ***");
		}
	}
	// 연결 해제(자원 반납)
	public void close() {
		
		
	}
}