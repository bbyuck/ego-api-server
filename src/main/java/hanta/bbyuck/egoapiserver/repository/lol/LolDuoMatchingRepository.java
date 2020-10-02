package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoMatching;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolDuoMatchingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class LolDuoMatchingRepository {
    private final EntityManager em;

    @Transactional
    public void save(LolDuoMatching matching) {
        em.persist(matching);
    }

    @Transactional
    public void remove(LolDuoMatching matching) {
        em.remove(matching);
    }

    @Transactional
    public void setFinishTime(LolDuoMatching matching) { matching.setFinishTime(); }

    @Transactional
    public void setOffTime(LolDuoMatching matching) { matching.setOffTime(); }


    @Transactional
    public void setMatchingStatus(LolDuoMatching matching, LolDuoMatchingStatus status) { matching.setMatchingStatus(status);}

    public LolDuoMatching find(User user) {
        String query = "select ldm from LolDuoMatching ldm where (ldm.requester = :user or ldm.respondent = :user) " +
                "and (ldm.matchingStatus = :matching or ldm.matchingStatus = :matching_on or ldm.matchingStatus = :matching_off)";
        return em.createQuery(query, LolDuoMatching.class)
                .setParameter("user", user)
                .setParameter("matching", LolDuoMatchingStatus.MATCHING)
                .setParameter("matching_on", LolDuoMatchingStatus.MATCHING_ON)
                .setParameter("matching_off", LolDuoMatchingStatus.MATCHING_OFF)
                .getSingleResult();
    }

    public Boolean isExist(User requester, User respondent) {
        String query = "select ldm from LolDuoMatching ldm where ldm.requester = : req and ldm.respondent = : res " +
                "and (ldm.matchingStatus = : matching or ldm.matchingStatus = : matching_on or ldm.matchingStatus = : matching_off)";
        List<LolDuoMatching> resultList = em.createQuery(query, LolDuoMatching.class)
                .setParameter("req", requester)
                .setParameter("res", respondent)
                .setParameter("matching", LolDuoMatchingStatus.MATCHING)
                .setParameter("matching_on", LolDuoMatchingStatus.MATCHING_ON)
                .setParameter("matching_off", LolDuoMatchingStatus.MATCHING_OFF)
                .getResultList();

        return !resultList.isEmpty();
    }

    public LolDuoMatching findById(Long id) {
        return em.find(LolDuoMatching.class, id);
    }
}
