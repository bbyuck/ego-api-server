package hanta.bbyuck.egoapiserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "score")
public class Score {

    @Id @GeneratedValue
    @Column(name = "SCORE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RELATED_FINISHED_MATCHING_ID", referencedColumnName = "FINISHED_MATCHING_ID")
    private FinishedMatching relatedFinishedMatching;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GIVER_ID", referencedColumnName = "USER_ID")
    private User giver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID", referencedColumnName = "USER_ID")
    private User receiver;

    private Integer point;
    private LocalDateTime evaluation_time;

}
