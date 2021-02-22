package hanta.bbyuck.egoapiserver.domain.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolRequestStatus;
import hanta.bbyuck.egoapiserver.domain.enumset.RequestType;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
 * HANTA - Lol Request Entity class
 *
 * @ description : Lol Request Entity 클래스 / E - R Diagram 참고해 연관관계 확인
 *                 단방향 매칭 신청 1건을 모델링한 Entity
 *
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * @ 기획상 문제가 있지 않는 경우 절대 건드리지 말 것 @
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 *
 * @      author : 강혁(bbyuck) (k941026h@naver.com)
 * @       since : 2020. 01. 01
 * @ last update : 2021. 02. 22
 *
 * <Copyright 2020. 한타. All rights reserved.>
 */

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
    private RequestType type;


    /*
     * 편의 메서드
     */

    public void updateRequestTime() { this.request_time = LocalDateTime.now(); }
    public void assignSender(User user) { this.sender = user; }
    public void assignReceiver(User user) { this.receiver = user; }
    public void setStatus(LolRequestStatus status) { this.status = status; }
    public void setType(RequestType type) { this.type = type; }
}
