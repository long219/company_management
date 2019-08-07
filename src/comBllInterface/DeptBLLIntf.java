package comBllInterface;

import java.util.List;

import property.ps.CoPStatisticsVo;
import property.ps.DeptVo;

public interface DeptBLLIntf {
	
	/**
	 * 查询员工统计信息信息
	 * @return
	 */
	public List<CoPStatisticsVo> query();
	
	/**
	 * 查询部门信息
	 * @return
	 */
	public List<DeptVo> queryDept();
	
	/**
	 * 部门修改
	 * @return
	 */
	public String deptUpate(DeptVo deptVo);
	
	/**
	 * 部门增加
	 */
	public String addDept(DeptVo deptVo);
}
