package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoRequest;
import hanta.bbyuck.egoapiserver.exception.http.NotFoundException;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LolDuoRequestRepository {
    private final EntityManager em;

    @Transactional
    public void save(LolDuoRequest request) {
        em.persist(request);
    }

    @Transactional
    public void remove(LolDuoRequest request) { em.remove(request); }

    @Transactional
    public void removeAllSent(User user) {
        String query = "delete from LolDuoRequest ldr where ldr.sender = :sender";
        em.createQuery(query)
                .setParameter("sender", user)
                .executeUpdate();
    }

    @Transactional
    public void removeAllReceived(User user) {
        String query = "delete from LolDuoRequest ldr where ldr.receiver = :receiver";
        em.createQuery(query)
                .setParameter("receiver", user)
                .executeUpdate();
    }

    public LolDuoRequest findRequest(User sender, User receiver){
        String query = "select ldr from LolDuoRequest ldr where ldr.sender =: sender and ldr.receiver =: receiver";
        return em.createQuery(query, LolDuoRequest.class)
                .setParameter("sender", sender)
                .setParameter("receiver", receiver)
                .getSingleResult();
    }

    public Long isExistRequest(User sender, User receiver) {
        String query = "select count(ldr) from LolDuoRequest ldr where ldr.sender =: sender and ldr.receiver =: receiver";
        return em.createQuery(query, Long.class)
                .setParameter("sender", sender)
                .setParameter("receiver", receiver)
                .getSingleResult();
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
