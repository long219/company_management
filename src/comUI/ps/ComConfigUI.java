package comUI.ps;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import comBLL.ps.ComConfigBLLImpl;
import javafx.print.JobSettings;
import property.ps.ComConfigVO;
import property.ps.DataVo;
import property.ps.EmpProperty;

/**
 * Servlet implementation class ComConfigUI
 */
@WebServlet("/comConfigUI")
public class ComConfigUI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ComConfigBLLImpl comConfigBLLImpl;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComConfigUI() {
        super();
        // TODO Auto-generated constructor stub
        comConfigBLLImpl = new ComConfigBLLImpl();
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
	      
	      if ("uptateTime".equals(method)) {
	    	  //修改上下班时间
	    	  msg = uptateTime(request, response);
	      }else {
	    	  //查询考勤信息
	    	  msg = quarySysTime(request, response);
	      }
	     
	      response.getWriter().println(msg);
	      
	      
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public String quarySysTime(HttpServletRequest request, HttpServletResponse response) {
		
		  DataVo<Object> dataVo = new DataVo<Object>();
	      
	      dataVo.setDataVo(comConfigBLLImpl.query());;
	      
	      String msg =  JSONObject.toJSONString(dataVo);
	      
	      return msg;
	}
	
	public String uptateTime(HttpServletRequest request, HttpServletResponse response) {
		
		String  strdata = request.getParameter("configList");
		
		DataVo<Object> empVo = new DataVo<Object>();
		
		//将JSON字符串转为Java 对象
		JSON json = JSONObject.parseObject(strdata.toString());
		
		ComConfigVO com = JSONObject.toJavaObject(json, ComConfigVO.class);
		
		//修改上下班时间  
		String msg= comConfigBLLImpl.updateTime(com);
		
		if (!"修改成功!".equals(msg)) {
			empVo.setCode("001");
			empVo.setMsg(msg);
		}else {
			empVo.setMsg(msg);
		}
		
		return JSONObject.toJSONString(empVo);
	}
}
