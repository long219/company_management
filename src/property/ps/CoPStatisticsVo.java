package property.ps;

import java.io.Serializable;
import java.util.List;

public class CoPStatisticsVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String avgSalary; //平均薪资
	private String empCount;//员工总数
	private String sumSalary;//总薪资
	
	//部门信息
	private List<DeptVo> deptV;
	
	
	public CoPStatisticsVo(String avgSalary, String empCount, String sumSalary) {
		super();
		this.avgSalary = avgSalary;
		this.empCount = empCount;
		this.sumSalary = sumSalary;
	}
	public CoPStatisticsVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getAvgSalary() {
		return avgSalary;
	}
	public void setAvgSalary(String avgSalary) {
		this.avgSalary = avgSalary;
	}
	public String getEmpCount() {
		return empCount;
	}
	public void setEmpCount(String empCount) {
		this.empCount = empCount;
	}
	public String getSumSalary() {
		return sumSalary;
	}
	public void setSumSalary(String sumSalary) {
		this.sumSalary = sumSalary;
	}
	
	public List<DeptVo> getDeptV() {
		return deptV;
	}
	public void setDeptV(List<DeptVo> deptV) {
		this.deptV = deptV;
	}
	
}
