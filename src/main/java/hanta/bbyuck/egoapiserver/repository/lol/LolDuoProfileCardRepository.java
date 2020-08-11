package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoProfileCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LolDuoProfileCardRepository {
    private final EntityManager em;

    @Transactional
    public Long save(LolDuoProfileCard lolDuoProfileCard) {
        em.persist(lolDuoProfileCard);
        return lolDuoProfileCard.getId();
    }


    public Boolean isExistSummonerName(String summonerName) {
        String query = "select count(dpc) from LolDuoProfileCard dpc where dpc.summonerName = :summonerName";
        Long summonerCount = em.createQuery(query, Long.class)
                .setParameter("summonerName", summonerName)
                .getSingleResult();
        return summonerCount != 0L;
    }



}
