package hanta.bbyuck.egoapiserver.service;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.UserAuthRequestDto;
import hanta.bbyuck.egoapiserver.util.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AES256Util aes256Util;

    public String join(UserAuthRequestDto request) {
        User user = new User();
        user.createUser(request.getSnsVendor(),
                aes256Util.encode(request.getSnsId()),
                aes256Util.encode(request.getEmail()));
        user.updateLoginTime();
        user.updateActiveTime();
        return userRepository.save(user);
    }

    public String login(UserAuthRequestDto request) {
        try {
            User user = userRepository.findBySnsId(request.getSnsVendor(), aes256Util.encode(request.getSnsId()));
            userRepository.updateLoginTime(user);
            return user.getUserAuth();
        }
        catch (NoResultException e) {
            return join(request);
        }
    }
}
