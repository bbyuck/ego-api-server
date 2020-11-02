package hanta.bbyuck.egoapiserver.repository;

import hanta.bbyuck.egoapiserver.domain.EgoTestAnswer;
import hanta.bbyuck.egoapiserver.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

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
