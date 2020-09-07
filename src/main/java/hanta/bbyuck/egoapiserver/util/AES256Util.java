package hanta.bbyuck.egoapiserver.util;

import hanta.bbyuck.egoapiserver.exception.util.AES256Exception;
import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public class AES256Util {
    private static volatile AES256Util INSTANCE;

    private final static String secretKey = "10oeganhkylpphgyss1wjhyateog20bb"; //32bit
    private final static String IV = "cqoskfmwo1sxzspd"; //16bit


    //암호화
    public static String encode(String str)  {
        try {
            byte[] keyData = secretKey.getBytes();

            SecretKey secureKey = new SecretKeySpec(keyData, "AES");

            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));

            byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));

            return new String(Base64.encodeBase64(encrypted));
        } catch (NoSuchAlgorithmException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new AES256Exception();
        }
    }

    //복호화
    public static String decode(String str) {
        try {
            byte[] keyData = secretKey.getBytes();
            SecretKey secureKey = new SecretKeySpec(keyData, "AES");
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes("UTF-8")));

            byte[] byteStr = Base64.decodeBase64(str.getBytes());

            return new String(c.doFinal(byteStr), "UTF-8");
        }
        catch (NoSuchAlgorithmException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new AES256Exception();
        }
    }
}
