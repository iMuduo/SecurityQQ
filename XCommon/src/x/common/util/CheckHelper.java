package x.common.util;

public final class CheckHelper {
	private CheckHelper(){}
	public static boolean isNotNullOrEmpty(Object obj){
		return obj != null && obj.toString().length()!=0;
	}
	
	public static boolean bytesEq(byte[] b1,byte[] b2){
		if(b1==b2)
			return true;
		if(b1.length!=b2.length)
			return false;
		for(int i=0;i<b1.length;i++)
			if(b1[i]!=b2[i])
				return false;
		return true;
	}
}
