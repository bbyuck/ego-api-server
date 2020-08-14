package hanta.bbyuck.egoapiserver;

import hanta.bbyuck.egoapiserver.domain.SnsVendor;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.UserStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolPosition;
import hanta.bbyuck.egoapiserver.domain.lol.LolTier;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.UserAuthRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoProfileCardMakeRequestDto;
import hanta.bbyuck.egoapiserver.service.UserService;
import hanta.bbyuck.egoapiserver.service.lol.LolDuoProfileCardService;
import hanta.bbyuck.egoapiserver.util.AES256Util;
import io.swagger.annotations.ApiModelProperty;
import lombok.RequiredArgsConstructor;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;


/*
 * 유저 500명
 */
@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
//        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final UserService userService;
        private final UserRepository userRepository;

        private final LolDuoProfileCardService lolDuoProfileCardService;
        private final AES256Util aes256Util;
        private final EntityManager em;


        public void dbInit1() {
            for (int i = 1; i <= 1000; i++) {
                UserAuthRequestDto user = new UserAuthRequestDto();
                if (i % 10 == 0) user.setSnsVendor(SnsVendor.NAVER);
                else user.setSnsVendor(SnsVendor.KAKAO);

                String email = "testEmail" + i + "@";
                if(i % 9 == 0) email += "naver.com";
                else email += "daum.net";

                user.setEmail(email);
                user.setSnsId(String.format("%019d", i));
                userService.join(user);

                if(i % 7 == 0 || i % 4 == 0) {
                    User userEntity = userRepository.find(aes256Util.encode(String.format("%019d", i)));
                    userEntity.updateUserStatus(UserStatus.LOL_DUO_MATHCING);
                }
            }

            for (int i = 1; i <= 1000; i++) {
                LolDuoProfileCardMakeRequestDto profileCardDto = new LolDuoProfileCardMakeRequestDto();

                profileCardDto.setOwnerAuth(aes256Util.encode(String.format("%019d", i)));

                Boolean voice;
                String summonerName = "소환사명" + i;
                LolTier tier;
                Integer tierLev;
                Integer lp;
                String champion1;
                String champion2;
                String champion3;
                Boolean top;
                Boolean jungle;
                Boolean mid;
                Boolean ad;
                Boolean support;
                LolPosition mainLolPosition;

                if (i % 7 == 0) voice = true;
                else voice = false;

                if(i % 100 <= 10) tier = LolTier.I;
                else if (i % 100 <= 20) tier = LolTier.B;
                else if (i % 100 <= 50) tier = LolTier.S;
                else if (i % 100 <= 80) tier = LolTier.G;
                else if (i % 100 <= 90) tier = LolTier.P;
                else if (i % 100 <= 95) tier = LolTier.D;
                else if (i % 100 <= 97) tier = LolTier.M;
                else if (i % 100 <= 98) tier = LolTier.GM;
                else tier = LolTier.C;

                if (tier.equals(LolTier.M) || tier.equals(LolTier.GM) || tier.equals(LolTier.C)) {
                    tierLev = 1;
                    lp = (tierLev * 100) % 2500;
                }
                else {
                    tierLev = i % 4 + 1;
                    lp = 0;
                }

                champion1 = "GAREN";
                champion2 = "Lee Sin";
                champion3 = "LUX";

                top = i % 6 == 0;
                jungle = i % 8 == 0;
                mid = i % 3 == 0;
                ad = i % 2 == 0;
                support = i % 15 == 0;

                if (i % 3 == 0) {
                    mainLolPosition = LolPosition.TOP;
                }
                else if (i % 4 == 0) {
                    mainLolPosition = LolPosition.JUNGLE;
                }
                else if (i % 7 == 0) {
                    mainLolPosition = LolPosition.MID;
                }
                else if (i % 11 == 0) {
                    mainLolPosition = LolPosition.AD;
                }
                else {
                    mainLolPosition = LolPosition.SUPPORT;
                }

                profileCardDto.setVoice(voice);
                profileCardDto.setSummonerName(summonerName);
                profileCardDto.setTier(tier);
                profileCardDto.setTierLev(tierLev);
                profileCardDto.setLp(lp);
                profileCardDto.setChampion1(champion1);
                profileCardDto.setChampion2(champion2);
                profileCardDto.setChampion3(champion3);
                profileCardDto.setTop(top);
                profileCardDto.setJungle(jungle);
                profileCardDto.setMid(mid);
                profileCardDto.setAd(ad);
                profileCardDto.setSupport(support);
                profileCardDto.setMainLolPosition(mainLolPosition);

                lolDuoProfileCardService.makeDuoProfileCard(profileCardDto);
            }

            em.flush();
        }

//        public void dbInit2() {
//            for (int i = 1; i <= 500; i++) {
//
//            }
//        }
    }
}