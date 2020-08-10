package hanta.bbyuck.egoapiserver.service;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.UserAuthRequest;
import hanta.bbyuck.egoapiserver.util.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AES256Util aes256Util = AES256Util.getInstance();

    public String join(UserAuthRequest request) {
        User user = new User();
        user.createUser(request.getSnsVendor(),
                request.getSnsId(),
                request.getEmail());

        Long userId = userRepository.save(user);

        try {
            String newAuth = aes256Util.AES_Encode(String.format("%019d", userId));
            user.assignAuth(newAuth);
        } catch (Exception e) {
            e.printStackTrace();
            return "회원가입 에러";
        }

        return user.getUserAuth();
    }

    public String login(UserAuthRequest request) {
        User user = userRepository.findBySnsId(request.getSnsId());

        if(user == null) {
            try{
                return join(request);
            } catch(Exception e) {
                e.printStackTrace();
                return "로그인 에러";
            }
        } else {
            return user.getUserAuth();
        }
    }

}
