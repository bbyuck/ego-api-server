package hanta.bbyuck.egoapiserver.repository;

import hanta.bbyuck.egoapiserver.domain.Experience;
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
public class ExperienceRepository {
    private final EntityManager em;

    @Transactional
    public void save(Experience experience) {
        em.persist(experience);
    }

    public List<Experience> findAll(User user) {
        String query = "select exp from Experience exp where exp.owner = :owner";

        return em.createQuery(query, Experience.class)
                .setParameter("owner", user)
                .getResultList();
    }
}
