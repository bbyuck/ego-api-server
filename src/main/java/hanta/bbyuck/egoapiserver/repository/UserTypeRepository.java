package hanta.bbyuck.egoapiserver.repository;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.UserType;
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
public class UserTypeRepository {
    private final EntityManager em;

    @Transactional
    public void save(UserType userType) {
        em.persist(userType);
    }

    public UserType find(User owner) {
        String query = "select ut from UserType ut where ut.user =: owner";
        return em.createQuery(query, UserType.class)
                .setParameter("owner", owner)
                .getSingleResult();
    }

    public Boolean exist(User owner) {
        String query = "select ut from UserType ut where ut.user =: owner";
        List<UserType> ls = em.createQuery(query, UserType.class)
                .setParameter("owner", owner)
                .getResultList();
        return !ls.isEmpty();
    }
}
