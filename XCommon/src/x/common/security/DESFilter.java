package x.common.security;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESFilter implements ISecurityFilter {

	@Override
	public byte[] encrypt(byte[] data, byte[] key) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(key);
			// ����һ���ܳ׹�����Ȼ��������DESKeySpecת����
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher����ʵ����ɼ��ܲ���
			Cipher cipher = Cipher.getInstance("DES");
			// ���ܳ׳�ʼ��Cipher����
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// ���ڣ���ȡ���ݲ�����
			// ��ʽִ�м��ܲ���
			return cipher.doFinal(data);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public byte[] decrypt(byte[] data, byte[] key) {
		try {
			// DES�㷨Ҫ����һ�������ε������Դ
			SecureRandom random = new SecureRandom();
			// ����һ��DESKeySpec����
			DESKeySpec desKey = new DESKeySpec(key);
			// ����һ���ܳ׹���
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// ��DESKeySpec����ת����SecretKey����
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher����ʵ����ɽ��ܲ���
			Cipher cipher = Cipher.getInstance("DES");
			// ���ܳ׳�ʼ��Cipher����
			cipher.init(Cipher.DECRYPT_MODE, securekey, random);
			// ������ʼ���ܲ���
			return cipher.doFinal(data);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
