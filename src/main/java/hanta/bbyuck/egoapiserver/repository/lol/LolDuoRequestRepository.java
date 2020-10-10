package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoRequest;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolRequestStatus;
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
@Transactional(readOnly = true, noRollbackFor = Exception.class)
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
        String query = "select ldr from LolDuoRequest ldr where (ldr.sender =: sender and ldr.receiver =: receiver) and ldr.status =: active";
        return em.createQuery(query, LolDuoRequest.class)
                .setParameter("sender", sender)
                .setParameter("receiver", receiver)
                .setParameter("active", LolRequestStatus.ACTIVE)
                .getSingleResult();
    }

    public Boolean isExistRequest(User sender, User receiver) {
        String query = "select count(ldr) from LolDuoRequest ldr where ldr.sender =: sender and ldr.receiver =: receiver and ldr.status =: active";
        List<LolDuoRequest> resultList = em.createQuery(query, LolDuoRequest.class)
                .setParameter("sender", sender)
                .setParameter("receiver", receiver)
                .setParameter("active", LolRequestStatus.ACTIVE)
                .getResultList();

        return !resultList.isEmpty();
    }


    public List<LolDuoRequest> findSend(User user) {
        String query = "select ldr from LolDuoRequest ldr where ldr.sender = :user and ldr.status = :active";

        return em.createQuery(query, LolDuoRequest.class)
                .setParameter("user", user)
                .setParameter("active", LolRequestStatus.ACTIVE)
                .getResultList();
    }

    public List<LolDuoRequest> findReceive(User user) {
        String query = "select ldr from LolDuoRequest ldr where ldr.receiver = :user and ldr.status = :active";

        return em.createQuery(query, LolDuoRequest.class)
                .setParameter("user", user)
                .setParameter("active", LolRequestStatus.ACTIVE)
                .getResultList();
    }

    @Transactional
    public void updateAllSentRequest(User apiCaller, LolRequestStatus status) {
        String query = "update LolDuoRequest ldr set ldr.status =: status where ldr.sender = :user and ldr.status = : now";
        em.createQuery(query)
                .setParameter("status", status)
                .setParameter("user", apiCaller)
                .setParameter("now", LolRequestStatus.ACTIVE)
                .executeUpdate();
    }

    @Transactional
    public void updateRequestStatus(LolDuoRequest request, LolRequestStatus status) {
        request.setStatus(status);
    }

    @Transactional
    public void updateAllReceivedRequest(User apiCaller, LolRequestStatus status) {
        String query = "update LolDuoRequest ldr set ldr.status =: status where ldr.receiver = :user and ldr.status = : now";
        em.createQuery(query)
                .setParameter("status", status)
                .setParameter("user", apiCaller)
                .setParameter("now", LolRequestStatus.ACTIVE)
                .executeUpdate();
    }
}
