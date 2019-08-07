package comDALInterface;

import java.util.List;

import property.ps.EmpProperty;

//数据访问层接口
public interface EmpDAlIntf {
	
	/**
	 * 员工增加
	 * @param empProperty
	 */
	public void add(EmpProperty empProperty);
	
	/**
	 * 查询所有员工信息
	 * @return
	 */
	public List<EmpProperty> query(int page , int row);
	
	/**
	 * 查询所有员工信息
	 * @return
	 */
	public List<EmpProperty> queryAll();
	
	/**
	 * 根据员工ID删除 (软删除 将员工的状态改为 0 )
	 * @param empId
	 */
	public void delete(EmpProperty empProperty);
	
	/**
	 * 修改员工信息
	 * @param empProperty
	 */
	public void update(EmpProperty empProperty);
	
	/**
	 * 根据用户手机号查询
	 */
	public List<EmpProperty> iphoneQueyr(String iPhone);
	
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
