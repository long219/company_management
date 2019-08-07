package comDAL.ps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comDALInterface.EmpDAlIntf;
import comUI.ps.MySQLConnection;
import property.ps.EmpProperty;

public class EmpDALImpl implements EmpDAlIntf{

	private Connection con = null;
	
	private List <EmpProperty> list =null ;
	
	public EmpDALImpl() {
		//建立连接
		con = MySQLConnection.getConnectionInstance();
	}

	@Override
	public void add(EmpProperty empProperty) {
		PreparedStatement add = null;
		try {
			add = con.prepareStatement("insert into emp_t (emp_name,ipone,password,sex,pay,type,department_id) values(?,?,?,?,?,?,?)");
			
			//给 add赋值
			add.setString(1, empProperty.getName());
			add.setString(2, empProperty.getIpone());
			add.setString(3, empProperty.getPassword());
			add.setString(4, empProperty.getSex());	
			add.setFloat(5, empProperty.getPay());
			add.setString(6, "0");
			add.setInt(7, empProperty.getDepartment_id());
			//执行sql
			add.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(add != null) {
					add.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public List<EmpProperty> query(int page , int row) {
		//存储员工信息
		List<EmpProperty> empList = null;				
		PreparedStatement ps = null;	
		try {
			if(page ==0 && row==0) {
				ps=con.prepareStatement("SELECT\r\n" + 
						"	m.id,m.emp_name,m.ipone,m.PASSWORD,m.sex,m.pay,m.type,m.department_id,d.department_name,m.entry_time,m.state, e.emp_name 'superior'\r\n" + 
						"FROM\r\n" + 
						"	emp_t m\r\n" + 
						"	LEFT JOIN divisional_t d ON ( m.department_id = d.id ) LEFT JOIN emp_t e ON(d.superior=e.id) WHERE m.type=0 ;");	
			}else {
				ps=con.prepareStatement("SELECT\r\n" + 
						"	m.id,m.emp_name,m.ipone,m.PASSWORD,m.sex,m.pay,m.type,m.department_id,d.department_name,m.entry_time,m.state, e.emp_name 'superior'\r\n" + 
						"FROM\r\n" + 
						"	emp_t m\r\n" + 
						"	LEFT JOIN divisional_t d ON ( m.department_id = d.id ) LEFT JOIN emp_t e ON(d.superior=e.id) WHERE m.type=0 LIMIT ?,?;");	
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
		int total = 0;
		try {
			if(empName==null && state==null && detpId==null) {
				ps = con.prepareStatement("SELECT COUNT(1) FROM emp_t e WHERE e.type = 0");
			}else {
				ps = con.prepareStatement("	SELECT COUNT(1) FROM emp_t e WHERE e.type = 0 AND e.emp_name LIKE CONCAT('%',?,'%')  AND e.state LIKE ? AND e.department_id LIKE ? ");
				
				if ("".equals(empName)) {
					ps.setString(1, "%");
				}else {
					ps.setString(1, empName);
				}
				
				if ("".equals(state)) {
					ps.setString(2, "%");
				}else {
					ps.setString(2, state);
				}
				
				if ("".equals(detpId)) {
					ps.setString(3, "%");
				}else {
					ps.setString(3, detpId);
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
	
	
	
	
	@Override
	public void update(EmpProperty empProperty) {
		String sql="UPDATE emp_t SET emp_name=?,ipone=?,sex=?,pay=? ,department_id =?,state=? ,entry_time=?  WHERE id=?";
		PreparedStatement ps = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(empProperty.getEntry_time());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			
			ps= con.prepareStatement(sql);
			ps.setString(1, empProperty.getName());
			ps.setString(2, empProperty.getIpone());
			ps.setString(3, empProperty.getSex());	
			ps.setFloat(4, empProperty.getPay());
			ps.setInt(5, empProperty.getDepartment_id());
			ps.setString(6, empProperty.getState());
			ps.setObject(7, date);
			ps.setInt(8, empProperty.getId());
			//执行sql
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(EmpProperty empProperty) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("update emp_t set state=0 WHERE id=?");
			ps.setInt(1,empProperty.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 迭代员工信息
	 * @param set
	 * @return
	 * @throws SQLException
	 */
	public List<EmpProperty> iterates(ResultSet set) throws SQLException {
		list = new ArrayList<>();
		while(set.next()) {//把数据迭代出来
			//把每一行的数据封装进属性对象中
			EmpProperty	empProery=new EmpProperty(set.getInt("id"), set.getString("emp_name"),set.getString("ipone"), set.getString("password"),set.getString("type"), 
					set.getString("sex"), set.getFloat("pay"), set.getInt("department_id"), set.getDate("entry_time").toString(), 
					set.getString("state"),set.getString("department_name"),set.getString("superior"));
			
			//把属性对象add进集合里
			list.add(empProery);
		}
		
		return list;
	}

	@Override
	public List<EmpProperty> iphoneQueyr(String iPhone) {
		
		List<EmpProperty> empList = null;				
		PreparedStatement ps = null;
		list=new ArrayList<>();
		try {
			ps=con.prepareStatement("select m.id,m.emp_name, m.ipone, m.password, m.sex, m.pay, m.type, m.department_id, m.entry_time , m.state  FROM emp_t m WHERE m.ipone=?");	
			ps.setString(1, iPhone);
			//empList = iterates();
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				EmpProperty	empProery=new EmpProperty();
				empProery.setId(set.getInt(1));
				empProery.setName(set.getString(2));
				empProery.setIpone(set.getString(3));
				empProery.setPassword(set.getString(4));
				empProery.setSex(set.getString(5));
				empProery.setPay(set.getFloat(6));
				empProery.setType(set.getString(7));
				empProery.setDepartment_id(set.getInt(8));
				empProery.setEntry_time(set.getString(9));
				empProery.setState(set.getString(10));
				
				list.add(empProery);
			}
			empList=list;
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
	public List<EmpProperty> queryVague(int page, int row, String empName, String state, String detpId) {
		//存储员工信息
				List<EmpProperty> empList = null;				
				PreparedStatement ps = null;	
				try {
					if(empName==null && state==null && detpId==null) {
						ps=con.prepareStatement("SELECT\r\n" + 
								"	m.id,m.emp_name,m.ipone,m.PASSWORD,m.sex,m.pay,m.type,m.department_id,d.department_name,m.entry_time,m.state, e.emp_name 'superior'\r\n" + 
								"FROM\r\n" + 
								"	emp_t m\r\n" + 
								"	LEFT JOIN divisional_t d ON ( m.department_id = d.id ) LEFT JOIN emp_t e ON(d.superior=e.id) WHERE m.type=0 LIMIT ?,?;");
						ps.setInt(1, (page-1)*row);
						ps.setInt(2,row );
					}else {
						ps=con.prepareStatement("SELECT\r\n" + 
								"	m.id,m.emp_name,m.ipone,m.PASSWORD,m.sex,m.pay,m.type,m.department_id,d.department_name,m.entry_time,m.state, e.emp_name 'superior'\r\n" + 
								"FROM\r\n" + 
								"	emp_t m\r\n" + 
								"	LEFT JOIN divisional_t d ON ( m.department_id = d.id ) LEFT JOIN emp_t e ON(d.superior=e.id) WHERE m.type=0 \r\n" + 
								"	AND m.emp_name LIKE CONCAT('%',?,'%')  AND m.state LIKE ? AND m.department_id LIKE ? LIMIT ?,?");
						
						if ("".equals(empName)) {
							ps.setString(1, "%");
						}else {
							ps.setString(1, empName);
						}
						
						if ("".equals(state)) {
							ps.setString(2, "%");
						}else {
							ps.setString(2, state);
						}
						
						if ("".equals(detpId)) {
							ps.setString(3, "%");
						}else {
							ps.setString(3, detpId);
						}
						
						ps.setInt(4, (page-1)*row);
						ps.setInt(5,row );
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
	public List<EmpProperty> queryAll() {
		List<EmpProperty> empList = null;				
		PreparedStatement ps = null;
		list=new ArrayList<>();
		try {
			ps=con.prepareStatement("select m.id,m.emp_name, m.ipone, m.password, m.sex, m.pay, m.type, m.department_id, m.entry_time , m.state  FROM emp_t m WHERE m.state=2");	
			ResultSet set = ps.executeQuery();
			while(set.next()) {
				EmpProperty	empProery=new EmpProperty();
				empProery.setId(set.getInt(1));
				empProery.setName(set.getString(2));
				empProery.setIpone(set.getString(3));
				empProery.setPassword(set.getString(4));
				empProery.setSex(set.getString(5));
				empProery.setPay(set.getFloat(6));
				empProery.setType(set.getString(7));
				empProery.setDepartment_id(set.getInt(8));
				empProery.setEntry_time(set.getString(9));
				empProery.setState(set.getString(10));
				
				list.add(empProery);
			}
			empList=list;
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
