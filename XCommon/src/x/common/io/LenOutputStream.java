package x.common.io;

import java.io.IOException;
import java.io.OutputStream;

import x.common.util.ConvertHelper;

public class LenOutputStream {
	private OutputStream out;

	public LenOutputStream(OutputStream out) {
		this.out = out;
	}

	public void write(String s) {
		write(s.getBytes());
	}

	public void write(byte[] b) {
		write(b.length);
		write0(b);
	}

	public void write(int n) {
		write0(ConvertHelper.int2Bytes(n));
	}

	public void flush() {
		try {
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void write0(byte[] b) {
		try {
			System.out.println("∑¢ÀÕ√‹Œƒ£∫");
			System.out.write(b);
			out.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
