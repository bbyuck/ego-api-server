package hanta.bbyuck.egoapiserver.domain.lol;


import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.GameType;
import hanta.bbyuck.egoapiserver.domain.enumset.MatchType;
import hanta.bbyuck.egoapiserver.domain.enumset.ReportStatus;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
 * HANTA - Lol Report Entity class
 *
 * @ description : Lol Report Entity 클래스 / E - R Diagram 참고해 연관관계 확인
 *                 단건 신고를 모델링한 Entity
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
    @Column(name = "game_type")
    private MatchType matchType;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_status")
    private ReportStatus status;

    @Column(name = "report_time")
    private LocalDateTime reportTime;

    /*
     * 유저 편의 메서드
     */

    public void makeReport(User reporter, User reported, MatchType matchType, String reportContent) {
        this.reporter = reporter;
        this.reported = reported;
        this.matchType = matchType;
        this.status = ReportStatus.PROCESSING;
        this.reportContent = reportContent;
        this.reportTime = LocalDateTime.now();
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }
}
