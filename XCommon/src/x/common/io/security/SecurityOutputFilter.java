package x.common.io.security;

import x.common.io.LenOutputStream;
import x.common.security.ISecurityFilter;

public class SecurityOutputFilter {
	private LenOutputStream out;
	private ISecurityFilter filter;
	private byte[] pwd;
	public SecurityOutputFilter(LenOutputStream out,ISecurityFilter filter,byte[] pwd){
		this.out=out;
		this.filter=filter;
		this.pwd=pwd;
	}
	
	public void write(String s){
		byte[] data=s.getBytes();
		data=filter.encrypt(data, pwd);
		out.write(data);
	}
	
	public void write(byte[] bs){
		out.write(filter.encrypt(bs, pwd));
	}
	
	public void close() {
		out.close();
	}
}
