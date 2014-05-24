package x.common.info;

import java.io.Serializable;

public class UserInfo implements Serializable ,Comparable<UserInfo>{
	private static final long serialVersionUID = 7018160696657310560L;
	public String name,pwd;
	public int id;
	public byte[] key;
	public boolean online;
	public UserInfo(int id,String userName,String pwd,byte[] key,boolean online){
		this.online=online;
		this.key=key;
		this.name=userName;
		this.id=id;
		this.pwd=pwd;
	}
	
	@Override
	public int compareTo(UserInfo o) {
		return this.id-o.id;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof UserInfo))
			return false;
		if(((UserInfo)o).id!=this.id)
			return false;
		return true;
	}
}
