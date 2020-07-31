package hanta.bbyuck.egoapiserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "duo_request")
public class DuoRequest {

    @Id @GeneratedValue
    @Column(name = "DUO_REQUEST_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SENDER_ID", referencedColumnName = "USER_ID")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "RECEIVER_ID", referencedColumnName = "USER_ID")
    private User receiver;

    private LocalDateTime request_time;
}
