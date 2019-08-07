package comDALInterface;

import java.util.List;

import property.ps.ComConfigVO;

public interface ComConfigDALIntf {
	
	/**
	 * 查询上下班时间
	 * @return
	 */
	public List<ComConfigVO> query();
	
	
	/**
	 * 修改上下班时间
	 * @param comConfigVO
	 */
	public void updateTime(ComConfigVO comConfigVO);
}
