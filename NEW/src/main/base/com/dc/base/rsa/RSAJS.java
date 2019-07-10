package com.dc.base.rsa;

import javax.crypto.Cipher;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAJS {
    public static final int KEYSIZE = 1024;// 密钥大小
    public static final String RSA_ALGORITHM = "RSA";// 算法名称
    public static final String PUBLIC_KEY = "PUBLIC_KEY";//公钥名称
    public static final String PRIVATE_KEY = "PRIVATE_KEY";//私钥名称，session中存储的私钥key

    public static void main(String[] args) throws Exception {

    }

    private static void demo() throws Exception {
        Map<String, Object> keyMap = createKey();
        String param = "111111";
        RSAPublicKey publicKey = (RSAPublicKey) keyMap.get(PUBLIC_KEY);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyMap.get(PRIVATE_KEY);
        RSAPublicKey pukey = getPublicKey(publicKey.getModulus().toString(), publicKey.getPublicExponent().toString());
        RSAPrivateKey prkey = getPrivateKey(privateKey.getModulus().toString(), privateKey.getPrivateExponent().toString());
        String miwen = publicEnrypyHexStr(pukey, param);
        String mingwen = privateDecryptHexStr(prkey, miwen);
        System.out.println("原文：" + param + "\n密文：" + miwen + "\n明文：" + mingwen);
    }

    /**
     * 自动生成密钥对
     *
     * @throws Exception
     */
    public static Map<String, Object> createKey() {
        try {
            // 算法名称
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM,
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());
            SecureRandom random = new SecureRandom();
            /** 密钥大小 */
            keyPairGenerator.initialize(KEYSIZE, random);
            // 生成钥匙对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            // 得到公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            //将公钥私钥存入map中并返回
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(PUBLIC_KEY, publicKey);
            map.put(PRIVATE_KEY, privateKey);
            return map;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <h3>获取session中保存的私钥，进行16进制密文解密</h3>
     *
     * @param session
     * @param param
     * @return
     * @throws Exception
     * @author Danpil
     */
    public static String sessionDecryptHexStr(HttpSession session, String param) throws Exception {
        if (param == null || "".equals(param)) {
            return null;
        }
        // 根据模和私钥指数获取私钥
        RSAPrivateKey prkey = (RSAPrivateKey) session.getAttribute(PRIVATE_KEY);
        return privateDecryptHexStr(prkey, param);
    }


    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return byte[]
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr == null || hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return String
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 根据私钥和密文进行16进制密文解密
     *
     * @param prkey
     * @param param
     * @return
     * @throws Exception
     */
    private static String privateDecryptHexStr(Key prkey, String param) throws Exception {
        byte[] endata = parseHexStr2Byte(param);//将16进制转2进制
        byte[] data = privateDecrypt(prkey, endata);//使用私钥解密
        return new String(data);
    }

    /**
     * 解密的方法，使用私钥进行解密 privateKey 私钥 encoData 需要解密的数据
     *
     * @throws Exception
     */
    private static byte[] privateDecrypt(Key privateKey, byte[] encoData) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM, new org.bouncycastle.jce.provider.BouncyCastleProvider());
        // 设置为解密模式，用私钥解密
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 解密
        byte[] data = cipher.doFinal(encoData);
        return data;
    }

    /**
     * 使用公钥进行加密，返回16进制字符串
     *
     * @param publicKey
     * @param data
     * @return
     * @throws Exception
     */
    private static String publicEnrypyHexStr(Key publicKey, String data) throws Exception {
        byte[] enData = publicEnrypy(publicKey, data.getBytes());
        String result = parseByte2HexStr(enData);//将16进制转2进制
        return result;
    }

    /**
     * 使用公钥进行加密
     *
     * @param publicKey
     * @param data
     * @return
     * @throws Exception
     */
    private static byte[] publicEnrypy(Key publicKey, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM, new org.bouncycastle.jce.provider.BouncyCastleProvider());
        // 设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 对数据进行加密
        byte[] result = cipher.doFinal(data);
        return result;
    }

    /**
     * 使用模和指数生成RSA公钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM,
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用模和指数生成RSA私钥
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
     * /None/NoPadding】
     *
     * @param modulus  模
     * @param exponent 指数
     * @return
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM,
                    new org.bouncycastle.jce.provider.BouncyCastleProvider());
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
