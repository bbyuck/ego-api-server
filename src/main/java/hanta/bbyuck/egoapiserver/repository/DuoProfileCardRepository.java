package hanta.bbyuck.egoapiserver.repository;

import hanta.bbyuck.egoapiserver.domain.DuoProfileCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DuoProfileCardRepository {
    private final EntityManager em;

    @Transactional
    public Long save(DuoProfileCard duoProfileCard) {
        em.persist(duoProfileCard);
        return duoProfileCard.getId();
    }

    public DuoProfileCard find(Long duoProfileCardId) {
        return em.find(DuoProfileCard.class, duoProfileCardId);
    }

    public Boolean isExistSummonerName(String summonerName) {
        String query = "select count(dpc) from DuoProfileCard dpc where dpc.summonerName = :summonerName";
        Long summonerCount = em.createQuery(query, Long.class)
                .setParameter("summonerName", summonerName)
                .getSingleResult();
        return summonerCount != 0L;
    }



}
