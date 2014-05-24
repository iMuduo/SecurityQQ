package x.common.info;

import java.io.Serializable;

public class Success implements Serializable{
	private static final long serialVersionUID = -6841637176464819961L;
	private String msg;
	public Success(Object msg){
		this.msg=msg.toString();
	}
	public String toString(){
		return msg;
	}
}
