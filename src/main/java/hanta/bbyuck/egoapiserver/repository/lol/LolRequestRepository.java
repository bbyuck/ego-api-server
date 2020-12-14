package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolRequest;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolRequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class LolRequestRepository {
    private final EntityManager em;

    @Transactional
    public void save(LolRequest request) {
        em.persist(request);
    }

    @Transactional
    public void remove(LolRequest request) { em.remove(request); }

    @Transactional
    public void removeAllSent(User user) {
        String query = "delete from LolRequest lr where lr.sender = :sender";
        em.createQuery(query)
                .setParameter("sender", user)
                .executeUpdate();
    }

    @Transactional
    public void removeAllReceived(User user) {
        String query = "delete from LolRequest lr where lr.receiver = :receiver";
        em.createQuery(query)
                .setParameter("receiver", user)
                .executeUpdate();
    }

    public LolRequest findRequest(User sender, User receiver){
        String query = "select lr from LolRequest lr where (lr.sender =: sender and lr.receiver =: receiver) and lr.status =: active";
        return em.createQuery(query, LolRequest.class)
                .setParameter("sender", sender)
                .setParameter("receiver", receiver)
                .setParameter("active", LolRequestStatus.ACTIVE)
                .getSingleResult();
    }

    public Boolean isExistRequest(User sender, User receiver) {
        String query = "select lr from LolRequest lr where lr.sender =: sender and lr.receiver =: receiver and lr.status =: active";
        List<LolRequest> resultList = em.createQuery(query, LolRequest.class)
                .setParameter("sender", sender)
                .setParameter("receiver", receiver)
                .setParameter("active", LolRequestStatus.ACTIVE)
                .getResultList();

        return !resultList.isEmpty();
    }


    public List<LolRequest> findSend(User user) {
        String query = "select lr from LolRequest lr where lr.sender = :user and lr.status = :active";

        return em.createQuery(query, LolRequest.class)
                .setParameter("user", user)
                .setParameter("active", LolRequestStatus.ACTIVE)
                .getResultList();
    }

    public List<LolRequest> findReceive(User user) {
        String query = "select lr from LolRequest lr where lr.receiver = :user and lr.status = :active";

        return em.createQuery(query, LolRequest.class)
                .setParameter("user", user)
                .setParameter("active", LolRequestStatus.ACTIVE)
                .getResultList();
    }

    @Transactional
    public void updateAllSentRequest(User apiCaller, LolRequestStatus status) {
        String query = "update LolRequest lr set lr.status =: status where lr.sender = :user and lr.status = : now";
        em.createQuery(query)
                .setParameter("status", status)
                .setParameter("user", apiCaller)
                .setParameter("now", LolRequestStatus.ACTIVE)
                .executeUpdate();
    }

    @Transactional
    public void updateRequestStatus(LolRequest request, LolRequestStatus status) {
        request.setStatus(status);
    }

    @Transactional
    public void updateAllReceivedRequest(User apiCaller, LolRequestStatus status) {
        String query = "update LolRequest lr set lr.status =: status where lr.receiver = :user and lr.status = : now";
        em.createQuery(query)
                .setParameter("status", status)
                .setParameter("user", apiCaller)
                .setParameter("now", LolRequestStatus.ACTIVE)
                .executeUpdate();
    }


}