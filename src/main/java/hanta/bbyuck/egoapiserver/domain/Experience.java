package hanta.bbyuck.egoapiserver.domain;


import hanta.bbyuck.egoapiserver.domain.enumset.ExpStatus;
import hanta.bbyuck.egoapiserver.domain.enumset.Game;
import hanta.bbyuck.egoapiserver.domain.enumset.GameType;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "experience")
@SequenceGenerator(
        name = "exp_seq_generator",
        sequenceName = "exp_sequence"
)
public class Experience {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "exp_seq_generator"
    )
    @Column(name = "experience_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "game")
    @Enumerated(EnumType.STRING)
    private Game game;

    @Column(name = "game_type")
    @Enumerated(EnumType.STRING)
    private GameType gameType;


    @Column(name = "exp_status")
    @Enumerated(EnumType.STRING)
    private ExpStatus expStatus;


    @Column(name = "get_date")
    private LocalDateTime getDate;


    /*
     * 유저 편의 메서드
     */

    public void makeExp(User user, Game game, GameType gameType, ExpStatus expStatus) {
        this.owner = user;
        this.game = game;
        this.gameType = gameType;
        this.expStatus = expStatus;
        this.getDate = LocalDateTime.now();
    }
}
