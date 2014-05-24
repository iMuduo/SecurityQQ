package x.common.io.security;

import x.common.io.LenInputStream;
import x.common.security.ISecurityFilter;

public class SecurityInputFilter {
	private LenInputStream in;
	private ISecurityFilter filter;
	private byte[] key;

	public SecurityInputFilter(LenInputStream in, ISecurityFilter filter,
			byte[] key) {
		this.in = in;
		this.filter = filter;
		this.key = key;
	}

	public String readNext() {
		byte[] data = in.read();
		data = filter.decrypt(data, key);
		return new String(data);
	}

	public byte[] read() {
		return filter.decrypt(in.read(), key);
	}

	public void close() {
		in.close();
	}
}
