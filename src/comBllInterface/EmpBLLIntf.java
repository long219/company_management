package comBllInterface;

import java.util.List;

import property.ps.EmpProperty;

public interface EmpBLLIntf {
	/**
	 * 员工新增
	 * @param empProperty
	 * @return
	 */
	public String add(EmpProperty empProperty);
	
	/**
	 * 查询所有员工
	 * @return
	 */
	public List<EmpProperty> query(int page , int row);
	
	/**
	 * 查询所有员工信息
	 * @return
	 */
	public List<EmpProperty> queryAll();
	
	/**
	 * 员工修改
	 * @param empProperty
	 * @return
	 */
	public String upate(EmpProperty empProperty);
	
	/**
	 * 根据Id 删除员工
	 * @param empId
	 * @return
	 */
	public String delete(EmpProperty empProperty);
	
	/**
	 * 根据员工手机查询
	 * @param iphone
	 * @return
	 */
	public  List<EmpProperty> iphoneQuery(EmpProperty empProperty);
	
	/**
	 * 总条数记录
	 * @return
	 */
	public int total(String empName, String state, String detpId);
	
	/**
	 * 根据（员工姓名  ， 部门 ， 状态）模糊查询
	 * @return
	 */
	public List<EmpProperty> queryVague(int page ,int row , String empName,String state , String detpId );
}
