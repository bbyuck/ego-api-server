package hanta.bbyuck.egoapiserver.domain.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "lol_in_progress_duo_matching")
@SequenceGenerator(
        name = "lol_duo_matching_seq_generator",
        sequenceName = "lol_duo_matching_sequence"
)
public class LolInProgressDuoMatching {
    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lol_duo_matching_seq_generator"
    )
    @Column(name = "LOL_IN_PROGRESS_DUO_MATCHING_ID")
    private Long id;
    private LocalDateTime start_time;

    @OneToMany(mappedBy = "lolInProgressDuoMatching")
    private List<User> participatingUsers = new ArrayList<>();

}
