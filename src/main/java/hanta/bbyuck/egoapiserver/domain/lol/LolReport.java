package hanta.bbyuck.egoapiserver.domain.lol;


import hanta.bbyuck.egoapiserver.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class LolReport {
    @Id @GeneratedValue
    @Column(name = "lol_report_id")
    private Long id;

    @Column(name = "report_reason", length = 500)
    private String reportContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_id")
    private User reported;
}
