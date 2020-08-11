package hanta.bbyuck.egoapiserver.domain.lol;

import hanta.bbyuck.egoapiserver.domain.Position;
import hanta.bbyuck.egoapiserver.domain.User;
import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "lol_duo_profile_instance")
@SequenceGenerator(
        name = "lol_duo_profile_instance_seq_generator",
        sequenceName = "lol_duo_profile_instance_sequence"
)
public class LolDuoProfileInstance {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lol_duo_profile_instance_seq_generator"
    )
    @Column(name = "LOL_DUO_PROFILE_INSTANCE_ID")
    private Long id;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID", referencedColumnName = "USER_ID")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RELATED_FINISHED_DUO_MATCHING_ID", referencedColumnName = "LOL_FINISHED_DUO_MATCHING_ID")
    private LolFinishedDuoMatching relatedLolFinishedDuoMatching;
}