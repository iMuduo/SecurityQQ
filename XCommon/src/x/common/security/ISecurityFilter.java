package x.common.security;

public interface ISecurityFilter {
	byte[] encrypt(byte[] data,byte[] key);
	byte[] decrypt(byte[] data,byte[] key);
}
