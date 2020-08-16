package hanta.bbyuck.egoapiserver.service;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.exception.UnauthorizedAppVersionException;
import hanta.bbyuck.egoapiserver.exception.http.BadRequestException;
import hanta.bbyuck.egoapiserver.exception.http.UnauthorizedException;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.UserAuthRequestDto;
import hanta.bbyuck.egoapiserver.util.AES256Util;
import hanta.bbyuck.egoapiserver.util.ClientVersionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AES256Util aes256Util;
    private final ClientVersionManager clientVersionManager;


    public String join(UserAuthRequestDto requestDto) {
        User user = new User();
        user.createUser(requestDto.getSnsVendor(),
                aes256Util.encode(requestDto.getSnsId()),
                aes256Util.encode(requestDto.getEmail()));
        user.updateLoginTime();
        user.updateActiveTime();
        return userRepository.save(user);
    }

    public String login(UserAuthRequestDto requestDto) {
        clientVersionManager.checkClientVersion(requestDto.getClientVersion());

        try {
            User user = userRepository.findBySnsId(requestDto.getSnsVendor(), aes256Util.encode(requestDto.getSnsId()));
            userRepository.updateLoginTime(user);
            return user.getUserAuth();
        }
        catch (NoResultException e) {
            return join(requestDto);
        }
    }
}
