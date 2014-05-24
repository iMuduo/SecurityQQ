package x.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class SerializeHelper {
	private SerializeHelper() {
	}

	public static byte[] object2Bytes(Object obj) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.close();
			return baos.toByteArray();
		} catch (Exception e) {
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T bytes2Object(byte[] obj) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(obj);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Object r=ois.readObject();
			ois.close();
			return (T) r;
		} catch (Exception e) {
		}
		return null;
	}
}
