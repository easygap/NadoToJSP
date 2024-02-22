package dto;

public class FilesDTO {
	private int file_lst;
	private int bbrd_lst;
	private String file_nm;
	private String file_path;
	
	public int getFile_lst() {
		return file_lst;
	}
	public void setFile_lst(int file_lst) {
		this.file_lst = file_lst;
	}
	public int getBbrd_lst() {
		return bbrd_lst;
	}
	public void setBbrd_lst(int bbrd_lst) {
		this.bbrd_lst = bbrd_lst;
	}
	public String getFile_nm() {
		return file_nm;
	}
	public void setFile_nm(String file_nm) {
		this.file_nm = file_nm;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
}
