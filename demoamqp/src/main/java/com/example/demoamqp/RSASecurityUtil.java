package com.example.demoamqp;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class RSASecurityUtil {
	/** 指定加密算法为RSA */
	private static final String ALGORITHM = "RSA";
	/** 密钥长度，用来初始化 */
	private static final int KEYSIZE = 1024;
	/** 指定公钥 */
	private static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWwyeHWhtFNMONZ+bZBLvCkE41nzKU1nQf5B/otrTc8nxEFgWZfyPy1W1WW8/rXDXNJUy2+ShlRaIV1QOUZkvD8Q8C828e4O7s4ifsVkPsNs0Jb5eSa224FdqAzs2gQjF5DMcXDeT3jXM37oZ+4geiq+Vap+c9Xr4kLHOVckTV7wIDAQAB";
	/** 指定私钥 */
	private static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJbDJ4daG0U0w41n5tkEu8KQTjWfMpTWdB/kH+i2tNzyfEQWBZl/I/LVbVZbz+tcNc0lTLb5KGVFohXVA5RmS8PxDwLzbx7g7uziJ+xWQ+w2zQlvl5JrbbgV2oDOzaBCMXkMxxcN5PeNczfuhn7iB6Kr5Vqn5z1eviQsc5VyRNXvAgMBAAECgYEAhXcq7VZFwhSd8fdDlGfuWKYrtqjCvKTFlN39qaTlpJw+in/8YRD/ICD1dHg/Lxsovf4BVhJpQo5MmYtEagHNm8F83Uorr8ad8mPLcS9/bqezAdXaP8uR51ZxV+paLJ7xSxGy8FtYQy3MwBzF9BgKgax+X23XEE7YOIFsoMkru8ECQQDZ5hcyP3kY55ut4B/bM4rFP5uHxrcCWv3ZsKqIxDqdPvAjYY8QhwY8qDrGSaEMt7deSqyuv90D+/gs6xc3XcH/AkEAsR/NKcPcczAOnPGjL2NgrfyLhtkZ3VwID1AC/87kkTKhLzpDQrN4gxGCNiU60hjoBBEJ7V7XA4pJd0XEeg0MEQJAU7WCKcqaogewFf8pHdPSNu61uFaNzfjY1r149iu0fN5F8MG07VuB+OGEGtMEbNdTOYUki8mnhbVR5Se3doytTwJAJZjBfMcFSTH/OBMWd12ZzPSZTUNR+Smq0E1EhbYN0EA19IdidYsQ3old4U5fwO7meM6BE/CYpTi6RWX6wYl5cQJBAIFcLHhPMvwrcSFzAEskM1RtkCJbYWJolReXg6yyjYiRTSMf1yOGemrr3YL8TGpyAUTA1Tbt146f0OCCAAbk5mc=";
	
	/**
	 * 公钥加密方法
	 * 
	 * @param source 源数据
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String source) throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(PUBLIC_KEY));
		Key publicKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
		/** 得到Cipher对象来实现对源数据的RSA加密 */
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] b = source.getBytes();
		/** 执行加密操作 */
		byte[] b1 = cipher.doFinal(b);
		return Base64.getEncoder().encodeToString(b1);
	}
	
	
	/**
	 * 私钥加密方法
	 * 
	 * @param source 源数据
	 * @return
	 * @throws Exception
	 */
	public static String encryptPriKey(String source) throws Exception {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(PRIVATE_KEY));
		Key privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
		/** 得到Cipher对象来实现对源数据的RSA加密 */
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		byte[] b = source.getBytes();
		/** 执行加密操作 */
		byte[] b1 = cipher.doFinal(b);
		return Base64.getEncoder().encodeToString(b1);
	}
 
	/**
	 * 私钥解密算法
	 * 
	 * @param cryptograph 密文
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String cryptograph) throws Exception {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(PRIVATE_KEY));
		Key privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
		/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] b1 = Base64.getDecoder().decode(cryptograph);
 
		/** 执行解密操作 */
		byte[] b = cipher.doFinal(b1);
		return new String(b);
	}
	
	/**
	 * 公钥解密算法
	 * 
	 * @param cryptograph 密文
	 * @return
	 * @throws Exception
	 */
	public static String decryptPubKey(String cryptograph) throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(PUBLIC_KEY));
		Key publicKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
		/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		byte[] b1 = Base64.getDecoder().decode(cryptograph);
 
		/** 执行解密操作 */
		byte[] b = cipher.doFinal(b1);
		return new String(b);
	}
 
	public static void main(String[] args) throws Exception {
		String source = "2019-05-07";// 要加密的字符串
		System.out.println("准备用公钥加密的字符串为：" + source);
 
		String cryptograph = encrypt(source);// 生成的密文
		System.out.print("用公钥加密后的结果为:" + cryptograph);
		System.out.println();
 
		String target = decrypt(cryptograph);// 解密密文
		System.out.println("用私钥解密后的字符串为：" + target);
		System.out.println();
		
		
		System.out.println("准备用私钥加密的字符串为：" + "2019-01-10");
 
		String cryptograph2 = encryptPriKey("2019-01-10");// 生成的密文
		System.out.print("用私钥加密后的结果为:" + cryptograph2);
		System.out.println();
 
		String target2 = decryptPubKey(cryptograph2);// 解密密文
		System.out.println("用公钥解密后的字符串为：" + target2);
		System.out.println();
	}
}
