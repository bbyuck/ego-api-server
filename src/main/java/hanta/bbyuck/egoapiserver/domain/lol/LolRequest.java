package hanta.bbyuck.egoapiserver.domain.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.MatchType;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolRequestStatus;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolRequestType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "lol_request")
@SequenceGenerator(
        name = "lol_request_seq_generator",
        sequenceName = "lol_request_sequence"
)
public class LolRequest {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "lol_request_seq_generator"
    )
    @Column(name = "LOL_REQUEST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID", referencedColumnName = "USER_ID")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID", referencedColumnName = "USER_ID")
    private User receiver;

    private LocalDateTime request_time;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private LolRequestStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type")
    private LolRequestType type;


    /*
     * 편의 메서드
     */

    public void updateRequestTime() { this.request_time = LocalDateTime.now(); }
    public void assignSender(User user) { this.sender = user; }
    public void assignReceiver(User user) { this.receiver = user; }
    public void setStatus(LolRequestStatus status) { this.status = status; }
    public void setType(LolRequestType type) { this.type = type; }
}
