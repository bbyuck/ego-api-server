package hanta.bbyuck.egoapiserver.repository;

import hanta.bbyuck.egoapiserver.domain.enumset.Game;
import hanta.bbyuck.egoapiserver.domain.enumset.SnsVendor;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.UserStatus;
import hanta.bbyuck.egoapiserver.exception.NoUserException;
import hanta.bbyuck.egoapiserver.util.AES256Util;
import hanta.bbyuck.egoapiserver.util.SHA256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
public class UserRepository {
    private final EntityManager em;


    @Transactional
    public void save(User user) {
        em.persist(user);
    }

    public User findById(Long userId) throws NoResultException {
        return em.find(User.class, userId);
    }

    // 성능 튜닝 필
    public User find(String userGeneratedId) throws NoResultException {
        String query = "select u from User u where u.generatedId = :generatedId";
        List<User> user = em.createQuery(query, User.class)
                .setParameter("generatedId", userGeneratedId)
                .getResultList();
        if (user.isEmpty()) throw new NoUserException();

        return user.get(0);
    }

    public User findBySnsId(SnsVendor snsVendor, String snsId) throws NoResultException {
        String query = "select u from User u where u.snsVendor = :snsVendor and u.snsId = :snsId";
        return em.createQuery(query, User.class)
                .setParameter("snsVendor", snsVendor)
                .setParameter("snsId", snsId)
                .getSingleResult();
    }

    @Transactional
    public void updateGame(User user, Game game) {
        user.updateGame(game);
    }

    @Transactional
    public void updateUserStatus(User user, UserStatus userStatus) {
        user.updateUserStatus(userStatus);
    }

    @Transactional
    public void updateLastActiveTime(User apiCaller) {
        apiCaller.updateActiveTime();
    }
}
