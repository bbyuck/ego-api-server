package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.enumset.ReportStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class LolReportRepository {
    private final EntityManager em;

    @Transactional
    public void save(LolReport report) {
        em.persist(report);
    }

    @Transactional
    public void updateStatus(LolReport report, ReportStatus status) {
        report.setStatus(status);
    }

}
