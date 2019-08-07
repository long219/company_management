package property.ps;

import java.io.Serializable;

public class EmpProperty implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int id ; //id
	private String name; //姓名
	private String ipone; //手机号
	private String password;//密码
	private String type;//类型 （(  1，经理   ， 2，员工  )）
	private String sex; //性别
	private float pay ; //薪资
	private int department_id ;//部门Id
	private String department_name; //部门名称
	private String superior;//上级领导

	private String entry_time;//入职时间
	private String state;//状态 （1.试用期 ，2.正式员工 ，3.离职 ， 0.删除）
	
	
	public EmpProperty(int id, String name, String ipone, String password, String type, String sex, float pay,
			int department_id, String entry_time, String state ,String department_name,String superior) {
		super();
		this.id = id;
		this.name = name;
		this.ipone = ipone;
		this.password = password;
		this.type = type;
		this.sex = sex;
		this.pay = pay;
		this.department_id = department_id;
		this.entry_time = entry_time;
		this.state = state;
		this.department_name = department_name;
		this.superior = superior;
	}

	public EmpProperty() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIpone() {
		return ipone;
	}
	public void setIpone(String ipone) {
		this.ipone = ipone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public float getPay() {
		return pay;
	}
	public void setPay(float pay) {
		this.pay = pay;
	}
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	public String getEntry_time() {
		return entry_time;
	}
	public void setEntry_time(String entry_time) {
		this.entry_time = entry_time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getSuperior() {
		return superior;
	}

	public void setSuperior(String superior) {
		this.superior = superior;
	}
	
	
}
