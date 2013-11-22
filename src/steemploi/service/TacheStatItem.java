package steemploi.service;

import org.apache.struts.action.ActionForm;


public class TacheStatItem extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5262519939155590640L;
	
	public TacheStatItem(String code, int count) {
		super();
		this.code = code;
		this.count = count;
	}
	private String code;
	private int count;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
