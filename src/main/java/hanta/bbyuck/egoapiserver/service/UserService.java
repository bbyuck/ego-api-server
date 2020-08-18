package hanta.bbyuck.egoapiserver.service;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.exception.UnauthorizedAppVersionException;
import hanta.bbyuck.egoapiserver.exception.http.BadRequestException;
import hanta.bbyuck.egoapiserver.exception.http.UnauthorizedException;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.UserAuthRequestDto;
import hanta.bbyuck.egoapiserver.response.LoginDto;
import hanta.bbyuck.egoapiserver.util.AES256Util;
import hanta.bbyuck.egoapiserver.util.ClientVersionManager;
import hanta.bbyuck.egoapiserver.util.SHA256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final AES256Util aes256Util;
    private final SHA256Util sha256Util;
    private final ClientVersionManager clientVersionManager;

    @Override
    public UserDetails loadUserByUsername(String userGeneratedId) throws UsernameNotFoundException {
        return userRepository.find(userGeneratedId);
    }

    public LoginDto join(UserAuthRequestDto requestDto) {
        clientVersionManager.checkClientVersion(requestDto.getClientVersion());

        User findUser = null;
        try {
           findUser = userRepository.findBySnsId(requestDto.getSnsVendor(), aes256Util.encode(requestDto.getSnsId()));
        } catch (NoResultException e) {
        }

        if (findUser != null) {
            return login(requestDto);
        }

        User user = new User();
        user.createUser(requestDto.getSnsVendor(),
                aes256Util.encode(requestDto.getSnsId()),
                aes256Util.encode(requestDto.getEmail()));

        String salt = sha256Util.generateSalt();
        String userPw = sha256Util.encode(user.getSnsId(), salt);
        String userId = aes256Util.encode(requestDto.getSnsVendor().toString() + " - " + requestDto.getSnsId());

        user.assignPw(userPw);
        user.assignSalt(salt);
        user.assignId (userId);
        user.updateLoginTime();
        user.updateActiveTime();

        LoginDto loginDto = new LoginDto();
        userRepository.save(user);

        loginDto.setId(user.getGeneratedId());
        loginDto.setGeneratedId(user.getGeneratedId());
        loginDto.setGeneratedPw(user.getGeneratedPw());
        loginDto.setPrivileges(user.getPrivileges());

        return loginDto;
    }

    public LoginDto login(UserAuthRequestDto requestDto) {
        clientVersionManager.checkClientVersion(requestDto.getClientVersion());

        User user = userRepository.findBySnsId(requestDto.getSnsVendor(), aes256Util.encode(requestDto.getSnsId()));
        userRepository.updateLoginTime(user);
        LoginDto loginDto = new LoginDto();

        loginDto.setId(user.getGeneratedId());
        loginDto.setGeneratedId(user.getGeneratedId());
        loginDto.setGeneratedPw(user.getGeneratedPw());
        loginDto.setPrivileges(user.getPrivileges());

        return loginDto;
    }
}
