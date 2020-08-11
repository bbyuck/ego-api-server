package hanta.bbyuck.egoapiserver.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "duo_profile_card")
@SequenceGenerator(
        name = "duo_profile_card_seq_generator",
        sequenceName = "duo_profile_card_sequence"
)
public class DuoProfileCard {
    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "duo_profile_card_seq_generator"
    )
    @Column(name = "DUO_PROFILE_CARD_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User owner;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean voice;

    @Column(name = "summoner_name", length = 20)
    private String summonerName;

    @Column(name = "tier", length = 2)
    private String tier;

    @Column(name = "tier_lev")
    private Integer tierLev;

    @Column(name = "lp")
    private Integer lp;

    @Column(name = "champion1", length = 20)
    private String champion1;

    @Column(name = "champion2", length = 20)
    private String champion2;

    @Column(name = "champion3", length = 20)
    private String champion3;

    @Column(name="top", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean top;

    @Column(name = "jungle", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean jungle;

    @Column(name = "mid", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean mid;

    @Column(name = "ad", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean ad;

    @Column(name = "support", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean support;

    @Enumerated(EnumType.STRING)
    @Column(name = "main_position", length = 7)
    private Position mainPosition;

    /*
     * 편의 메서드
     */
    public void makeProfileCard(User _owner,
                                Boolean _voice,
                                String _summonerName,
                                String _tier,
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
                                Position _mainPosition) {
        this.owner = _owner;
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
        this.mainPosition = _mainPosition;
    }
}
