package hanta.bbyuck.egoapiserver.repository;

import hanta.bbyuck.egoapiserver.domain.ESTITestAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ESTITestRepository {
    private final EntityManager em;

    public void save(ESTITestAnswer answer) {
        em.persist(answer);
    }
}
