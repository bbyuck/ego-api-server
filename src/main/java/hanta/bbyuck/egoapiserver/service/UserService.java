package hanta.bbyuck.egoapiserver.service;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.UserAuthRequestDto;
import hanta.bbyuck.egoapiserver.request.UserGameSelectDto;
import hanta.bbyuck.egoapiserver.response.LoginDto;
import hanta.bbyuck.egoapiserver.response.UserInfoResponseDto;
import hanta.bbyuck.egoapiserver.util.AES256Util;
import hanta.bbyuck.egoapiserver.util.ClientVersionManager;
import hanta.bbyuck.egoapiserver.util.SHA256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

import static hanta.bbyuck.egoapiserver.util.ClientVersionManager.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userGeneratedId) throws UsernameNotFoundException {
        return userRepository.find(userGeneratedId);
    }

    public LoginDto join(UserAuthRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());
        User findUser = null;
        try {
           findUser = userRepository.findBySnsId(requestDto.getSnsVendor(), AES256Util.encode(requestDto.getSnsId()));
        } catch (NoResultException e) {
        }

        if (findUser != null) {
            return login(requestDto);
        }

        User user = new User();
        user.createUser(requestDto.getSnsVendor(),
                AES256Util.encode(requestDto.getSnsId()),
                AES256Util.encode(requestDto.getEmail()));

        String salt = SHA256Util.generateSalt();
        String userPw = SHA256Util.encode(user.getSnsId(), salt);
        String userId = AES256Util.encode(requestDto.getSnsVendor().toString() + " - " + requestDto.getSnsId());

        user.assignPw(userPw);
        user.assignSalt(salt);
        user.assignId (userId);
        user.updateLoginTime();
        user.updateActiveTime();
        user.updateFcmToken(requestDto.getFcmToken());
        userRepository.save(user);

        LoginDto loginDto = new LoginDto();

        loginDto.setId(user.getGeneratedId());
        loginDto.setGeneratedId(user.getGeneratedId());
        loginDto.setGeneratedPw(user.getGeneratedPw());
        loginDto.setPrivileges(user.getPrivileges());

        return loginDto;
    }

    public LoginDto login(UserAuthRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        User user = userRepository.findBySnsId(requestDto.getSnsVendor(), AES256Util.encode(requestDto.getSnsId()));
        user.updateLoginTime();
        user.updateFcmToken(requestDto.getFcmToken());

        LoginDto loginDto = new LoginDto();

        loginDto.setId(user.getGeneratedId());
        loginDto.setGeneratedId(user.getGeneratedId());
        loginDto.setGeneratedPw(user.getGeneratedPw());
        loginDto.setPrivileges(user.getPrivileges());

        return loginDto;
    }

    public UserInfoResponseDto findLastPlayGame(UserGameSelectDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());
        User user = userRepository.find(requestDto.getGeneratedId());
        UserInfoResponseDto responseDto = new UserInfoResponseDto();
        responseDto.setGame(user.getGame());
        return responseDto;
    }

    public void refreshToken(UserAuthRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());

        User user = userRepository.findBySnsId(requestDto.getSnsVendor(), AES256Util.encode(requestDto.getSnsId()));
        user.updateFcmToken(requestDto.getFcmToken());
    }

    public void updateGame(UserGameSelectDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());
        User user = userRepository.find(requestDto.getGeneratedId());
        userRepository.updateGame(user, requestDto.getGame());
    }
}
