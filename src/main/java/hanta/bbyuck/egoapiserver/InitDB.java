package hanta.bbyuck.egoapiserver;

import hanta.bbyuck.egoapiserver.domain.enumset.GameType;
import hanta.bbyuck.egoapiserver.domain.enumset.MatchType;
import hanta.bbyuck.egoapiserver.domain.enumset.SnsVendor;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolPosition;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolTier;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.UserAuthRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolProfileCardMakeRequestDto;
import hanta.bbyuck.egoapiserver.service.UserService;
import hanta.bbyuck.egoapiserver.service.lol.LolProfileCardService;
import hanta.bbyuck.egoapiserver.service.lol.LolRequestService;
import hanta.bbyuck.egoapiserver.util.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import static hanta.bbyuck.egoapiserver.domain.enumset.GameType.*;
import static hanta.bbyuck.egoapiserver.domain.enumset.GameType.RANK;
import static hanta.bbyuck.egoapiserver.domain.enumset.MatchType.*;


//@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

//    @PostConstruct
    public void init() {
        initService.dbInit1();
//        initService.dbInit2();
    }

//    @Component
//    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final UserService userService;
        private final UserRepository userRepository;

        private final LolRequestService lolRequestService;

        private final LolProfileCardService lolProfileCardService;
        private final EntityManager em;

        private static final int TEST_USER_COUNT = 40;
        private static final int TEST_REQUEST_COUNT = 100;

        public void dbInit1() {
            for (int i = 1; i <= TEST_USER_COUNT; i++) {
                UserAuthRequestDto user = new UserAuthRequestDto();
                if (i % 10 == 0) user.setSnsVendor(SnsVendor.NAVER);
                else user.setSnsVendor(SnsVendor.KAKAO);

                String email = "testEmail" + i + "@";
                if(i % 9 == 0) email += "naver.com";
                else email += "daum.net";

                user.setEmail(email);
                user.setSnsId(String.format("%019d", i));
                user.setClientVersion("v1.00");
                userService.join(user);


                em.flush();
            }

            for (int i = 1; i <= TEST_USER_COUNT; i++) {
                LolProfileCardMakeRequestDto profileCardDto = new LolProfileCardMakeRequestDto();

                SnsVendor snsVendor;
                String snsId = String.format("%019d", i);

                if (i % 10 == 0) snsVendor = SnsVendor.NAVER;
                else snsVendor = SnsVendor.KAKAO;



                profileCardDto.setGeneratedId(AES256Util.encode(snsVendor + " - " + snsId));

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
                LolPosition mainLolPosition = LolPosition.AD;

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
                    if(tier.equals(LolTier.M)) {
                        lp = 100;
                    }
                    else if (tier.equals(LolTier.GM)) {
                        lp = 450;
                    }
                    else {
                        lp = 900;
                    }
                }
                else {
                    tierLev = i % 4 + 1;
                    lp = 0;
                }

                champion1 = "GAREN";
                champion2 = "Lee Sin";
                champion3 = "LUX";

                top = i % 6 == 0;
                jungle = i % 5 == 0;
                mid = i % 3 == 0;
                ad = i % 2 == 0;
                support = i % 7 == 0;

                if (i % 6 == 0) {
                    mainLolPosition = LolPosition.TOP;
                }
                else if (i % 5 == 0) {
                    mainLolPosition = LolPosition.JUNGLE;
                }
                else if (i % 3 == 0) {
                    mainLolPosition = LolPosition.MID;
                }
                else if (i % 2 == 0) {
                    mainLolPosition = LolPosition.AD;
                }
                else if (i % 7 == 0) {
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
                profileCardDto.setClientVersion("v1.00");
                profileCardDto.setMatchType(DUO);
                profileCardDto.setGameType((i % 2 ==0 ? NORMAL : RANK));
                lolProfileCardService.makeDuoProfileCard(profileCardDto);
                em.flush();
            }
        }

        public void dbInit2() {
            for (int i = 7; i <= 40; i *= 2) {

            }
        }
    }
}