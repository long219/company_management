package comUI.ps;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import comBLL.ps.DeptBLLImpl;
import property.ps.CoPStatisticsVo;
import property.ps.DataVo;
import property.ps.DeptVo;


/**
 * Servlet implementation class DeptUIServe
 */
@WebServlet("/deptUIServe")
public class DeptUIServe extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public DeptBLLImpl deptBLLImpl;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeptUIServe() {
        super();
        // TODO Auto-generated constructor stub
        deptBLLImpl = new DeptBLLImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置服务端编码
		 response.setCharacterEncoding("UTF-8");
		 response.setHeader("Content-type", "text/html;charset=UTf-8");
	 
	   //*表示允许所有域名跨域
       response.setHeader("Access-Control-Allow-Origin", "*");
       response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
       response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
         
       String mehtod = request.getParameter("method");
       String msg = null;
       
       if("detpUpate".equals(mehtod)) {
    	   //修改
    	   msg = deptUpate(request, response);
       }else if("deptAdd".equals(mehtod)) {
    	   //增加
    	   msg = deptAdd(request, response);
       } else {
    	   //查询
    	   msg =  deptQueryAll(request, response);
       }
       
      
       
       //将信息发生给前台
       response.getWriter().println(msg);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	//查询员工部门的统计
	public String deptQueryAll(HttpServletRequest request, HttpServletResponse response) {
		
		DataVo<CoPStatisticsVo> dateVo = new DataVo<CoPStatisticsVo>();
	       
	       //查询员工统计信息
	       List<CoPStatisticsVo> queryC = deptBLLImpl.query();
	       
	       //将查询的部门信息存入  CoPStatisticsVo 对象里
	       queryC.get(0).setDeptV(deptBLLImpl.queryDept());
	       
	       dateVo.setDataVo(queryC.get(0));
	       
	       return JSONObject.toJSONString(dateVo);
	}
	
	//修改部门
	public String deptUpate(HttpServletRequest request, HttpServletResponse response) {
		
		String  strdata = request.getParameter("detpList");
		
		System.out.println(strdata);
		DataVo<Object> empVo = new DataVo<Object>();
		
		//将JSON字符串转为java 对象
		JSON json = JSONObject.parseObject(strdata.toString());
		DeptVo dep = JSONObject.toJavaObject(json, DeptVo.class);
		
		
		dep.setSuperiorId(Integer.parseInt(dep.getSuperior()));
		
		//根据部门id 修改 名字  或者 管理者
		String msg=deptBLLImpl.deptUpate(dep);
		
		System.out.println(msg);
		
		empVo.setMsg(msg);
		
		return JSONObject.toJSONString(empVo);
	}
	
	//增加部门
	public String deptAdd(HttpServletRequest request, HttpServletResponse response) {
		
		String  strdata = request.getParameter("detpList");
		
		System.out.println(strdata);
		DataVo<Object> empVo = new DataVo<Object>();
		
		//将JSON字符串转为java 对象
		JSON json = JSONObject.parseObject(strdata.toString());
		DeptVo dep = JSONObject.toJavaObject(json, DeptVo.class);
		
		dep.setSuperiorId(Integer.parseInt(dep.getSuperior()));
		
		//增加部门
		String msg=deptBLLImpl.addDept(dep);
		
		empVo.setMsg(msg);
		
		return JSONObject.toJSONString(empVo);
	}
	
}
