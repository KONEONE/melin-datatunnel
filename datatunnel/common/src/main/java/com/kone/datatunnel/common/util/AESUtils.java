package com.kone.datatunnel.common.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtils.class);
    private static final String ENCRYPT_KEY = "a2d0a3ec6c33931f";

    /*
     * 描述:加密
     */
    public static String encrypt(String value){
        try{
            // 创建一个初始化向量
            IvParameterSpec iv = new IvParameterSpec(ENCRYPT_KEY.getBytes(StandardCharsets.UTF_8));
            // 创建一个密钥规范
            SecretKeySpec skeySpec = new SecretKeySpec(ENCRYPT_KEY.getBytes(StandardCharsets.UTF_8), "AES");

            // 创建一个AES/CBC/PKCS5Padding的加密器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            // 初始化加密器
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            // 加密数据
            byte[] encrypted = cipher.doFinal(value.getBytes());

            // 将加密后的数据转换为Base64编码的字符串
            return Base64.getUrlEncoder().encodeToString(encrypted);
        }catch (Exception e){
            LOGGER.error("AES加密失败:"+e.getMessage(), e);
        }
        return null;
    }

    public static String decrypt(String encrypted){
        try{
            // 创建一个初始化向量
            IvParameterSpec iv = new IvParameterSpec(ENCRYPT_KEY.getBytes(StandardCharsets.UTF_8));
            // 创建一个密钥规范
            SecretKeySpec skeySpec = new SecretKeySpec(ENCRYPT_KEY.getBytes(StandardCharsets.UTF_8), "AES");

            // 创建一个AES/CBC/PKCS5Padding的加密器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            // 初始化加密器
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            // 解密
            byte[] original = cipher.doFinal(Base64.getUrlDecoder().decode(encrypted));

            return new String(original);
        }catch(Exception e){
            LOGGER.error("AES解密失败:"+e.getMessage(), e);
        }
        return null;
    }
}
