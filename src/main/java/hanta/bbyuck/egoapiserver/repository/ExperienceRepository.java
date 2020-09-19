package hanta.bbyuck.egoapiserver.repository;

import hanta.bbyuck.egoapiserver.domain.Experience;
import hanta.bbyuck.egoapiserver.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

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
