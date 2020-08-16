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
@Table(name = "lol_duo_in_progress_matching")
@SequenceGenerator(
        name = "lol_duo_matching_seq_generator",
        sequenceName = "lol_duo_matching_sequence"
)
public class LolDuoInProgressMatching {
    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lol_duo_matching_seq_generator"
    )
    @Column(name = "LOL_DUO_IN_PROGRESS_MATCHING_ID")
    private Long id;
    private LocalDateTime start_time;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", referencedColumnName = "USER_ID")
    private User requester;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "respondent_id", referencedColumnName = "USER_ID")
    private User respondent;
}