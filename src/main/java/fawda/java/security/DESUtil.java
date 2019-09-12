package fawda.java.security;

import com.risen.hp.BaseRuntimeException;
import com.risen.hp.cmm.codec.binary.Hex;
import com.risen.hp.cmm.codec.digest.DigestUtils;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class DESUtil {
    public static final String KEY_ALGORITHM = "DES";
    public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5PADDING";

    public DESUtil() {
    }

    private static Key toKey(byte[] key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
        cipher.init(2, k);
        return cipher.doFinal(data);
    }

    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
        cipher.init(1, k);
        return cipher.doFinal(data);
    }

    public static byte[] randomKey() {
        KeyGenerator kg = null;

        try {
            kg = KeyGenerator.getInstance("DES");
        } catch (NoSuchAlgorithmException var2) {
            throw new BaseRuntimeException("没有DES加密算法程序实现", var2);
        }

        kg.init(56, new SecureRandom());
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    public static Key buildKey(String keyStr) {
        try {
            byte[] keyBytes = (keyStr + "$瑞成$").getBytes("UTF-8");
            return toKey(keyBytes);
        } catch (Exception var2) {
            throw new BaseRuntimeException("根据密钥字符串构建密钥对象失败", var2);
        }
    }

    public static void main(String[] args) throws Throwable {
        System.out.println("$瑞成$".getBytes("UTF-8").length);
        Key key = new SecretKeySpec("$瑞成$".getBytes("UTF-8"), "DES");
        System.out.println(new String(key.getEncoded(), "UTF-8"));
        Key key$ = new SecretKeySpec("$瑞成$".getBytes("UTF-8"), "DES");
        Key key1 = new SecretKeySpec("$瑞成1".getBytes("UTF-8"), "DES");
        Key key2 = new SecretKeySpec("$瑞成2".getBytes("UTF-8"), "DES");
        Key key$1 = toKey((new SecretKeySpec("$瑞成$1".getBytes("UTF-8"), "DES")).getEncoded());
        Key key$2 = toKey((new SecretKeySpec("$瑞成$2".getBytes("UTF-8"), "DES")).getEncoded());
        Key key$12 = toKey((new SecretKeySpec("$瑞成$12".getBytes("UTF-8"), "DES")).getEncoded());
        Cipher encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(1, key);
        Cipher cipher$ = Cipher.getInstance("DES");
        cipher$.init(1, key$);
        Cipher cipher1 = Cipher.getInstance("DES");
        cipher1.init(1, key1);
        Cipher cipher2 = Cipher.getInstance("DES");
        cipher2.init(1, key2);
        Cipher cipher$1 = Cipher.getInstance("DES");
        cipher$1.init(1, key$1);
        Cipher cipher$2 = Cipher.getInstance("DES");
        cipher$2.init(1, key$2);
        Cipher cipher$12 = Cipher.getInstance("DES");
        cipher$12.init(1, key$12);
        System.out.println(Hex.encodeHexString(cipher$.doFinal("123456".getBytes())));
        System.out.println(Hex.encodeHexString(cipher1.doFinal("123456".getBytes())));
        System.out.println(Hex.encodeHexString(cipher2.doFinal("123456".getBytes())));
        System.out.println(Hex.encodeHexString(cipher$1.doFinal("123456".getBytes())));
        System.out.println(Hex.encodeHexString(cipher$2.doFinal("123456".getBytes())));
        System.out.println(Hex.encodeHexString(cipher$12.doFinal("123456".getBytes())));
    }

    public static byte[] encodeMD5(String data) {
        return DigestUtils.md5(data);
    }

    public static String encodeMD5Hex(String data) {
        return DigestUtils.md5Hex(data);
    }
}
