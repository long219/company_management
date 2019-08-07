package comDALInterface;

import java.util.List;

import property.ps.AttdentVo;

public interface AttdentDAlIntf {
	
	/**
	 * 查询考勤信息
	 * @return
	 */
	public List<AttdentVo> quary(int page,int row);
	
	/**
	 * 总条记录
	 * @param empName
	 * @param state
	 * @param detpId
	 * @return
	 */
	public int total(String empName, String state, String detpId);
	
	/**
	 * 根据 根据（员工姓名 ，状态， 部门  ）模糊查询
	 * @param empName
	 * @param state
	 * @param detpId
	 * @return
	 */
	public List<AttdentVo> queryFuzzy(int page,int row, String empName, String state, String detpId);
	
}
