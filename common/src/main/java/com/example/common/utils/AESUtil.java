package com.example.common.utils;

import android.text.TextUtils;
import android.util.Base64;

import java.security.InvalidParameterException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES encryption and decryption
 * 只支持utf8编码,支持base64和16进制
 * 结果可以在线验证：http://tool.chacuo.net/cryptaes
 * <p>
 * 1. key's length >= 16
 * 2. iv's length > 16
 * 3. "transformation": AES/CBC/PKCS5Padding
 * 4. iv=16(bytes) length=128(bits)
 * 5. iv=24(bytes) length=192(bits)
 * 6. iv=32(bytes) length=256(bits)
 * <p>
 */

public class AESUtil {
    //格式为: AES/加密模式/填充
    //CBC需要偏移iv,因此不能为空
    public static final String TRANSFORMATION_CBC_PKCS5PADDING = "AES/CBC/PKCS5PADDING";
    public static final String TRANSFORMATION_CBC_PKCS7PADDING = "AES/CBC/PKCS7PADDING";
    public static final String TRANSFORMATION_CBC_ZEROBYTEPADDING = "AES/CBC/ZEROBYTEPADDING";

    //ECB不需要偏移iv，因此iv可为null
    public static final String TRANSFORMATION_ECB_PKCS5PADDING = "AES/ECB/PKCS5PADDING";
    public static final String TRANSFORMATION_ECB_PKCS7PADDING = "AES/ECB/PKCS7PADDING";
    public static final String TRANSFORMATION_ECB_ZEROBYTEPADDING = "AES/ECB/ZEROBYTEPADDING";

    private AESUtil() {
    }


    /**
     * 加密并返回base64字符串
     *
     * @param data
     * @param key
     * @param iv
     * @param transformation
     * @return
     * @throws Exception
     */
    public static String encryptAndBase64Encode(String data, String key, String iv, String transformation) throws Exception {
        if (TextUtils.isEmpty(data)) {
            throw (new InvalidParameterException("data is empty"));
        }
        if (TextUtils.isEmpty(key)) {
            throw (new InvalidParameterException("key is empty"));
        }
        byte[] ivBytes = null;
        if (!TextUtils.isEmpty(iv)) {
            ivBytes = iv.getBytes();
        }
        return new String(Base64.encode(encryptCore(data.getBytes(), key.getBytes(), ivBytes, transformation), Base64.DEFAULT));
    }

    /**
     * 加密并返回16进制字符串（大写）
     *
     * @param data
     * @param key
     * @param iv
     * @param transformation
     * @return
     * @throws Exception
     */
    public static String encryptAndHexEncode(String data, String key, String iv, String transformation) throws Exception {
        if (TextUtils.isEmpty(data)) {
            throw (new InvalidParameterException("data is empty"));
        }
        if (TextUtils.isEmpty(key)) {
            throw (new InvalidParameterException("key is empty"));
        }
        byte[] ivBytes = null;
        if (!TextUtils.isEmpty(iv)) {
            ivBytes = iv.getBytes();
        }
        return byte2hex(encryptCore(data.getBytes(), key.getBytes(), ivBytes, transformation));
    }


    /**
     * 核心加密过程
     *
     * @param data
     * @param key
     * @param iv
     * @param transformation
     * @return
     * @throws Exception
     */
    public static byte[] encryptCore(byte[] data, byte[] key, byte[] iv, String transformation) throws Exception {
        if (data == null || data.length == 0) {
            throw (new InvalidParameterException("data is empty"));
        }
        if (key == null || key.length == 0) {
            throw (new InvalidParameterException("key is empty"));
        }
        if (transformation == null || transformation.length() == 0) {
            throw (new InvalidParameterException("transformation is empty"));
        }
        if (transformation.toUpperCase().startsWith("AES/CBC")
                && (iv == null || iv.length == 0)) {
            throw (new InvalidParameterException("CBC need iv,but iv is empty"));
        }

        SecretKey newKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(transformation);
        if (iv != null && iv.length != 0) {
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, newKey);
        }
        return cipher.doFinal(data);
    }


    /**
     * 解密base64密文
     *
     * @param data
     * @param key
     * @param iv
     * @param transformation
     * @return
     * @throws Exception
     */
    public static String decryptBase64EncodeData(String data, String key, String iv, String transformation) throws Exception {
        if (TextUtils.isEmpty(data)) {
            throw (new InvalidParameterException("data is empty"));
        }
        if (TextUtils.isEmpty(key)) {
            throw (new InvalidParameterException("key is empty"));
        }
        byte[] ivBytes = null;
        if (!TextUtils.isEmpty(iv)) {
            ivBytes = iv.getBytes();
        }
        return new String(decryptCore(Base64.decode(data.getBytes(), Base64.DEFAULT), key.getBytes(), ivBytes, transformation));

    }

    /**
     * 解密16进制密文
     *
     * @param data
     * @param key
     * @param iv
     * @param transformation
     * @return
     * @throws Exception
     */
    public static String decryptHexEncodeData(String data, String key, String iv, String transformation) throws Exception {
        if (TextUtils.isEmpty(data)) {
            throw (new InvalidParameterException("data is empty"));
        }
        if (TextUtils.isEmpty(key)) {
            throw (new InvalidParameterException("key is empty"));
        }
        byte[] ivBytes = null;
        if (!TextUtils.isEmpty(iv)) {
            ivBytes = iv.getBytes();
        }
        return new String(decryptCore(hex2byte(data), key.getBytes(), ivBytes, transformation));
    }


    /**
     * 核心解密过程
     *
     * @param data
     * @param key
     * @param iv
     * @param transformation
     * @return
     * @throws Exception
     */
    public static byte[] decryptCore(byte[] data, byte[] key, byte[] iv, String transformation) throws Exception {

        if (data == null || data.length == 0) {
            throw (new InvalidParameterException("data is empty"));
        }
        if (key == null || key.length == 0) {
            throw (new InvalidParameterException("key is empty"));
        }
        if (transformation == null || transformation.length() == 0) {
            throw (new InvalidParameterException("transformation is empty"));
        }
        if (transformation.toUpperCase().startsWith("AES/CBC")
                && (iv == null || iv.length == 0)) {
            throw (new InvalidParameterException("CBC need iv,but iv is empty"));
        }

        SecretKeySpec newKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance(transformation);
        if (iv != null && iv.length != 0) {
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, newKey);
        }
        return cipher.doFinal(data);
    }


    /**
     * 字节数组转成16进制字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) { // 一个字节的数，
        StringBuffer sb = new StringBuffer(b.length * 2);
        String tmp = "";
        for (int n = 0; n < b.length; n++) {
            // 整数转成十六进制表示
            tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase(); // 转成大写
    }

    /**
     * 将hex字符串转换成字节数组
     *
     * @param inputString
     * @return
     */
    public static byte[] hex2byte(String inputString) {
        if (inputString == null || inputString.length() < 2) {
            return new byte[0];
        }
        inputString = inputString.toLowerCase();
        int l = inputString.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = inputString.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }


    public static void test() throws Exception {
        String plainText = "hello world";//明文
        String key = "abcdefghijklmnop";//秘钥
        String iv = "abcdefghijklmnop";//偏移量
        String resultBase64;
        String resultHex;
        String result;
        //结果可以在线验证：http://tool.chacuo.net/cryptaes
        System.out.println("---------TRANSFORMATION_CBC_PKCS5PADDING------");
        resultBase64 = encryptAndBase64Encode(plainText, key, iv, TRANSFORMATION_CBC_PKCS5PADDING);
        System.out.println("resultBase64=" + resultBase64);//1dGGFwB4VKhqD6jGCIwT7Q==
        resultHex = encryptAndHexEncode(plainText, key, iv, TRANSFORMATION_CBC_PKCS5PADDING);
        System.out.println("resultHex=" + resultHex);//D5D18617007854A86A0FA8C6088C13ED
        result = decryptBase64EncodeData(resultBase64, key, iv, TRANSFORMATION_CBC_PKCS5PADDING);
        System.out.println("result=" + result);
        result = decryptHexEncodeData(resultHex, key, iv, TRANSFORMATION_CBC_PKCS5PADDING);
        System.out.println("result=" + result);
        System.out.println("---------TRANSFORMATION_CBC_PKCS7PADDING------");
        resultBase64 = encryptAndBase64Encode(plainText, key, iv, TRANSFORMATION_CBC_PKCS7PADDING);
        System.out.println("resultBase64=" + resultBase64);//1dGGFwB4VKhqD6jGCIwT7Q==
        resultHex = encryptAndHexEncode(plainText, key, iv, TRANSFORMATION_CBC_PKCS7PADDING);
        System.out.println("resultHex=" + resultHex);//D5D18617007854A86A0FA8C6088C13ED
        result = decryptBase64EncodeData(resultBase64, key, iv, TRANSFORMATION_CBC_PKCS7PADDING);
        System.out.println("result=" + result);
        result = decryptHexEncodeData(resultHex, key, iv, TRANSFORMATION_CBC_PKCS7PADDING);
        System.out.println("result=" + result);
        System.out.println("---------TRANSFORMATION_CBC_ZEROBYTEPADDING------");
        resultBase64 = encryptAndBase64Encode(plainText, key, iv, TRANSFORMATION_CBC_ZEROBYTEPADDING);
        System.out.println("resultBase64=" + resultBase64);//SSZOxRJUoLPfhLIu1LYE9Q==
        resultHex = encryptAndHexEncode(plainText, key, iv, TRANSFORMATION_CBC_ZEROBYTEPADDING);
        System.out.println("resultHex=" + resultHex);//49264EC51254A0B3DF84B22ED4B604F5
        result = decryptBase64EncodeData(resultBase64, key, iv, TRANSFORMATION_CBC_ZEROBYTEPADDING);
        System.out.println("result=" + result);
        result = decryptHexEncodeData(resultHex, key, iv, TRANSFORMATION_CBC_ZEROBYTEPADDING);
        System.out.println("result=" + result);


        System.out.println("---------TRANSFORMATION_ECB_PKCS5PADDING------");
        resultBase64 = encryptAndBase64Encode(plainText, key, null, TRANSFORMATION_ECB_PKCS5PADDING);
        System.out.println("resultBase64=" + resultBase64);//f7sSBDV0N6MOpRJLpSJL0w==
        resultHex = encryptAndHexEncode(plainText, key, null, TRANSFORMATION_ECB_PKCS5PADDING);
        System.out.println("resultHex=" + resultHex);//7FBB1204357437A30EA5124BA5224BD3
        result = decryptBase64EncodeData(resultBase64, key, null, TRANSFORMATION_ECB_PKCS5PADDING);
        System.out.println("result=" + result);
        result = decryptHexEncodeData(resultHex, key, null, TRANSFORMATION_ECB_PKCS5PADDING);
        System.out.println("result=" + result);
        System.out.println("---------TRANSFORMATION_ECB_PKCS7PADDING------");
        resultBase64 = encryptAndBase64Encode(plainText, key, null, TRANSFORMATION_ECB_PKCS7PADDING);
        System.out.println("resultBase64=" + resultBase64);//f7sSBDV0N6MOpRJLpSJL0w==
        resultHex = encryptAndHexEncode(plainText, key, null, TRANSFORMATION_ECB_PKCS7PADDING);
        System.out.println("resultHex=" + resultHex);//7FBB1204357437A30EA5124BA5224BD3
        result = decryptBase64EncodeData(resultBase64, key, null, TRANSFORMATION_ECB_PKCS7PADDING);
        System.out.println("result=" + result);
        result = decryptHexEncodeData(resultHex, key, null, TRANSFORMATION_ECB_PKCS7PADDING);
        System.out.println("result=" + result);
        System.out.println("---------TRANSFORMATION_ECB_ZEROBYTEPADDING------");
        resultBase64 = encryptAndBase64Encode(plainText, key, null, TRANSFORMATION_ECB_ZEROBYTEPADDING);
        System.out.println("resultBase64=" + resultBase64);//ICR1dyuaFTqAi+sRzoZ+uQ==
        resultHex = encryptAndHexEncode(plainText, key, null, TRANSFORMATION_ECB_ZEROBYTEPADDING);
        System.out.println("resultHex=" + resultHex);//202475772B9A153A808BEB11CE867EB9
        result = decryptBase64EncodeData(resultBase64, key, null, TRANSFORMATION_ECB_ZEROBYTEPADDING);
        System.out.println("result=" + result);
        result = decryptHexEncodeData(resultHex, key, null, TRANSFORMATION_ECB_ZEROBYTEPADDING);
        System.out.println("result=" + result);

    }

}