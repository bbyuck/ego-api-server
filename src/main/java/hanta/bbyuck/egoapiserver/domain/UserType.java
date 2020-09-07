package hanta.bbyuck.egoapiserver.domain;

import hanta.bbyuck.egoapiserver.domain.enumset.ESTIType;
import hanta.bbyuck.egoapiserver.domain.enumset.Game;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class UserType {

    @Id @GeneratedValue
    @Column(name = "user_type_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "game", length = 45)
    @Enumerated(EnumType.STRING)
    private Game game;

    @Column(name = "type", length = 45)
    @Enumerated(EnumType.STRING)
    private ESTIType type;
}
