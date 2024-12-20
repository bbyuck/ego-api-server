package hanta.bbyuck.egoapiserver.domain.lol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.GameType;
import hanta.bbyuck.egoapiserver.domain.enumset.MatchType;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolPosition;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolTier;
import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
 * HANTA - Lol Profile Card Entity class
 *
 * @ description : Lol Profile Card Entity 클래스 / E - R Diagram 참고해 연관관계 확인
 *                 Lol Profile Card를 모델링한 Entity
 *
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * @ 기획상 문제가 있지 않는 경우 절대 건드리지 말 것 @
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 *
 * @      author : 강혁(bbyuck) (k941026h@naver.com)
 * @       since : 2020. 01. 01
 * @ last update : 2021. 02. 22
 *
 * <Copyright 2020. 한타. All rights reserved.>
 */

@Entity
@Getter
@Table(name = "lol_profile_card")
@SequenceGenerator(
        name = "lol_profile_card_seq_generator",
        sequenceName = "lol_profile_card_sequence"
)
public class LolProfileCard {
    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lol_profile_card_seq_generator"
    )
    @Column(name = "LOL_PROFILE_CARD_ID")
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID", nullable = false)
    private User owner;

    @Column(name = "last_update_time", nullable = false)
    private LocalDateTime lastUpdateTime;

    @Column(name = "voice", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean voice;

    @Column(name = "summoner_name", length = 20, nullable = false)
    private String summonerName;


    @Enumerated(EnumType.STRING)
    @Column(name = "tier", length = 12, nullable = false)
    private LolTier tier;

    @Column(name = "tier_lev", nullable = false)
    private Integer tierLev;

    @Column(name = "lp", nullable = false)
    private Integer lp;

    @Column(name = "champion1", length = 20, nullable = false)
    private String champion1;

    @Column(name = "champion2", length = 20, nullable = false)
    private String champion2;

    @Column(name = "champion3", length = 20, nullable = false)
    private String champion3;

    @Column(name="top", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean top;

    @Column(name = "jungle", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean jungle;

    @Column(name = "mid", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean mid;

    @Column(name = "ad", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean ad;

    @Column(name = "support", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean support;

    @Enumerated(EnumType.STRING)
    @Column(name = "main_position", length = 7, nullable = false)
    private LolPosition mainLolPosition;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_type")
    private GameType gameType;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_type")
    private MatchType matchType;

    @Enumerated(EnumType.STRING)
    @Column(name = "favorite_tier", length = 12, nullable = false)
    private LolTier favoriteTier;

    @Column(name="favorite_top", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean favoriteTop;

    @Column(name = "favorite_jungle", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean favoriteJungle;

    @Column(name = "favorite_mid", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean favoriteMid;

    @Column(name = "favorite_ad", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean favoriteAd;

    @Column(name = "favorite_support", columnDefinition = "TINYINT", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean favoriteSupport;

    /*
     * 편의 메서드
     */
    public void makeProfileCard(User _owner,
                                Boolean _voice,
                                String _summonerName,
                                LolTier _tier,
                                Integer _tierLev,
                                Integer _lp,
                                String _champion1,
                                String _champion2,
                                String _champion3,
                                Boolean _top,
                                Boolean _jungle,
                                Boolean _mid,
                                Boolean _ad,
                                Boolean _support,
                                LolPosition _mainLolPosition,
                                GameType _gameType,
                                MatchType _matchType) {
        this.owner = _owner;
        this.lastUpdateTime = LocalDateTime.now();
        this.voice = _voice;
        this.summonerName = _summonerName;
        this.tier = _tier;
        this.tierLev = _tierLev;
        this.lp = _lp;
        this.champion1 = _champion1;
        this.champion2 = _champion2;
        this.champion3 = _champion3;
        this.top = _top;
        this.jungle = _jungle;
        this.mid = _mid;
        this.ad = _ad;
        this.support = _support;
        this.mainLolPosition = _mainLolPosition;
        this.gameType = _gameType;
        this.matchType = _matchType;
    }

    public void updateProfileCard(Boolean _voice,
                                  String _summonerName,
                                  LolTier _tier,
                                  Integer _tierLev,
                                  Integer _lp,
                                  String _champion1,
                                  String _champion2,
                                  String _champion3,
                                  Boolean _top,
                                  Boolean _jungle,
                                  Boolean _mid,
                                  Boolean _ad,
                                  Boolean _support,
                                  LolPosition _mainLolPosition,
                                  GameType _gameType) {
        this.lastUpdateTime = LocalDateTime.now();
        this.voice = _voice;
        this.summonerName = _summonerName;
        this.tier = _tier;
        this.tierLev = _tierLev;
        this.lp = _lp;
        this.champion1 = _champion1;
        this.champion2 = _champion2;
        this.champion3 = _champion3;
        this.top = _top;
        this.jungle = _jungle;
        this.mid = _mid;
        this.ad = _ad;
        this.support = _support;
        this.mainLolPosition = _mainLolPosition;
        this.gameType = _gameType;
    }

    public void updateFavorite(LolTier _favoriteTier,
                               Boolean _favoriteTop,
                               Boolean _favoriteJungle,
                               Boolean _favoriteMid,
                               Boolean _favoriteAd,
                               Boolean _favoriteSupport) {
        favoriteTier = _favoriteTier;
        favoriteTop = _favoriteTop;
        favoriteJungle = _favoriteJungle;
        favoriteMid = _favoriteMid;
        favoriteAd = _favoriteAd;
        favoriteSupport = _favoriteSupport;
    }
}
