package top.omoms.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * @program: omom
 * @description: PBKDF2加密工具类
 * @author: yuanshuai
 * @create: 2023-11-24 12:59
 **/
public class PBKDF2Utils {

    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * 生成盐
     *
     * @return {@link String}
     */
    public static String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * 加密密码
     *
     * @param password 密码
     * @param salt     盐
     * @return {@link String}
     */
    public static String hashPassword(String password, String salt) {
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = Base64.getDecoder().decode(salt);

        try {
            PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hashedBytes = keyFactory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // 处理异常
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证密码
     *
     * @param password       密码
     * @param salt           盐
     * @param hashedPassword 哈希密码
     * @return boolean
     */
    public static boolean verifyPassword(String password, String salt, String hashedPassword) {
        String calculatedHash = hashPassword(password, salt);
        return hashedPassword.equals(calculatedHash);
    }

}