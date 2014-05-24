package x.common.info;

import java.io.Serializable;

public class RegisterInfo implements Serializable{
	private static final long serialVersionUID = -8294243929896412004L;
	public String name,pwd;
	public RegisterInfo(String name,String pwd){
		this.name=name;
		this.pwd=pwd;
	}
}
