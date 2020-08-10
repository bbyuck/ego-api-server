package hanta.bbyuck.egoapiserver.repository;

import hanta.bbyuck.egoapiserver.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRepository {
    private final EntityManager em;

    @Transactional
    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }

    public User find(Long id) {
        return em.find(User.class, id);
    }

    public User findBySnsId(String snsId) {
        String jpql = "select u from User u where u.snsId = :snsId";
        List<User> findUser = em.createQuery(jpql, User.class)
                .setParameter("snsId", snsId)
                .getResultList();

        if(findUser.isEmpty()) {
            return null;
        } else {
            return findUser.get(0);
        }
    }
}
