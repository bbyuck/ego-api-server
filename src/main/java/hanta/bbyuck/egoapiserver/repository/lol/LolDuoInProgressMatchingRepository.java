package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoInProgressMatching;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LolDuoInProgressMatchingRepository {
    private final EntityManager em;

    @Transactional
    public void save(LolDuoInProgressMatching matching) {
        em.persist(matching);
    }

    @Transactional
    public void remove(LolDuoInProgressMatching matching) {
        em.remove(matching);
    }

    public LolDuoInProgressMatching find(User user) {
        String query = "select ldipm from LolDuoInProgressMatching ldipm where ldipm.requester = :user or ldipm.respondent = :user";
        return em.createQuery(query, LolDuoInProgressMatching.class)
                .setParameter("user", user)
                .getSingleResult();
    }
}
