package hanta.bbyuck.egoapiserver.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "duo_request")
@SequenceGenerator(
        name = "duo_request_seq_generator",
        sequenceName = "duo_request_sequence"
)
public class DuoRequest {

    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "duo_request_seq_generator"
    )
    @Column(name = "DUO_REQUEST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID", referencedColumnName = "USER_ID")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ID", referencedColumnName = "USER_ID")
    private User receiver;

    private LocalDateTime request_time;
}
