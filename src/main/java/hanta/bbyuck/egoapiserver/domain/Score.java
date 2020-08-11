package hanta.bbyuck.egoapiserver.domain;

import hanta.bbyuck.egoapiserver.domain.lol.LolFinishedDuoMatching;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "score")
@SequenceGenerator(
        name = "score_seq_generator",
        sequenceName = "score_sequence"
)
public class Score {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "score_seq_generator"
    )
    @Column(name = "SCORE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RELATED_FINISHED_DUO_MATCHING_ID", referencedColumnName = "LOL_FINISHED_DUO_MATCHING_ID")
    private LolFinishedDuoMatching relatedLolFinishedDuoMatching;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GIVER_ID", referencedColumnName = "USER_ID")
    private User giver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID", referencedColumnName = "USER_ID")
    private User receiver;

    private Integer point;
    private LocalDateTime evaluation_time;

}
