package comUI.ps;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import comBLL.ps.DeptBLLImpl;
import comBLL.ps.EmpBLLImpl;
import property.ps.DataVo;
import property.ps.EmpProperty;


/**
 * Servlet implementation class empServe
 */
@WebServlet("/empServe")
public class EmpUIServe extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	
    //员工逻辑层  
	public EmpBLLImpl empBLLImpl;
	
	//部门逻辑层
	public DeptBLLImpl deptBLLImpl;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmpUIServe() {
        super();
        empBLLImpl = new EmpBLLImpl();
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
		 
		 
		 String method = request.getParameter("method");
		 String msg = "";
		 
		 if("login".equals(method)) {
			 //登录
			 msg=userRegister(request, response);	 
		 }
		 if("empQueryAll".equals(method)) {
			//查询员工信息
			msg =  empQueryAll(request, response);
		 }
		 if("empUpate".equals(method)) {
			 //修改员工
			 msg = empUpate(request, response);
		 }
		 if("empDelete".equals(method)) {
			 //员工删除 （软删除）
			 msg = empDelete(request, response);
		 }
		 if("queryDetp".equals(method)) {
			 //查询部门信息
			 msg = queryDetp(request, response);
		 }
		 if("queryVague".equals(method)) {
			 ///根据（员工姓名  ， 部门 ， 状态）模糊查询
			 msg = queryVague(request, response);
		 }
		 if("empAdd".equals(method)) {
			 //员工新增
			 msg = empAdd(request, response);
		 }
		 if("queryAll".equals(method)) {
			 
			 //查询所有员工信息
			 msg=queryAll(request, response);
		 }
		 
		 PrintWriter pw = response.getWriter();
		 pw.println(msg);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	//用户登录
	public String userRegister(HttpServletRequest request,HttpServletResponse response) {
		String  strdata = request.getParameter("empList");
		System.out.println(strdata);
		
		DataVo<EmpProperty> empVo = new DataVo<EmpProperty>();
		//将Json字符串转为 JAVA 对象
		JSON json = JSONObject.parseObject(strdata.toString());
		EmpProperty emp = JSONObject.toJavaObject(json, EmpProperty.class);
		
		List<EmpProperty> empProperty = empBLLImpl.iphoneQuery(emp);
		
		if(empProperty.size()!=0) {
			if(empProperty.get(0).getPassword().equals(emp.getPassword())) {
				//消息提示语
				empVo.setMsg("登录成功!");
				//emp信息
				empVo.setDataVo(empProperty.get(0));
				
			}else {
				//错误码
				empVo.setCode("001");
				empVo.setMsg("输入的密码有误!");
			}
		}else {
			//错误码
			empVo.setCode("002");
			empVo.setMsg("账户不存在!");
		}
				
		String  msg =JSONObject.toJSONString(empVo);
		return msg;
	}
	
	//查询员工
	public String empQueryAll(HttpServletRequest request,HttpServletResponse response) {
		DataVo<Object> empVo = new DataVo<Object>();
		//当前页
		 int page=Integer.parseInt(request.getParameter("page"));
		// 每页展示 多少条记录
		 int row = Integer.parseInt(request.getParameter("row"));
		
		//查询员工信息
		List<EmpProperty> empProperty = empBLLImpl.query(page , row);
		empVo.setDataVo(empProperty);
		
		String empName = null;
		String state = null;
		String detpId = null;
		//获取总条记录
		empVo.setTotal(empBLLImpl.total(empName,state,detpId));
		
		//将Java 对象转为JSON字符串
		return JSONObject.toJSONString(empVo);
	}
	
	//员工修改
	public String empUpate(HttpServletRequest request,HttpServletResponse response) {
		String  strdata = request.getParameter("empList");
		
		DataVo<EmpProperty> empVo = new DataVo<EmpProperty>();
		JSON json = JSONObject.parseObject(strdata.toString());
		EmpProperty emp = JSONObject.toJavaObject(json, EmpProperty.class);
		
		String msg =empBLLImpl.upate(emp);
		empVo.setMsg(msg);
		
		return JSONObject.toJSONString(empVo);
	}
	
	//员工删除 （软删除 将员工的状态改为 0）
	public String empDelete(HttpServletRequest request,HttpServletResponse response) {
		String strdata = request.getParameter("empList");
		DataVo<EmpProperty> empVo = new DataVo<EmpProperty>();
		
		JSON json = JSONObject.parseObject(strdata.toString());
		EmpProperty emp = JSONObject.toJavaObject(json, EmpProperty.class);
		String msg = empBLLImpl.delete(emp);
		if(msg!="删除成功!") {
			empVo.setMsg(msg);
			//标识删除的码
			empVo.setCode("002");
		}else {
			empVo.setMsg(msg);
		}
		
		return JSONObject.toJSONString(empVo);
	}
	
	//查询部门信息
	public String queryDetp(HttpServletRequest request,HttpServletResponse response) {
		
		DataVo<Object> empVo = new DataVo<Object>();
		//获取部门信息
		empVo.setDataVo(deptBLLImpl.queryDept());
		
		return JSONObject.toJSONString(empVo);
	}
	
	//根据（员工姓名  ， 部门 ， 状态）模糊查询
	public String queryVague(HttpServletRequest request,HttpServletResponse response) {
		int page =Integer.parseInt( request.getParameter("page"));
		int row = Integer.parseInt(request.getParameter("row"));
		String empName = request.getParameter("empName");
		String state = request.getParameter("state");
		String detpId = request.getParameter("detpId");
		
		DataVo<Object> empVo = new DataVo<Object>();

		empVo.setDataVo(empBLLImpl.queryVague(page, row, empName, state, detpId));
		
		empVo.setTotal(empBLLImpl.total(empName, state, detpId));
		
		return JSONObject.toJSONString(empVo);
	}
	
	//员工增加
	public String empAdd(HttpServletRequest request,HttpServletResponse response) {
		
		String  strdata = request.getParameter("empList");
		
		DataVo<Object> empVo = new DataVo<Object>();
		
		JSON json = JSONObject.parseObject(strdata.toString());
		EmpProperty emp = JSONObject.toJavaObject(json, EmpProperty.class);
		String msg = empBLLImpl.add(emp);
		if(msg!="增加成功!") {
			empVo.setCode("001");
			empVo.setMsg(msg);
		}else {
			empVo.setMsg(msg);
		}
		
		return JSONObject.toJSONString(empVo);
	}
	
	
	//查询所有员工
	public String queryAll(HttpServletRequest request,HttpServletResponse response) {
		
		DataVo<Object> empVo = new DataVo<Object>();
		//获取部门信息
		empVo.setDataVo(empBLLImpl.queryAll());
		
		return JSONObject.toJSONString(empVo);
	}
}
