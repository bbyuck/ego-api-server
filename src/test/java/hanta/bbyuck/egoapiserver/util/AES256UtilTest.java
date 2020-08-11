package hanta.bbyuck.egoapiserver.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

@SpringBootTest
class AES256UtilTest {
        String id = "123456989012";
        String custrnmNo = "1234213";
        String custNm = "테스트";
        @Autowired AES256Util a256;

        @Test
        public void encDesTest() {

            String enId = a256.encode(id);
            String enYyyymmdd = a256.encode(custrnmNo);
            String enCustNm = a256.encode(custNm);

            String desId = a256.decode(enId);
            String desYyyymmdd = a256.decode(enYyyymmdd);
            String desCustNm = a256.decode(enCustNm);

            Assertions.assertThat(desId).isEqualTo(id);
            Assertions.assertThat(desYyyymmdd).isEqualTo(custrnmNo);
            Assertions.assertThat(desCustNm).isEqualTo(custNm);

        }
}