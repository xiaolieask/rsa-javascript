package com.chenjiamin.utils.security;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Random;
import java.util.UUID;

/**
 * 安全键值、秘钥工具
 *
 * @author 作者:肖烈 E-mail: 760956257@qq.com
 * @version 创建时间：2018年5月24日 上午10:35:50
 */
public class SecurityKeyUtil {

    private static final String SALT = "asklie5588";


    /**
     * 隐藏手机号
     *
     * @param phone 13888888888
     * @return 138****8888
     */
    public static String hiddenPhone(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }


    /**
     * 隐藏邮箱
     *
     * @param email aaa@bbb.com
     * @return ***@bbb.com
     */
    public static String hiddenEmail(String email) {
        int index = email.lastIndexOf('@');
        String name = email.substring(0, index);
        if (name.length() > 4)
            name = name.replaceAll("(\\w*})\\w{4}", "$1****");
        else
            name = "****";
        return name + email.substring(index);
    }

    /**
     * 静态数组
     */
    private static char[] chars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    /**
     * 生成access_token
     *
     * @return token
     */
    public static String CreateAccessToken(String str) {

        String bytesString = "";
        Random ran = new Random();
        try{
            byte[] bytes = Base64.encodeBase64((UUID.randomUUID().toString() + str).getBytes());
            bytesString = new String(bytes, "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        bytesString = bytesString.substring(0,bytesString.length()-5);

        StringBuilder sb = new StringBuilder();
        sb.append(bytesString);
        for (int i = 0; i < 512-bytesString.length(); i++) {
            int n = ran.nextInt(chars.length);// 取随机字符索引
            sb.append(chars[n]);
        }
        return sb.toString();
    }


    /*
     * MD5
     * :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
     *::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
     */


    /**
     * MD5加密
     *
     * @param password 待加密内容
     * @return 加密结果
     */
    public static String encode(String password) {
        password = password + SALT;
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        char[] charArray = password.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte m : md5Bytes) {
            int val = ((int) m) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static void main(String[] args) {
        System.out.println(encode("1"));
    }

    /*
     * RSA
     * :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
     *::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
     */

    /**
     * 生成密钥对
     *
     * @return KeyPair
     * @throws Exception 异常
     */
    public static KeyPair generateKeyPair() throws Exception {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
                    new BouncyCastleProvider());
            final int KEY_SIZE = 256;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());
            return keyPairGen.generateKeyPair();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 将PrivateKey转换为String
     *
     * @param privateKey PrivateKey
     * @return privateKeyString
     */
    public static String PrivateKeyToString(PrivateKey privateKey) {
        return SecurityKeyUtil.toHexString(privateKey.getEncoded());
    }

    /**
     * 通过String得到privateKey
     *
     * @param privateKeyString privateKeyString
     * @return PrivateKey
     * @throws Exception 异常
     */
    public static PrivateKey getPrivateKey(String privateKeyString) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", new BouncyCastleProvider());
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(SecurityKeyUtil.toBytes(privateKeyString));
        return keyFactory.generatePrivate(priPKCS8);
    }

    /**
     * RSA解密
     *
     * @param key   私钥PrivateKey
     * @param enStr 待解密内容
     * @return 解密结果
     * @throws Exception 异常
     */
    public static String decrypt(PrivateKey key, String enStr) throws Exception {
        try {
            //byte[] raw11 = new BigInteger(enStr, 16).toByteArray();
            byte[] raw = hexStringToBytes(enStr);
            Cipher cipher = Cipher.getInstance("RSA",
                    new BouncyCastleProvider());
            cipher.init(Cipher.DECRYPT_MODE, key);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;
            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            byte[] de_result = bout.toByteArray();
            StringBuilder sb = new StringBuilder();
            sb.append(new String(de_result));
            return sb.reverse().toString();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || "".equals(hexString)) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    private static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (byte i : b) {
            sb.append(HEXCHAR[(i & 0xf0) >>> 4]);
            sb.append(HEXCHAR[i & 0x0f]);
        }
        return sb.toString();
    }

    private static byte[] toBytes(String s) {
        byte[] bytes;
        bytes = new byte[s.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
                    16);
        }
        return bytes;
    }

    private static char[] HEXCHAR = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

}
