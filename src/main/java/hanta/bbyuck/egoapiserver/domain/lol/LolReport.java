package hanta.bbyuck.egoapiserver.domain.lol;


import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.ReportStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "report_status")
    private ReportStatus status;

    @Column(name = "report_time")
    private LocalDateTime reportTime;

    public void setStatus(ReportStatus status) {
        this.status = status;
    }
}
