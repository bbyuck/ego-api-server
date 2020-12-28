package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolRecommendationRefresh;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class LolRecommendationRefreshRepository {
    private final EntityManager em;
    private final Integer MAX_RECOMMEND_COUNT_PER_DAY = 3;
    @Transactional
    public void save(LolRecommendationRefresh refresh) {
        em.persist(refresh);
    }

    public List<LolRecommendationRefresh> find(User apiCaller) {
        String query = "select lrr " +
                "from LolRecommendationRefresh lrr " +
                "where lrr.apiCaller =: apiCaller " +
                "order by lrr.refreshDatetime";

        return em.createQuery(query, LolRecommendationRefresh.class)
                .setFirstResult(0)
                .setMaxResults(MAX_RECOMMEND_COUNT_PER_DAY)
                .setParameter("apiCaller", apiCaller)
                .getResultList();
    }
}
