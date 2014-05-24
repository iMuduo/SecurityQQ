package x.common.io;

import java.io.IOException;
import java.io.InputStream;

import x.common.util.ConvertHelper;

public class LenInputStream {
	private InputStream in;

	public LenInputStream(InputStream in) {
		this.in = in;
	}

	public byte[] read() {
		byte[] data = new byte[readInt()];
		read(data);
		return data;
	}

	public int readInt() {
		byte[] n = new byte[4];
		read(n);
		return ConvertHelper.bytes2Int(n);
	}

	private int read(byte[] b) {
		try {
			int r=in.read(b);
			System.out.println("接收到密文：");
			System.out.write(b);
			return r;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void close() {
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
