package dto;

public class BoardDTO {
	// 맴버 변수 선언
	private int lst;
	private String ttl;
	private String cntns;
	private String wrter;
	private String pwd;
	private int vew;
	private java.sql.Date wr_date;
	private String yn;
	public int getLst() {
		return lst;
	}
	public void setLst(int lst) {
		this.lst = lst;
	}
	public String getTtl() {
		return ttl;
	}
	public void setTtl(String ttl) {
		this.ttl = ttl;
	}
	public String getCntns() {
		return cntns;
	}
	public void setCntns(String cntns) {
		this.cntns = cntns;
	}
	public String getWrter() {
		return wrter;
	}
	public void setWrter(String wrter) {
		this.wrter = wrter;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getVew() {
		return vew;
	}
	public void setVew(int vew) {
		this.vew = vew;
	}
	public java.sql.Date getWr_date() {
		return wr_date;
	}
	public void setWr_date(java.sql.Date wr_date) {
		this.wr_date = wr_date;
	}
	public String getYn() {
		return yn;
	}
	public void setYn(String yn) {
		this.yn = yn;
	}
}