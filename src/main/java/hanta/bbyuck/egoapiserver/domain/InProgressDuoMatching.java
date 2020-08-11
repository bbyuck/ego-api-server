package hanta.bbyuck.egoapiserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "in_progress_duo_matching")
@SequenceGenerator(
        name = "duo_matching_seq_generator",
        sequenceName = "duo_matching_sequence"
)
public class InProgressDuoMatching {
    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "duo_matching_seq_generator"
    )
    @Column(name = "IN_PROGRESS_DUO_MATCHING_ID")
    private Long id;
    private LocalDateTime start_time;

    @OneToMany(mappedBy = "inProgressDuoMatching")
    private List<User> participatingUsers = new ArrayList<>();

}
