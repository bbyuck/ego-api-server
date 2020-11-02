package hanta.bbyuck.egoapiserver.domain;

import hanta.bbyuck.egoapiserver.domain.enumset.Game;
import hanta.bbyuck.egoapiserver.domain.enumset.SnsVendor;
import hanta.bbyuck.egoapiserver.domain.enumset.UserStatus;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "user")
@SequenceGenerator(
        name = "user_seq_generator",
        sequenceName = "user_sequence"
)
public class User implements UserDetails {
    @Id @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_seq_generator"
    )
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "fcm_token", length = 250)
    private String fcmToken;

    // Spring Security
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> privileges = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "sns_vendor", length = 10)
    private SnsVendor snsVendor;

    @Column(name = "sns_id", length = 250)
    private String snsId;

    @Column(name = "email", length = 250)
    private String email;

    @Column(name = "user_status", length = 50)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    /*
     * ACTIVE   -> 30분 이내
     * MATCHING -> 현재 매칭중
    */

    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @Column(name = "last_active_time")
    private LocalDateTime lastActiveTime;

    @Column(name = "reg_time")
    private LocalDateTime regTime;

    @Column(name = "generated_id", length = 255)
    private String generatedId;

    @Column(name = "salt", length = 255)
    private String salt;

    @Column(name = "generated_pw", length = 255)
    private String generatedPw;

    @Column(name = "game", length = 45)
    @Enumerated(EnumType.STRING)
    private Game game;

    /*
     * X-AUTH-TOKEN 관련
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.privileges.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return generatedPw;
    }

    @Override
    public String getUsername() {
        return generatedId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /*
     * 양방향 인스턴스
     */


    /*
     * score
     */

    @OneToMany(mappedBy = "receiver")
    private List<EgoScore> receivedESTISocreList = new ArrayList<>();

    /*
     * 편의 메서드
     */

    public void createUser(SnsVendor _snsVendor, String _snsId, String _email) {
        this.snsVendor = _snsVendor;
        this.snsId = _snsId;
        this.email = _email;
        this.status = UserStatus.ACTIVE;
        this.privileges.add("ROLE_USER");
        this.lastLoginTime = null;
        this.lastActiveTime = null;
        this.regTime = LocalDateTime.now();
    }

    public void assignSalt(String salt) { this.salt = salt; }
    public void assignPw(String pw) { this.generatedPw = pw; }
    public void assignId(String id) { this.generatedId = id; }

    public void updateLoginTime() {
        this.lastLoginTime = LocalDateTime.now();
    }
    public void updateActiveTime() {
        this.lastActiveTime = LocalDateTime.now();
    }
    public void updateUserStatus(UserStatus newStatus) {
        this.status = newStatus;
    }
    public void updateFcmToken(String fcmToken) { this.fcmToken = fcmToken; }

    public void updateGame(Game game) { this.game = game; }

}
