package hanta.bbyuck.egoapiserver.repository;

import hanta.bbyuck.egoapiserver.domain.ESTITestAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ESTITestRepository {
    private final EntityManager em;

    @Transactional
    public void save(ESTITestAnswer answer) {
        em.persist(answer);
    }
}
