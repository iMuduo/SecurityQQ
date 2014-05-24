package x.common.io.security;

import x.common.util.SerializeHelper;

public class SecurityObjectInput {
	private SecurityInputFilter in;
	public SecurityObjectInput(SecurityInputFilter in){
		this.in=in;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T read(){
		byte[] bs=in.read();
		return (T) SerializeHelper.bytes2Object(bs);
	}
	
	public void close() {
		in.close();
	}
}
