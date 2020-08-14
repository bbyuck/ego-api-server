package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LolDuoRequestRepository {
    private final EntityManager em;

    public void save(LolDuoRequest request) {
        em.persist(request);
        em.flush();
    }

    public List<LolDuoRequest> findSend(User user) {
        String query = "select ldr from LolDuoRequest ldr where ldr.sender = :user";

        return em.createQuery(query, LolDuoRequest.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<LolDuoRequest> findReceive(User user) {
        String query = "select ldr from LolDuoRequest ldr where ldr.receiver = :user";

        return em.createQuery(query, LolDuoRequest.class)
                .setParameter("user", user)
                .getResultList();
    }
}
