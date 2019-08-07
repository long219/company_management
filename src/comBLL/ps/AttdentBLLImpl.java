package comBLL.ps;

import java.util.List;

import comBllInterface.AttdentBLLIntf;
import comDAL.ps.AttentDALImpl;
import property.ps.AttdentVo;

public class AttdentBLLImpl implements AttdentBLLIntf{
	
	//考勤存储层
	private AttentDALImpl attentDALImpl;
	
	public AttdentBLLImpl() {
		attentDALImpl = new AttentDALImpl();
	}
	
	@Override
	public List<AttdentVo> query(int page, int row) {
		// TODO Auto-generated method stub
		return attentDALImpl.quary(page, row);
	}

	@Override
	public int total(String empName, String state, String detpId) {
		// TODO Auto-generated method stub
		return attentDALImpl.total(empName, state, detpId);
	}

	@Override
	public List<AttdentVo> queryFuzzy(int page, int row, String empName, String state, String detpId) {
		// TODO Auto-generated method stub
		return attentDALImpl.queryFuzzy(page, row, empName, state, detpId);
	}

}
