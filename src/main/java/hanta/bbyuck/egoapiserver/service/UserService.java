package hanta.bbyuck.egoapiserver.service;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.UserAuthRequestDto;
import hanta.bbyuck.egoapiserver.request.UserGameSelectDto;
import hanta.bbyuck.egoapiserver.request.UserStatusRequestDto;
import hanta.bbyuck.egoapiserver.response.LoginDto;
import hanta.bbyuck.egoapiserver.response.UserInfoResponseDto;
import hanta.bbyuck.egoapiserver.response.UserStatusResponseDto;
import hanta.bbyuck.egoapiserver.util.AES256Util;
import hanta.bbyuck.egoapiserver.util.ClientVersionManager;
import hanta.bbyuck.egoapiserver.util.SHA256Util;
import hanta.bbyuck.egoapiserver.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.NoResultException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static hanta.bbyuck.egoapiserver.util.ClientVersionManager.*;
import static hanta.bbyuck.egoapiserver.util.ServiceUtil.*;

/*
 * <pre>
 * Copyright (c) 2020 HANTA
 * All rights reserved.
 *
 * This software is the proprietary information of HANTA
 * </pre>
 *
 * @ author 강혁(bbyuck) (k941026h@naver.com)
 * @ since  2020. 01. 01
 *
 * @History
 * <pre>
 * -----------------------------------------------------
 * 2020.01.01
 * bbyuck (k941026h@naver.com) 최초작성
 * -----------------------------------------------------
 * </pre>
 */

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @PostConstruct
    private void setURL_Encoding_Map() {
        URL_Encoding_Map = new HashMap<>();
        URL_Encoding_Map.put(' ', "%20");
        URL_Encoding_Map.put('!', "%21");
        URL_Encoding_Map.put('"', "%22");
        URL_Encoding_Map.put('#', "%23");
        URL_Encoding_Map.put('$', "%24");
        URL_Encoding_Map.put('%', "%25");
        URL_Encoding_Map.put('&', "%26");
        URL_Encoding_Map.put('\'', "%27");
        URL_Encoding_Map.put('(', "%28");
        URL_Encoding_Map.put(')', "%29");
        URL_Encoding_Map.put('*', "%2A");
        URL_Encoding_Map.put('+', "%2B");
        URL_Encoding_Map.put(',', "%2C");
        URL_Encoding_Map.put('-', "%2D");
        URL_Encoding_Map.put('.', "%2E");
        URL_Encoding_Map.put('/', "%2F");
    }

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

        loginDto.setGeneratedId(user.getGeneratedId());
        loginDto.setGeneratedIdForGet(encodeForGet(user.getGeneratedId()));
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

        loginDto.setGeneratedId(user.getGeneratedId());
        loginDto.setGeneratedIdForGet(encodeForGet(user.getGeneratedId()));
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

    public UserStatusResponseDto findStatus(UserStatusRequestDto requestDto) {
        checkClientVersion(requestDto.getClientVersion());
        User apiCaller = userRepository.find(requestDto.getGeneratedId());
        userRepository.updateLastActiveTime(apiCaller);
        UserStatusResponseDto responseDto = new UserStatusResponseDto();
        responseDto.setLastActiveTime(LocalDateTime.now());
        responseDto.setStatus(apiCaller.getStatus());

        return responseDto;
    }

    private static String encodeForGet(String id) {
        String answer = "";

        for (int i = 0; i < id.length(); i++){
            char c = id.charAt(i);

            String enc = URL_Encoding_Map.get(c);

            if (enc == null) answer += c;
            else answer += enc;
        }

        return answer;
    }
}
