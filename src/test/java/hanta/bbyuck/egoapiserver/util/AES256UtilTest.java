package hanta.bbyuck.egoapiserver.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class AES256UtilTest {
        String id = "123456989012";
        String custrnmNo = "1234213";
        String custNm = "테스트";

        @Test
        public void encDesTest() throws InvalidKeyException,
                UnsupportedEncodingException,
                NoSuchAlgorithmException,
                NoSuchPaddingException,
                InvalidAlgorithmParameterException,
                IllegalBlockSizeException,
                BadPaddingException {
            AES256Util a256 = AES256Util.getInstance();

            String enId = a256.AES_Encode(id);
            String enYyyymmdd = a256.AES_Encode(custrnmNo);
            String enCustNm = a256.AES_Encode(custNm);

            String desId = a256.AES_Decode(enId);
            String desYyyymmdd = a256.AES_Decode(enYyyymmdd);
            String desCustNm = a256.AES_Decode(enCustNm);

            Assertions.assertThat(desId).isEqualTo(id);
            Assertions.assertThat(desYyyymmdd).isEqualTo(custrnmNo);
            Assertions.assertThat(desCustNm).isEqualTo(custNm);

        }
}