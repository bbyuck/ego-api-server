package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoMatching;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolDuoMatchingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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
    public void setMatchingStatus(LolDuoMatching matching, LolDuoMatchingStatus status) { matching.setMatchingStatus(status);}

    public LolDuoMatching find(User user) {
        String query = "select ldipm from LolDuoMatching ldipm where ldipm.requester = :user or ldipm.respondent = :user";
        return em.createQuery(query, LolDuoMatching.class)
                .setParameter("user", user)
                .getSingleResult();
    }

    public LolDuoMatching findById(Long id) {
        return em.find(LolDuoMatching.class, id);
    }
}
