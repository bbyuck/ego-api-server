package hanta.bbyuck.egoapiserver.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "duo_profile_card")
public class DuoProfileCard {
    @Id @GeneratedValue
    @Column(name = "DUO_PROFILE_CARD_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User owner;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean voice;

    @Column(length = 20)
    private String summoner_name;
    @Column(length = 2)
    private String tier;
    private Integer tier_lev;
    private Integer lp;

    @Column(length = 20)
    private String champion1;

    @Column(length = 20)
    private String champion2;

    @Column(length = 20)
    private String champion3;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean top;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean jungle;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean mid;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean ad;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean support;

    @Enumerated(EnumType.STRING)
    private Position main_position;
}
