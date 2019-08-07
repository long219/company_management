package property.ps;

import java.io.Serializable;

public class DeptVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id; //部门ID
	private String deptName ;  //部门名称
	private String deptNumber; //部门总人数
	private String superior;  //部门管理者
	private int superiorId; //部门管理者ID
	
	public DeptVo(String deptName, String deptNumber, String superior,int id) {
		super();
		this.deptName = deptName;
		this.deptNumber = deptNumber;
		this.superior = superior;
		this.id = id;
		
	}
	
	public DeptVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getDeptNumber() {
		return deptNumber;
	}
	public void setDeptNumber(String deptNumber) {
		this.deptNumber = deptNumber;
	}
	public String getSuperior() {
		return superior;
	}
	public void setSuperior(String superior) {
		this.superior = superior;
	}
	public int getSuperiorId() {
		return superiorId;
	}
	public void setSuperiorId(int superiorId) {
		this.superiorId = superiorId;
	}
	

}
