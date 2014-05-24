package x.server;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.sun.nio.sctp.MessageInfo;

import x.common.info.RegisterInfo;
import x.common.info.UserInfo;
import x.common.util.CheckHelper;

public class DBService {
	private static final Map<Integer, UserInfo> map=new Hashtable<Integer, UserInfo>();
	private static final List<UserInfo> list=new Vector<>();
	private static int n=10000;
	public static int register(RegisterInfo reg){
		int id=n+1;
		if(CheckHelper.isNotNullOrEmpty(reg.name) && CheckHelper.isNotNullOrEmpty(reg.pwd))
			map.put(id, new UserInfo(id,reg.name,reg.pwd,null,false));
		else
			return 0;
		n=id;
		return n;
	}
	
	public static boolean login(UserInfo user){
		UserInfo user1=map.get(user.id);
		if(user1==null || !user1.pwd.equals(user.pwd))
			return false;
		UserInfo u=new UserInfo(user1.id, user1.name, null, null, true);
		user.name=user1.name;
		list.remove(u);
		list.add(u);
		return user1.online=true;
	} 
	
	public static void saveKey(UserInfo user){
		map.put(user.id, user);
	}
	
	public static List<UserInfo> getOnlineLists(){
		return list;
	}
	
	public static void log(MessageInfo info){
		
	}
}
