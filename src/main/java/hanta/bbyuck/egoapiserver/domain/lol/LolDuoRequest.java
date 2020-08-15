package hanta.bbyuck.egoapiserver.domain.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "lol_duo_request")
@SequenceGenerator(
        name = "lol_duo_request_seq_generator",
        sequenceName = "lol_duo_request_sequence"
)
public class LolDuoRequest {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lol_duo_request_seq_generator"
    )
    @Column(name = "LOL_DUO_REQUEST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID", referencedColumnName = "USER_ID")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID", referencedColumnName = "USER_ID")
    private User receiver;

    private LocalDateTime request_time;

    /*
     * 편의 메서드
     */

    public void updateRequestTime() { this.request_time = LocalDateTime.now(); }
    public void assignSender(User user) { this.sender = user; }
    public void assignReceiver(User user) { this.receiver = user; }
}
