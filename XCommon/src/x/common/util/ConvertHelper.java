package x.common.util;

public final class ConvertHelper {
	private ConvertHelper() {
	}

	public static byte[] int2Bytes(int n) {
		byte[] r = new byte[4];
		for (int i = 0; i < 4; i++)
			r[i] = (byte) (n >> 8 * i & 0xFF);
		return r;
	}

	public static int bytes2Int(byte[] b) {
		int r = 0;
		for (int i = 0; i < b.length; i++)
			r += (b[i] & 0xFF) << (8 * i);
		return r;
	}
}
