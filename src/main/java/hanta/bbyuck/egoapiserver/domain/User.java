package hanta.bbyuck.egoapiserver.domain;

import hanta.bbyuck.egoapiserver.domain.lol.LolDuoProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoRequest;
import hanta.bbyuck.egoapiserver.domain.lol.LolInProgressDuoMatching;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
@SequenceGenerator(
        name = "user_seq_generator",
        sequenceName = "user_sequence"
)
public class User {
    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_seq_generator"
    )
    @Column(name = "USER_ID")
    private Long id;

    @Column
    private String salt;

    @Column(name = "user_auth", length = 44)
    private String userAuth;

    @Column(name = "user_key", length = 64)
    private String userKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "privilege", length = 10)
    private Privilege privilege;

    @Enumerated(EnumType.STRING)
    @Column(name = "sns_vendor", length = 10)
    private SnsVendor snsVendor;

    @Column(name = "sns_id", length = 250)
    private String snsId;

    @Column(name = "email", length = 250)
    private String email;

    @Column(name = "matching_status", length = 50)
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    /*
     * INACTIVE -> 최근 활동 시각에서 30분 이상 지났을 경우
     * ACTIVE   -> 최근 활동 시각에서 30분 이내
     * MATCHING -> 현재 매칭중
    */

    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @Column(name = "last_active_time")
    private LocalDateTime lastActiveTime;

    @Column(name = "reg_time")
    private LocalDateTime regTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 4)
    private ESTIType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participating_duo_matching", referencedColumnName = "LOL_IN_PROGRESS_DUO_MATCHING_ID")
    private LolInProgressDuoMatching lolInProgressDuoMatching;

    /*
     * 양방향 인스턴스
     */


    /*
     * duo_request
     */

    @OneToMany(mappedBy = "sender")
    private List<LolDuoRequest> sentLolDuoRequest = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private List<LolDuoRequest> receivedLolDuoRequest = new ArrayList<>();

    /*
     * score
     */

    @OneToMany(mappedBy = "receiver")
    private List<ESTIScore> receivedESTISocreList = new ArrayList<>();

    /*
     * 편의 메서드
     */

    public void createUser(SnsVendor _snsVendor, String _snsId, String _email) {
        this.userAuth = null;
        this.snsVendor = _snsVendor;
        this.privilege = Privilege.USER;
        this.snsId = _snsId;
        this.email = _email;
        this.status = UserStatus.INACTIVE;
        this.type = null;
        this.lastLoginTime = null;
        this.lastActiveTime = null;
        this.regTime = LocalDateTime.now();
        this.lolInProgressDuoMatching = null;
    }

    public void assignAuth(String auth) {
        this.userAuth = auth;
    }
    public void assignSalt(String salt) { this.salt = salt; }
    public void assignKey(String key) { this.userKey = key; }
    public void assignType(ESTIType type) { this.type = type; }

    public void updateLoginTime() {
        this.lastLoginTime = LocalDateTime.now();
    }
    public void updateActiveTime() {
        this.lastActiveTime = LocalDateTime.now();
    }
    public void updateUserStatus(UserStatus newStatus) {
        this.status = newStatus;
    }
    public void updateLolDuoMatching(LolInProgressDuoMatching lolInProgressDuoMatching) {
        this.lolInProgressDuoMatching = lolInProgressDuoMatching;
    }


}
