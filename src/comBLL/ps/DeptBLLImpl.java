package comBLL.ps;

import java.util.List;

import comBllInterface.DeptBLLIntf;
import comDAL.ps.DeptDALImpl;
import property.ps.CoPStatisticsVo;
import property.ps.DeptVo;

public class DeptBLLImpl implements DeptBLLIntf{
	
	//初始化数据访问层
	private DeptDALImpl deptDALImpl;
	
	public DeptBLLImpl() {
		deptDALImpl = new DeptDALImpl();
	}

	@Override
	public List<CoPStatisticsVo> query() {
		
		return deptDALImpl.query();
	}

	@Override
	public List<DeptVo> queryDept() {
		
		return deptDALImpl.queryDept();
	}

	@Override
	public String deptUpate(DeptVo deptVo) {
		// TODO Auto-generated method stub
		deptDALImpl.detpUpate(deptVo);
		
		return "修改成功!";
	}

	@Override
	public String addDept(DeptVo deptVo) {
		int index = checkDeptName(deptVo);
		if(index==-1) {
			deptDALImpl.addDept(deptVo);
			return "新增成功!";
		}
		return "新增失败!";
	}
	
	/**
	 * 校验部门名
	 * @param deptVo
	 * @return
	 */
	public int checkDeptName(DeptVo deptVo) {
		List<DeptVo> queryDept = deptDALImpl.queryDept();
		for (int i = 0; i < queryDept.size(); i++) {
			DeptVo  deptV = queryDept.get(i);
			if(deptVo.getDeptName() !=null && deptVo.getDeptName().equals(deptV.getDeptName())) {
				return i;
			}
		}
		return -1;
	}
	
}
