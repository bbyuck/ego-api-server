package hanta.bbyuck.egoapiserver.util;

import hanta.bbyuck.egoapiserver.exception.lol.UserAuthenticationException;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Slf4j
public class SHA256Util {
    private static volatile SHA256Util INSTANCE;

    public static SHA256Util getInstance() {
        if (INSTANCE == null) {
            synchronized (SHA256Util.class) {
                if (INSTANCE == null)
                    INSTANCE = new SHA256Util();
            }
        }
        return INSTANCE;
    }

    private SHA256Util() {}

    public String encode(String input, String salt) {
        return encode(input, salt.getBytes());
    }

    public String encode(String input, byte[] salt) {
        try {
            String result = "";
            byte[] a = input.getBytes();
            byte[] bytes = new byte[a.length + salt.length];

            System.arraycopy(a, 0, bytes, 0, a.length);
            System.arraycopy(salt, 0, bytes, a.length, salt.length);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(bytes);

            byte[] byteData = md.digest();

            StringBuffer sb = new StringBuffer();
            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xFF) + 256, 16).substring(1));
            }

            result = sb.toString();
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new UserAuthenticationException("유저 인증 실패 : 잘못된 키 입력");
        }
    }

    public String generateSalt() {
        Random random = new Random();

        byte[] salt = new byte[8];
        random.nextBytes(salt);

        StringBuffer sb = new StringBuffer();
        for (byte b : salt) {
            // byte 값을 Hex 값으로 바꾸기.
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
