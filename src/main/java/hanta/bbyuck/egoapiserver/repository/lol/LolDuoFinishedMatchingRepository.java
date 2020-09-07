package hanta.bbyuck.egoapiserver.repository.lol;


import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoFinishedMatching;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LolDuoFinishedMatchingRepository {
    private final EntityManager em;
    private final int MAX_SHOW_DATE = 30;

    @Transactional
    public Long save(LolDuoFinishedMatching finishedMatching) {
        em.persist(finishedMatching);
        return finishedMatching.getId();
    }


    public List<LolDuoFinishedMatching> find(User user) {
        String query = "select ldfm from LolDuoFinishedMatching ldfm where ldfm.endTime < :threshold";
        return em.createQuery(query, LolDuoFinishedMatching.class)
                .setParameter("threshold", LocalDateTime.now().minusDays(30))
                .getResultList();
    }
}
