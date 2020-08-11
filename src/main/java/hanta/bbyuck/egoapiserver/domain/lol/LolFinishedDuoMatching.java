package hanta.bbyuck.egoapiserver.domain.lol;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "lol_finished_duo_matching")
@SequenceGenerator(
        name = "lol_duo_matching_seq_generator",
        sequenceName = "lol_duo_matching_sequence"
)
public class LolFinishedDuoMatching {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lol_duo_matching_seq_generator"
    )
    @Column(name = "LOL_FINISHED_DUO_MATCHING_ID")
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;


    /*
     * 일대다 인스턴스
     */

    @OneToMany(mappedBy = "relatedLolFinishedDuoMatching")
    private List<LolDuoProfileInstance> participatedProfiles = new ArrayList<>();
}
