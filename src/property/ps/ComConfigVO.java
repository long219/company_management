package property.ps;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;


@Entity
public class ComConfigVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	private String cName;
	
	@Column(name="toWork_time")
	private String toWorkTime;
	
	@Column(name="downWork_time")
	private String downWorkTime;
	
	public ComConfigVO(int id, String cName, String toWorkTime, String downWorkTime) {
		super();
		this.id = id;
		this.cName = cName;
		this.toWorkTime = toWorkTime;
		this.downWorkTime = downWorkTime;
	}

	public ComConfigVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getToWorkTime() {
		return toWorkTime;
	}

	public void setToWorkTime(String toWorkTime) {
		this.toWorkTime = toWorkTime;
	}

	public String getDownWorkTime() {
		return downWorkTime;
	}

	public void setDownWorkTime(String downWorkTime) {
		this.downWorkTime = downWorkTime;
	}
}
