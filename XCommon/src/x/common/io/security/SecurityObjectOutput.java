package x.common.io.security;

import x.common.util.SerializeHelper;

public class SecurityObjectOutput {
	private SecurityOutputFilter out;
	public SecurityObjectOutput(SecurityOutputFilter out){
		this.out=out;
	}
	
	public void write(Object obj){
		byte[] bs=SerializeHelper.object2Bytes(obj);
		out.write(bs);
	}
	
	public void close() {
		out.close();
	}
}
