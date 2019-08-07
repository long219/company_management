package comDALInterface;

import java.util.List;

import property.ps.CoPStatisticsVo;
import property.ps.DeptVo;

public interface DeptDALALIntf {
	
	/*//
	public void add(CoPStatisticsVo coPStatisticsVo) */;
	
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
	 * 根据部门id修改
	 * @param deptVo
	 */
	public void  detpUpate(DeptVo deptVo) ;
	
	/**
	 * 部门增加
	 * @param deptVo
	 */
	public void  addDept(DeptVo deptVo);

}
