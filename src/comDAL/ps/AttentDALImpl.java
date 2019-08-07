package comDAL.ps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comDALInterface.AttdentDAlIntf;
import comUI.ps.MySQLConnection;
import property.ps.AttdentVo;

public class AttentDALImpl implements AttdentDAlIntf{

	private Connection con = null;
	
	private List <AttdentVo> list =null ;
	
	public AttentDALImpl() {
			//建立连接
			con = MySQLConnection.getConnectionInstance();
	}
	
	@Override
	public List<AttdentVo> quary(int page , int row) {
		//存储员工信息
		
		List<AttdentVo> empList = null;				
		PreparedStatement ps = null;	
		try {
			if(page ==0 && row==0) {
				ps=con.prepareStatement("SELECT e.id, d.id 'deptId', a.id 'attId', e.emp_name,d.department_name,a.day_date,a.toWork_time,a.downWork_time,a.be_late,a.leave_early , a.leave FROM emp_t e \r\n" + 
						"LEFT JOIN divisional_t d ON(e.department_id = d.id) LEFT JOIN attendance_t a ON(e.id=a.emp_id) WHERE e.state in(1,2) AND  e.type=0 ");	
			}else {
				ps=con.prepareStatement("SELECT e.id, d.id 'deptId', a.id 'attId', e.emp_name,d.department_name,a.day_date,a.toWork_time,a.downWork_time,a.be_late,a.leave_early , a.leave FROM emp_t e \r\n" + 
						"LEFT JOIN divisional_t d ON(e.department_id = d.id) LEFT JOIN attendance_t a ON(e.id=a.emp_id) WHERE e.state in(1,2) AND  e.type=0  LIMIT ?,? ");	
				ps.setInt(1, (page-1)*row);
				ps.setInt(2,row );
			}
			empList = iterates(ps.executeQuery());				
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
		return empList;
	}
	
	@Override
	public int total(String empName, String state, String detpId) {
		PreparedStatement ps = null;
		//记录总条数记录
		int total = 0;
		
		String stated = "";
		
		if ( !"".equals(state)) {
			if ("cd".equals(state)) {
				stated="AND a.be_late like 'Y'";
			}
			if ("zt".equals(state)) {
				stated="AND a.leave_early like 'Y'";
			}
			if("xj".equals(state)) {
				stated="AND a.leave like 'Y'";
			}
		}
		try {
			if(empName==null && state==null && detpId==null) {
				ps = con.prepareStatement("SELECT COUNT(1) FROM emp_t e WHERE e.state in(1,2) AND e.type = 0");
			}else {
				ps = con.prepareStatement("SELECT COUNT(1) FROM emp_t e LEFT JOIN divisional_t d ON(e.department_id = d.id) LEFT JOIN attendance_t a ON(e.id=a.emp_id) WHERE e.state in(1,2) AND  e.type=0 AND e.emp_name LIKE CONCAT('%',?,'%')  "+stated+" AND e.department_id LIKE ? ");
				
				if ("".equals(empName)) {
					ps.setString(1, "%");
				}else {
					ps.setString(1, empName);
				}
				
				if ("".equals(detpId)) {
					ps.setString(2, "%");
				}else {
					ps.setString(2, detpId);
				}
			}
			ResultSet executeQuery = ps.executeQuery();
			executeQuery.next();
			total = executeQuery.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 * 迭代考勤信息
	 * @param set
	 * @return
	 * @throws SQLException
	 */
	public List<AttdentVo> iterates(ResultSet set) throws SQLException {
		list = new ArrayList<>();
		while(set.next()) {//把数据迭代出来
			//把每一行的数据封装进属性对象中
			AttdentVo attdentVo = new AttdentVo(set.getInt("id"),set.getInt("deptId"),set.getInt("attId"),set.getString("emp_name"),set.getString("department_name"),set.getString("day_date"),
					set.getString("toWork_time"),set.getString("downWork_time"),set.getString("be_late"),set.getString("leave_early"),set.getString("leave"));
			//把属性对象add进集合里
			list.add(attdentVo);
		}
		
		return list;
	}

	@Override
	public List<AttdentVo> queryFuzzy(int page, int row, String empName, String state, String detpId) {
		
		List<AttdentVo> empList = null;	
		
		PreparedStatement ps = null;
		
		String stated = "";
		
		if ( !"".equals(state)) {
			if ("cd".equals(state)) {
				stated="AND a.be_late like 'Y'";
			}
			if ("zt".equals(state)) {
				stated="AND a.leave_early like'Y'";
			}
			if("xj".equals(state)) {
				stated="AND a.leave like 'Y'";
			}
		}
		try {
			if(empName==null && state==null && detpId==null) {
				ps=con.prepareStatement("SELECT e.id, d.id 'deptId', a.id 'attId', e.emp_name,d.department_name,a.day_date,a.toWork_time,a.downWork_time,a.be_late,a.leave_early , a.leave FROM emp_t e \r\n" + 
						"LEFT JOIN divisional_t d ON(e.department_id = d.id) LEFT JOIN attendance_t a ON(e.id=a.emp_id) WHERE e.state in(1,2) AND  e.type=0  LIMIT ?,? ");	
				ps.setInt(1, (page-1)*row);
				ps.setInt(2,row );
			}else {
				ps=con.prepareStatement("SELECT e.id, d.id 'deptId', a.id 'attId', e.emp_name,d.department_name,a.day_date,a.toWork_time,a.downWork_time,a.be_late,a.leave_early , a.leave FROM emp_t e \r\n" + 
						"LEFT JOIN divisional_t d ON(e.department_id = d.id) LEFT JOIN attendance_t a ON(e.id=a.emp_id) WHERE e.state in(1,2) AND  e.type=0 \r\n" + 
						"AND e.emp_name LIKE CONCAT('%',?,'%')  "+stated+" AND e.department_id LIKE ? LIMIT ?,? ");
				
				if ("".equals(empName)) {
					ps.setString(1, "%");
				}else {
					ps.setString(1, empName);
				}
				
				if ("".equals(detpId)) {
					ps.setString(2, "%");
				}else {
					ps.setString(2, detpId);
				}
				ps.setInt(3, (page-1)*row);
				ps.setInt(4,row );
			}
			empList = iterates(ps.executeQuery());				
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
		return empList;
	}

}
