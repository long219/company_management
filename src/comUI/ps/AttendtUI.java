package comUI.ps;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import comBLL.ps.AttdentBLLImpl;
import property.ps.AttdentVo;
import property.ps.DataVo;


/**
 * Servlet implementation class AttendtUI
 */
@WebServlet("/attendtUI")
public class AttendtUI extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	//考勤逻辑层
	public AttdentBLLImpl attdentBLLImpl;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendtUI() {
        super();
        // TODO Auto-generated constructor stub
        attdentBLLImpl=new AttdentBLLImpl();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//设置服务端编码
		 response.setCharacterEncoding("UTF-8");
		 response.setHeader("Content-type", "text/html;charset=UTf-8");
		 
	   //*表示允许所有域名跨域
       response.setHeader("Access-Control-Allow-Origin", "*");
       response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
       response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
       
       String method = request.getParameter("method");
       String msg = "";
       if("attQuery".equals(method)) {
    	   //查询考勤信息
    	   msg=attQuery(request, response);
       }
       if("queryFuzzy".equals(method)) {
    	   //考勤模糊查询
    	   msg = queryFuzzy(request, response);
       }
       //将请求发送给前台
      response.getWriter().println(msg);
    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	//查询考勤信息
	public String attQuery(HttpServletRequest request, HttpServletResponse response) {
		
		DataVo<Object> empVo = new DataVo<Object>();
		//当前页
		 int page=Integer.parseInt(request.getParameter("page"));
		// 每页展示 多少条记录
		 int row = Integer.parseInt(request.getParameter("row"));
		
		//查询考勤信息
		List<AttdentVo> query = attdentBLLImpl.query(page , row);
		empVo.setDataVo(query);
		
	 	String empName = null;
	 	String state = null;
	 	String detpId = null;
	 	
	 	//获取总条记录
		empVo.setTotal(attdentBLLImpl.total(empName,state,detpId));
		
		//将Java 对象转为JSON字符串
		return JSONObject.toJSONString(empVo);
	}
	
	//考勤模糊查询
	public String queryFuzzy(HttpServletRequest request, HttpServletResponse response) {
		
		int page =Integer.parseInt( request.getParameter("page"));
		int row = Integer.parseInt(request.getParameter("row"));
		String empName = request.getParameter("empName");
		String state = request.getParameter("state");
		String detpId = request.getParameter("detpId");
		
		System.out.println(""+page+" "+empName+" "+ state+"  "+ detpId);
		DataVo<Object> empVo = new DataVo<Object>();
		
		//根据姓名 部门 状态 模糊查询
		empVo.setDataVo(attdentBLLImpl.queryFuzzy(page, row, empName, state, detpId));
		
		empVo.setTotal(attdentBLLImpl.total(empName, state, detpId));
		
		return JSONObject.toJSONString(empVo);
		//return "";
	}
	
}
