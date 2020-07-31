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
@Table(name = "finished_matching")
public class FinishedMatching {

    @Id @GeneratedValue
    @Column(name = "FINISHED_MATCHING_ID")
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;


    /*
     * 일대다 인스턴스
     */

    @OneToMany(mappedBy = "relatedFinishedMatching")
    private List<DuoProfileInstance> participatedProfiles = new ArrayList<>();
}
