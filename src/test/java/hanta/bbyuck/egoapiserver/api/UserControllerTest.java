package hanta.bbyuck.egoapiserver.api;

import hanta.bbyuck.egoapiserver.domain.SnsVendor;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.UserAuthRequest;
import hanta.bbyuck.egoapiserver.response.dto.UserAuthResponse;
import hanta.bbyuck.egoapiserver.util.AES256Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserControllerTest {

    @Autowired UserRepository userRepository;
    @Autowired UserController userController;
    @Autowired AES256Util aes256Util;

    @Test
    public void nums() throws Exception {
        System.out.println(String.format("%019d", 1));

        String numStr = String.format("%019d", 2144124);

        Long aLong = Long.parseLong(numStr);
        System.out.println(aLong);
    }

    @Test
    public void loginTest() throws Exception {
        // given
        String email1 = "k941026h@naver.com";
        String snsId1 = "1232443243252532";

        UserAuthRequest userAuthRequest = new UserAuthRequest();
        userAuthRequest.setEmail(email1);
        userAuthRequest.setSnsId(snsId1);
        userAuthRequest.setSnsVendor(SnsVendor.KAKAO);

        String email2 = "k941026h@naver.com";
        String snsId2 = "1232421312323252532";

        UserAuthRequest userAuthRequest2 = new UserAuthRequest();
        userAuthRequest2.setEmail(email2);
        userAuthRequest2.setSnsId(snsId2);
        userAuthRequest2.setSnsVendor(SnsVendor.KAKAO);


        // then

    }
}