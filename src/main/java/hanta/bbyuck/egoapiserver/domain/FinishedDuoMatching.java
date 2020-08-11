package hanta.bbyuck.egoapiserver.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "finished_duo_matching")
@SequenceGenerator(
        name = "duo_matching_seq_generator",
        sequenceName = "duo_matching_sequence"
)
public class FinishedDuoMatching {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "duo_matching_seq_generator"
    )
    @Column(name = "FINISHED_DUO_MATCHING_ID")
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;


    /*
     * 일대다 인스턴스
     */

    @OneToMany(mappedBy = "relatedFinishedDuoMatching")
    private List<DuoProfileInstance> participatedProfiles = new ArrayList<>();
}
