package hanta.bbyuck.egoapiserver.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SHA256UtilTest {
    SHA256Util sha256Util = new SHA256Util();

    @Test
    public void cnt() throws Exception {
        // given
        String encode = sha256Util.encode(String.format("%019d", 1), "23af42a4ebad3561");
        String expected = "94bc008adaed22e3b6f9f830c893fc6a36041ab31b947146e37f51059edc4c3c";
        // when
        Assertions.assertEquals(encode, expected);
        // then
    }
}