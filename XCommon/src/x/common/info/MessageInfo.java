package x.common.info;

import java.io.Serializable;

public class MessageInfo implements Serializable{
	private static final long serialVersionUID = 7599488069072365083L;
	public String from,to,time,msg;
	public int fromid,toid;
	
	public MessageInfo(int fromid, int toid,String from,String to,String time,String msg){
		this.from=from;
		this.to=to;
		this.time=time;
		this.msg=msg;
		this.fromid=fromid;
		this.toid=toid;
	}
}
