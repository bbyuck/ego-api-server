package hanta.bbyuck.egoapiserver.domain.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.MatchType;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolMatchingStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "lol_matching")
@SequenceGenerator(
        name = "lol_matching_seq_generator",
        sequenceName = "lol_matching_sequence"
)
public class LolMatching {
    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lol_matching_seq_generator"
    )
    @Column(name = "LOL_MATCHING_ID")
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "off_time")
    private LocalDateTime offTime;

    @Column(name = "finish_time")
    private LocalDateTime finishTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "matching_status", length = 45)
    private LolMatchingStatus matchingStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_type")
    private MatchType matchType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", referencedColumnName = "USER_ID")
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "respondent_id", referencedColumnName = "USER_ID")
    private User respondent;

    /*
     * 편의 메서드
     */

    public void assignRequester(User user) {
        this.requester = user;
    }

    public void assignRespondent(User user) {
        this.respondent = user;
    }

    public void setStartTime() {
        this.startTime = LocalDateTime.now();
    }

    public void setOffTime() { this.offTime = LocalDateTime.now(); }

    public void setFinishTime() { this.finishTime = LocalDateTime.now(); }

    public void setMatchingStatus(LolMatchingStatus matchingStatus) { this.matchingStatus = matchingStatus; }
}
