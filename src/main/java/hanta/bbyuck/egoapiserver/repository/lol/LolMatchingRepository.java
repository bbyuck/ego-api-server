package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.MatchType;
import hanta.bbyuck.egoapiserver.domain.lol.LolMatching;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolMatchingStatus;
import hanta.bbyuck.egoapiserver.exception.http.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
public class LolMatchingRepository {
    private final EntityManager em;

    @Transactional
    public void save(LolMatching matching) {
        em.persist(matching);
    }

    @Transactional
    public void remove(LolMatching matching) {
        em.remove(matching);
    }

    @Transactional
    public void setDuoFinishTime(LolMatching matching) { matching.setFinishTime(); }

    @Transactional
    public void setDuoOffTime(LolMatching matching) { matching.setOffTime(); }


    @Transactional
    public void setDuoMatchingStatus(LolMatching matching, LolMatchingStatus status) { matching.setMatchingStatus(status);}

    public LolMatching findDuoMatching(User user) {
        String query = "select ldm from LolMatching ldm where (ldm.requester = :user or ldm.respondent = :user) " +
                "and (ldm.matchingStatus = :matching or ldm.matchingStatus = :matching_on or ldm.matchingStatus = :matching_off) " +
                "and (ldm.matchType = :match_type)";
        return em.createQuery(query, LolMatching.class)
                .setParameter("user", user)
                .setParameter("matching", LolMatchingStatus.MATCHING)
                .setParameter("matching_on", LolMatchingStatus.MATCHING_ON)
                .setParameter("matching_off", LolMatchingStatus.MATCHING_OFF)
                .setParameter("match_type", MatchType.DUO)
                .getSingleResult();
    }

    public Boolean isExist(User requester, User respondent) {
        String query = "select ldm from LolMatching ldm where ldm.requester = : req and ldm.respondent = : res " +
                "and (ldm.matchingStatus = : matching or ldm.matchingStatus = : matching_on or ldm.matchingStatus = : matching_off)";
        List<LolMatching> resultList = em.createQuery(query, LolMatching.class)
                .setParameter("req", requester)
                .setParameter("res", respondent)
                .setParameter("matching", LolMatchingStatus.MATCHING)
                .setParameter("matching_on", LolMatchingStatus.MATCHING_ON)
                .setParameter("matching_off", LolMatchingStatus.MATCHING_OFF)
                .getResultList();

        return !resultList.isEmpty();
    }

    public LolMatching findById(Long id) {
        return em.find(LolMatching.class, id);
    }

    public Long findMyMatching(User apiCaller) {
        String query = "select lm from LolMatching lm where (lm.requester = : me or lm.respondent = : me) and (lm.matchingStatus <> : finish and lm.matchingStatus <> : cancel) ";
        List<LolMatching> match = em.createQuery(query, LolMatching.class)
                .setParameter("me", apiCaller)
                .setParameter("finish", LolMatchingStatus.FINISHED)
                .setParameter("cancel", LolMatchingStatus.CANCEL)
                .getResultList();

        if (match.isEmpty()) throw new BadRequestException("매치 존재하지 않음");

        return match.get(0).getId();
    }
}
