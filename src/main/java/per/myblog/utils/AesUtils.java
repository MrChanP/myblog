package per.myblog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class AesUtils {

	private static final Logger log = LoggerFactory.getLogger(AesUtils.class);
	private static String key = "myblogtruefirst1";
	private static String iv = "showmyblogfirst1";

	/**
	 * 加密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data) {
		if (data != null) {
			try {
				Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
				int blockSize = cipher.getBlockSize();
				byte[] dataBytes = data.getBytes("UTF-8");
				int plaintextLength = dataBytes.length;
				if (plaintextLength % blockSize != 0) {
					plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
				}
				byte[] plaintext = new byte[plaintextLength];
				System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
				SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
				IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
				cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
				byte[] encrypted = cipher.doFinal(plaintext);
				return new sun.misc.BASE64Encoder().encode(encrypted);
			} catch (Exception e) {
				log.error(data + " - 加密data失败 - " + e);
			}
		}
		return null;
	}

	/**
	 * 解密
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String desEncrypt(String data) {
		if (data != null && !data.equals("nul")) {
			try {
				byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);
				Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
				SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
				IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
				cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "UTF-8");
				return originalString.trim();
			} catch (Exception e) {
				log.error(data + " - 解密失败：" + e);
			}
		}
		return null;
	}
}
