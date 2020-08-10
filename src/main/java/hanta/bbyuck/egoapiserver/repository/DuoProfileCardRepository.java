package hanta.bbyuck.egoapiserver.repository;

import hanta.bbyuck.egoapiserver.domain.DuoProfileCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DuoProfileCardRepository {
    private final EntityManager em;

    @Transactional
    public void save(DuoProfileCard duoProfileCard) {
        em.persist(duoProfileCard);
    }


}
