package hanta.bbyuck.egoapiserver.domain;

import hanta.bbyuck.egoapiserver.domain.lol.LolDuoFinishedMatching;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "esti_score")
@SequenceGenerator(
        name = "esti_score_seq_generator",
        sequenceName = "esti_score_sequence"
)
public class ESTIScore {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "esti_score_seq_generator"
    )
    @Column(name = "ESTI_SCORE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RELATED_FINISHED_DUO_MATCHING_ID", referencedColumnName = "LOL_DUO_FINISHED_MATCHING_ID")
    private LolDuoFinishedMatching relatedLolDuoFinishedMatching;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GIVER_ID", referencedColumnName = "USER_ID")
    private User giver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID", referencedColumnName = "USER_ID")
    private User receiver;

    private Integer point;
    private LocalDateTime evaluation_time;

}
