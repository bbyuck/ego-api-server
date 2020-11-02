package hanta.bbyuck.egoapiserver.repository;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

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
