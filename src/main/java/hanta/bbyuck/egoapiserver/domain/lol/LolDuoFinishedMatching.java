package hanta.bbyuck.egoapiserver.domain.lol;

import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolDuoFinishedMatchingStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "lol_duo_finished_matching")
@SequenceGenerator(
        name = "lol_duo_matching_seq_generator",
        sequenceName = "lol_duo_matching_sequence"
)
public class LolDuoFinishedMatching {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lol_duo_matching_seq_generator"
    )
    @Column(name = "LOL_DUO_FINISHED_MATCHING_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "finished_matching_status")
    private LolDuoFinishedMatchingStatus status;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;


    /*
     * 일대다 인스턴스
     */

    @OneToMany(mappedBy = "relatedLolDuoFinishedMatching")
    private List<LolDuoProfileInstance> participatedProfiles = new ArrayList<>();
}
