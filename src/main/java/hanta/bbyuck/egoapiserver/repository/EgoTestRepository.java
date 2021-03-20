package hanta.bbyuck.egoapiserver.repository;

import hanta.bbyuck.egoapiserver.domain.EgoTestAnswer;
import hanta.bbyuck.egoapiserver.domain.User;
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
@Transactional(readOnly = true)
public class EgoTestRepository {
    private final EntityManager em;

    @Transactional
    public void save(EgoTestAnswer answer) {
        em.persist(answer);
    }

    public Boolean exist(User user) {
        String query = "select eta from EgoTestAnswer eta where eta.respondent =: user";
        List<EgoTestAnswer> answers = em.createQuery(query, EgoTestAnswer.class)
                .setParameter("user", user)
                .getResultList();
        return !answers.isEmpty();
    }

}
