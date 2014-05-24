package x.common.util;

import java.util.Random;

public final class RandomHelper {
	private RandomHelper() {
	}
	
	public static byte[] randomKey(){
		int len=randomInt(20)+10;
		byte[] pwd=new byte[len];
		for(int i=0;i<len;i++)
			pwd[i]=(byte)randomInt(255);
		return pwd;
	}
	
	public static int randomInt(int range){
		return new Random().nextInt(range);
	}
}
