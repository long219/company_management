package comBLL.ps;

import java.util.List;

import comBllInterface.EmpBLLIntf;
import comDAL.ps.EmpDALImpl;
import property.ps.EmpProperty;

public class EmpBLLImpl implements EmpBLLIntf{
	
	//数据访问层
	private EmpDALImpl empDALImpl;
	
	public EmpBLLImpl() {
		empDALImpl = new EmpDALImpl();
	}

	@Override
	public String add(EmpProperty empProperty) {
	int index = clickiPhone(empProperty);
		if(index==-1) {
			empDALImpl.add(empProperty);
			return "增加成功!";
		}
		return "增加失败!";
	}

	@Override
	public List<EmpProperty> query(int page , int row) {
		return empDALImpl.query(page ,row);
	}

	@Override
	public String upate(EmpProperty empProperty) {
		int index = clickEmp(empProperty);
		if(index!=-1) {
			empDALImpl.update(empProperty);
			return "修改成功!";
		}
		return "修改失败!";
	}

	@Override
	public String delete(EmpProperty empProperty) {
		int index = clickEmp(empProperty);
		if(index!=-1) {
			empDALImpl.delete(empProperty);
			return "删除成功!";
		}
		return "删除失败!";
	}

	@Override
	public List<EmpProperty>  iphoneQuery(EmpProperty empP) {
		List<EmpProperty> empProperty=empDALImpl.iphoneQueyr(empP.getIpone());
		return empProperty;
	}

	@Override
	public int total(String empName, String state, String detpId) {
		
		return empDALImpl.total(empName,state,detpId);
	}
	
	/**
	 * 校验员工信息
	 * @param empProperty
	 * @return
	 */
	public int  clickEmp(EmpProperty empProperty) {
		List<EmpProperty> empP=empDALImpl.query(0,0);
		for (int i = 0; i < empP.size(); i++) {
			EmpProperty em = empP.get(i);
			if(empProperty.getId()==em.getId()) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public List<EmpProperty> queryVague(int page, int row, String empName, String state, String detpId) {
		
		return empDALImpl.queryVague(page, row, empName, state, detpId);
	}
	
	/**
	 * 校验手机号
	 * @param empProperty
	 * @return
	 */
	public int  clickiPhone(EmpProperty empProperty) {
		List<EmpProperty> empP=empDALImpl.query(0,0);
		for (int i = 0; i < empP.size(); i++) {
			EmpProperty em = empP.get(i);
			if(empProperty.getIpone().equals(em.getIpone())) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public List<EmpProperty> queryAll() {
		// TODO Auto-generated method stub
		return empDALImpl.queryAll();
	}
	
}
