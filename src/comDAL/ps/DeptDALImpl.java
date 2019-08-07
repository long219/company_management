package comDAL.ps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comDALInterface.DeptDALALIntf;
import comUI.ps.MySQLConnection;
import property.ps.CoPStatisticsVo;
import property.ps.DeptVo;

public class DeptDALImpl implements DeptDALALIntf{
private Connection con = null;
	
	//存储统计员工信息
	private List <CoPStatisticsVo> list =null ;
	
	//存储部门信息
	private List<DeptVo> listDept = null;
	
	public DeptDALImpl() {
		//建立连接
		con = MySQLConnection.getConnectionInstance();
	}

	@Override
	public List<CoPStatisticsVo> query() {
		//员工统计信息
		List<CoPStatisticsVo> deptList = null;				
		PreparedStatement ps = null;	
		try {
			ps=con.prepareStatement("SELECT  AVG(e.pay) 'avgSalary' , count(d.count) 'empCount' ,SUM(e.pay) 'sumSalary' FROM  emp_t e LEFT JOIN divisional_t d ON(e.department_id = d.id) WHERE e.state in(1,2) AND  e.type=0;");			
			deptList = iterates(ps.executeQuery());				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
				try {
					if(ps != null) {						
						ps.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();				
			}
		}
		return deptList;
	}
	
	@Override
	public List<DeptVo> queryDept() {
		//员工统计信息
		List<DeptVo> deptList = null;				
		PreparedStatement ps = null;	
		try {
			ps=con.prepareStatement("SELECT d.department_name 'deptName', d.count , e.emp_name , d.id FROM divisional_t d LEFT JOIN  emp_t e ON(d.superior=e.id) WHERE e.state in(1,2) AND  e.type=0;");			
			deptList = iteratesDept(ps.executeQuery());				
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
				try {
					if(ps != null) {						
						ps.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();				
			}
		}
		return deptList;
	}
	
	/**
	 * 迭代信息
	 * @param set
	 * @return
	 * @throws SQLException
	 */
	public List<CoPStatisticsVo> iterates(ResultSet set) throws SQLException {
		list = new ArrayList<>();
		while(set.next()) {//把数据迭代出来
			//把每一行的数据封装进属性对象中
			CoPStatisticsVo	coPStatisticsVo=new CoPStatisticsVo(set.getString(1),set.getString(2),set.getString(3));
			//把属性对象add进集合里
			list.add(coPStatisticsVo);
		}
		return list;
	}
	
	/**
	 * 迭代部门信息
	 * @param set
	 * @return
	 * @throws SQLException
	 */
	public List<DeptVo> iteratesDept(ResultSet set) throws SQLException {
		listDept = new ArrayList<>();
		while(set.next()) {//把数据迭代出来
			//把每一行的数据封装进属性对象中
			DeptVo	deptVo=new DeptVo(set.getString(1),set.getString(2),set.getString(3),set.getInt(4));
			//把属性对象add进集合里
			listDept.add(deptVo);
		}
		return listDept;
	}

	@Override
	public void detpUpate(DeptVo deptVo) {
					
		PreparedStatement ps = null;	
		try {
			ps = con.prepareStatement("UPDATE divisional_t SET department_name = ? , superior=? WHERE id=?");
			ps.setString(1, deptVo.getDeptName());
			ps.setInt(2, deptVo.getSuperiorId());
			ps.setInt(3, deptVo.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addDept(DeptVo deptVo) {
		PreparedStatement ps = null;
		try {
			ps=con.prepareStatement("INSERT into divisional_t (department_name , superior ) VALUES (?,?)");
			ps.setString(1,deptVo.getDeptName() );
			ps.setInt(2, deptVo.getSuperiorId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
}
