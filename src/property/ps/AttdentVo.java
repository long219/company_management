package property.ps;

import java.io.Serializable;

public class AttdentVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int id ; //员工id
	
	private int deptId ; //部门Id 
	
	private int attId; //打卡Id
	
	private String emp_name ;//员工姓名
	
	private String department_name ;//部门名
	
	private String day_date ;//日期
	
	private String toWork_time;//上班时间
	
	private String downWork_time ; //下班时间
	
	private String be_late;//迟到
	
	private String leave_early;//早退
	
	private String leave; //休假、
	

	public AttdentVo(int id, int deptId, int attId, String emp_name, String department_name, String day_date,
			String toWork_time, String downWork_time, String be_late, String leave_early, String leave) {
		super();
		this.id = id;
		this.deptId = deptId;
		this.attId = attId;
		this.emp_name = emp_name;
		this.department_name = department_name;
		this.day_date = day_date;
		this.toWork_time = toWork_time;
		this.downWork_time = downWork_time;
		this.be_late = be_late;
		this.leave_early = leave_early;
		this.leave = leave;
	}

	public AttdentVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getAttId() {
		return attId;
	}

	public void setAttId(int attId) {
		this.attId = attId;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getDay_date() {
		return day_date;
	}

	public void setDay_date(String day_date) {
		this.day_date = day_date;
	}

	public String getToWork_time() {
		return toWork_time;
	}

	public void setToWork_time(String toWork_time) {
		this.toWork_time = toWork_time;
	}

	public String getDownWork_time() {
		return downWork_time;
	}

	public void setDownWork_time(String downWork_time) {
		this.downWork_time = downWork_time;
	}

	public String getBe_late() {
		return be_late;
	}

	public void setBe_late(String be_late) {
		this.be_late = be_late;
	}

	public String getLeave_early() {
		return leave_early;
	}

	public void setLeave_early(String leave_early) {
		this.leave_early = leave_early;
	}

	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}
	
	
	
}
