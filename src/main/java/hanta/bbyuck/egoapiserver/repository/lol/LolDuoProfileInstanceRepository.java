package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoFinishedMatching;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoProfileInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LolDuoProfileInstanceRepository {
    private final EntityManager em;

    @Transactional
    public Long save(LolDuoProfileInstance instance) {
        em.persist(instance);
        return instance.getId();
    }

    public List<LolDuoProfileInstance> findInstances(LolDuoFinishedMatching finishedMatching) {
        String query = "select ldpi from LolDuoProfileInstance ldpi where ldpi.relatedLolDuoFinishedMatching = :matching";
        return em.createQuery(query, LolDuoProfileInstance.class)
                .setParameter("matching", finishedMatching)
                .getResultList();
    }
}
