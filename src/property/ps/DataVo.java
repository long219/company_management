package property.ps;

import java.io.Serializable;

public class DataVo<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String msg; //消息提示
	
	private String code; //错误码
	
	private T dataVo; //存储对象
	
	private int total; //总员工条数记录

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getDataVo() {
		return dataVo;
	}

	public void setDataVo(T dataVo) {
		this.dataVo = dataVo;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	

}
