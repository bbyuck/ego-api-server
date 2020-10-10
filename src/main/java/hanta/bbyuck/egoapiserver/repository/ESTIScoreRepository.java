package hanta.bbyuck.egoapiserver.repository;

import hanta.bbyuck.egoapiserver.domain.ESTIScore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ESTIScoreRepository {
    private final EntityManager em;

    @Transactional
    public void save(ESTIScore score) {
        em.persist(score);
    }
}
