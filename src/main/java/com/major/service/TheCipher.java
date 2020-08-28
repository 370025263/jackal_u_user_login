package com.major.service;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Component
public class TheCipher {


    public TheCipher() throws UnsupportedEncodingException {
    }

    public String encrypt(String username) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //1.生成KEY
        byte[] byteKey = "1234561234561234".getBytes("UTF-8");


        //2.转换KEY
        Key key = new SecretKeySpec(byteKey,"AES");
        //3.加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] result = cipher.doFinal(username.getBytes());
        System.out.println("加密后：" + Hex.encodeHexString(result));

        return Hex.encodeHexString(result);

    }
    public String decrypy(String token) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, DecoderException {
        //1.生成KEY
        byte[] byteKey = "1234561234561234".getBytes("UTF-8");


        //2.转换KEY
        Key key = new SecretKeySpec(byteKey,"AES");

        //4.解密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] feed=Hex.decodeHex(token.toCharArray());
        byte[] out = cipher.doFinal(feed);//在这里浪费40分钟，doFinal这个东西只接受Hex编码的byte[]，需要string（hex）变成char【】再用Hex变成byte[]；
        String nameOfUser=new String(out);
        System.out.println("解密后：" + nameOfUser);
        return nameOfUser;
    }
}
