package comDAL.ps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import comDALInterface.ComConfigDALIntf;
import comUI.ps.MySQLConnection;
import comUI.ps.Utils;
import property.ps.ComConfigVO;

public class ComConfigDALImpl  implements ComConfigDALIntf{

	private Connection con = null;
	
	//private List <ComConfigVO> list =null ;
	
	public ComConfigDALImpl() {
			//建立连接
			con = MySQLConnection.getConnectionInstance();
	}
	
	@Override
	public List<ComConfigVO> query() {
				
		List<ComConfigVO> empList = new ArrayList<>();
		
		PreparedStatement ps = null;	
		try {
			ps = con.prepareStatement("SELECT * FROM company_config_t;");
			
			ResultSet rs = ps.executeQuery();
			
			empList=Utils.sqlToJavaObjectVO(rs, ComConfigVO.class);	 // 迭代考勤信息
			
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
	public void updateTime(ComConfigVO comConfigVO) {
		PreparedStatement ps = null;	
		try {
			ps = con.prepareStatement("UPDATE company_config_t SET toWork_time =? , downWork_time=?  WHERE id=?;");
			ps.setString(1,comConfigVO.getToWorkTime() );
			ps.setString(2, comConfigVO.getDownWorkTime());
			ps.setInt(3, comConfigVO.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
