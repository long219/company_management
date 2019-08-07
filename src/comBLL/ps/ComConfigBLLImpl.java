package comBLL.ps;

import java.util.List;

import comBllInterface.ComConfigBLLIntf;
import comDAL.ps.ComConfigDALImpl;
import property.ps.ComConfigVO;

public class ComConfigBLLImpl implements ComConfigBLLIntf{

	private ComConfigDALImpl comConfigDALImpl;
	public ComConfigBLLImpl() {
		
		comConfigDALImpl =new ComConfigDALImpl();
	}

	@Override
	public List<ComConfigVO> query() {
		return comConfigDALImpl.query();
	}

	@Override
	public String updateTime(ComConfigVO comConfigVO) {
		int index = check(comConfigVO);
		if (index!=-1) {
			comConfigDALImpl.updateTime(comConfigVO);
			return "修改成功!";
		}
		return "修改失败!";
	}
	
	/**
	 * 校验考勤信息
	 * @param comConfigVO
	 * @return
	 */
	public int check(ComConfigVO comConfigVO) {
		List<ComConfigVO> comList = query();
		
		for (int i = 0; i < comList.size(); i++) {
			ComConfigVO comVO = comList.get(i);
			if (comConfigVO.getId()==comVO.getId()) {
				return i;
			}
		}
		return -1;
	}
}
