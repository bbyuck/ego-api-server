package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolRecommendationRefresh;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

/*
 * <pre>
 * Copyright (c) 2020 HANTA
 * All rights reserved.
 *
 * This software is the proprietary information of HANTA
 * </pre>
 *
 * @ author 강혁(bbyuck) (k941026h@naver.com)
 * @ since  2020. 01. 01
 *
 * @History
 * <pre>
 * -----------------------------------------------------
 * 2020.01.01
 * bbyuck (k941026h@naver.com) 최초작성
 * -----------------------------------------------------
 * </pre>
 */

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
                "order by lrr.refreshDatetime desc";

        return em.createQuery(query, LolRecommendationRefresh.class)
                .setFirstResult(0)
                .setMaxResults(MAX_RECOMMEND_COUNT_PER_DAY + 1)
                .setParameter("apiCaller", apiCaller)
                .getResultList();
    }
}
