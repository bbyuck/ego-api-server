package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.UserStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolTier;
import hanta.bbyuck.egoapiserver.dto.ReferralConditions;
import hanta.bbyuck.egoapiserver.exception.NoUserException;
import hanta.bbyuck.egoapiserver.request.lol.LolProfileCardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static hanta.bbyuck.egoapiserver.domain.lol.enumset.LolTier.bronze;
import static hanta.bbyuck.egoapiserver.domain.lol.enumset.LolTier.iron;

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
public class LolProfileCardRepository {
    private final EntityManager em;
    private static final int MAX_CARD_RETURN = 50;
    private static final int MAX_RECOMMENDATION_NUM = 3;
    @Transactional
    public void save(LolProfileCard lolProfileCard) {
        em.persist(lolProfileCard);
    }

    public Boolean isExistSummonerName(String summonerName) {
        String query = "select count(lpc) from LolProfileCard lpc where lpc.summonerName = :summonerName";
        Long summonerCount = em.createQuery(query, Long.class)
                .setParameter("summonerName", summonerName)
                .getSingleResult();
        return summonerCount != 0L;
    }

    public Boolean isExist(User owner) {
        try {
            find(owner);
            return true;
        } catch (NoResultException e){
            return false;
        }
    }

    @Transactional
    public void update(LolProfileCard lolProfileCard, LolProfileCardUpdateRequestDto requestDto) {
        lolProfileCard.updateProfileCard(
                requestDto.getVoice(),
                requestDto.getSummonerName(),
                requestDto.getTier(),
                requestDto.getTierLev(),
                requestDto.getLp(),
                requestDto.getChampion1(),
                requestDto.getChampion2(),
                requestDto.getChampion3(),
                requestDto.getTop(),
                requestDto.getJungle(),
                requestDto.getMid(),
                requestDto.getAd(),
                requestDto.getSupport(),
                requestDto.getMainLolPosition(),
                requestDto.getGameType());
    }

    public LolProfileCard findById(Long id) {
        return em.find(LolProfileCard.class, id);
    }

    public LolProfileCard find(User owner) throws NoResultException{
        String query = "select lpc from LolProfileCard lpc where lpc.owner = :owner";
        return em.createQuery(query, LolProfileCard.class)
                .setParameter("owner", owner)
                .getSingleResult();
    }

    public List<LolProfileCard> findAll() {
        String query = "select lpc from LolProfileCard lpc join fetch lpc.owner";
        return em.createQuery(query, LolProfileCard.class)
                .getResultList();
    }

    public List<LolProfileCard> findAllOrderByActiveTime() {
        String query = "select lpc from LolProfileCard lpc join fetch lpc.owner order by lpc.owner.lastActiveTime desc";
        return em.createQuery(query, LolProfileCard.class)
                .setFirstResult(0)
                .setMaxResults(MAX_CARD_RETURN)
                .getResultList();
    }

    @Transactional
    public void updateFavorite(LolProfileCard lolProfileCard,
                               LolTier favoriteTier,
                               Boolean favoriteTop,
                               Boolean favoriteJungle,
                               Boolean favoriteMid,
                               Boolean favoriteAd,
                               Boolean favoriteSupport) {
        lolProfileCard.updateFavorite(favoriteTier, favoriteTop, favoriteJungle, favoriteMid, favoriteAd, favoriteSupport);
    }

    // 시즌 10 (2020시즌)
    public List<LolProfileCard> findCustomizedListV1(User owner) {
        LolProfileCard reqUserCard = find(owner);

        /*
         * 1. 티어로 구분
         */
        String query = "";
        List<LolProfileCard> deckList = new ArrayList<>();

        switch (reqUserCard.getTier()) {
            case iron:
            case bronze:
                // I, B, S
                query = "select lpc " +
                        "from LolProfileCard lpc " +
                        "where (lpc.tier = :iron or lpc.tier = :bronze or lpc.tier = :silver) " +
                        "and (lpc.owner.status = :active) " +
                        "and (lpc.owner <> :owner) " +
                        "order by lpc.owner.lastActiveTime desc";
                deckList = em.createQuery(query, LolProfileCard.class)
                        .setParameter("iron", iron)
                        .setParameter("bronze", bronze)
                        .setParameter("silver", LolTier.silver)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setParameter("owner", owner)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case silver:
                // I, B, S, G
                query = "select lpc " +
                        "from LolProfileCard lpc " +
                        "where (lpc.tier = :iron or lpc.tier = :bronze or lpc.tier = :silver or lpc.tier = :gold) " +
                        "and (lpc.owner.status = :active) " +
                        "and (lpc.owner <> :owner) " +
                        "order by lpc.owner.lastActiveTime desc";
                deckList = em.createQuery(query, LolProfileCard.class)
                        .setParameter("iron", iron)
                        .setParameter("bronze", bronze)
                        .setParameter("silver", LolTier.silver)
                        .setParameter("gold", LolTier.gold)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setParameter("owner", owner)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case gold:
                // S, G, P
                query = "select lpc " +
                        "from LolProfileCard lpc " +
                        "where (lpc.tier = :silver or lpc.tier = :gold or lpc.tier = :platinum) " +
                        "and (lpc.owner.status = :active) " +
                        "and (lpc.owner <> :owner) " +
                        "order by lpc.owner.lastActiveTime desc";
                deckList = em.createQuery(query, LolProfileCard.class)
                        .setParameter("silver", LolTier.silver)
                        .setParameter("gold", LolTier.gold)
                        .setParameter("platinum", LolTier.platinum)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setParameter("owner", owner)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case platinum:
                // G, P, if(P1 == D3, D4), if(P2 == D4)

                if(reqUserCard.getTierLev() == 1) {
                    query = "select lpc " +
                            "from LolProfileCard lpc " +
                            "where (lpc.tier = :gold " +
                            "or lpc.tier = :platinum " +
                            "or (lpc.tier = :diamond and (lpc.tierLev = 3 or lpc.tierLev = 4))) " +
                            "and (lpc.owner.status = :active) " +
                            "and (lpc.owner <> :owner) " +
                            "order by lpc.owner.lastActiveTime desc";
                    deckList = em.createQuery(query, LolProfileCard.class)
                            .setParameter("gold", LolTier.gold)
                            .setParameter("platinum", LolTier.platinum)
                            .setParameter("diamond", LolTier.diamond)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setParameter("owner", owner)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                else if(reqUserCard.getTierLev() == 2) {
                    query = "select lpc " +
                            "from LolProfileCard lpc " +
                            "where (lpc.tier = :gold or lpc.tier = :platinum or (lpc.tier = :diamond and lpc.tierLev = 4)) " +
                            "and (lpc.owner.status = :active) " +
                            "and (lpc.owner <> :owner) " +
                            "order by lpc.owner.lastActiveTime desc";
                    deckList = em.createQuery(query, LolProfileCard.class)
                            .setParameter("gold", LolTier.gold)
                            .setParameter("platinum", LolTier.platinum)
                            .setParameter("diamond", LolTier.diamond)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setParameter("owner", owner)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                } else {
                    query = "select lpc " +
                            "from LolProfileCard lpc " +
                            "where (lpc.tier = :gold or lpc.tier = :platinum) " +
                            "and (lpc.owner.status = :active) " +
                            "and (lpc.owner <> :owner) " +
                            "order by lpc.owner.lastActiveTime desc";
                    deckList = em.createQuery(query, LolProfileCard.class)
                            .setParameter("gold", LolTier.gold)
                            .setParameter("platinum", LolTier.platinum)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setParameter("owner", owner)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                break;
            case diamond:
                // if(D4 == P2, P1 - D4, D3, D2), if(D3 == P1, D4, D3, D2, D1), if(D2 == D4, D3, D2, D1, M1), if(D1 == D3, D2, D1, M, GM)
                if(reqUserCard.getTierLev() == 4) {
                    query = "select lpc " +
                            "from LolProfileCard lpc " +
                            "where ((lpc.tier = :platinum and (lpc.tierLev = 2 or lpc.tierLev = 1)) " +
                            "or (lpc.tier =: diamond and (lpc.tierLev = 4 or lpc.tierLev = 3 or lpc.tierLev = 2))) " +
                            "and (lpc.owner.status = :active) " +
                            "and (lpc.owner <> :owner) " +
                            "order by lpc.owner.lastActiveTime desc";
                    deckList = em.createQuery(query, LolProfileCard.class)
                            .setParameter("platinum", LolTier.platinum)
                            .setParameter("diamond", LolTier.diamond)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setParameter("owner", owner)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                else if (reqUserCard.getTierLev() == 3) {
                    query = "select lpc " +
                            "from LolProfileCard lpc " +
                            "where ((lpc.tier = :platinum and lpc.tierLev = 1) " +
                            "or lpc.tier = :diamond) " +
                            "and (lpc.owner.status = :active) " +
                            "and (lpc.owner <> :owner) " +
                            "order by lpc.owner.lastActiveTime desc";
                    deckList = em.createQuery(query, LolProfileCard.class)
                            .setParameter("platinum", LolTier.platinum)
                            .setParameter("diamond", LolTier.diamond)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setParameter("owner", owner)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                else if (reqUserCard.getTierLev() == 2) {
                    query = "select lpc " +
                            "from LolProfileCard lpc " +
                            "where (lpc.tier = :diamond or lpc.tier = :master) " +
                            "and (lpc.owner.status = :active) " +
                            "and (lpc.owner <> :owner) " +
                            "order by lpc.owner.lastActiveTime desc";
                    deckList = em.createQuery(query, LolProfileCard.class)
                            .setParameter("diamond", LolTier.diamond)
                            .setParameter("master", LolTier.master)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setParameter("owner", owner)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                else {
                    query = "select lpc " +
                            "from LolProfileCard lpc " +
                            "where ((lpc.tier = :diamond and (lpc.tierLev = 3 or lpc.tierLev = 2 or lpc.tierLev = 1)) " +
                            "or lpc.tier = :master " +
                            "or lpc.tier = :grandmaster) " +
                            "and (lpc.owner.status = :active) " +
                            "and (lpc.owner <> :owner) " +
                            "order by lpc.owner.lastActiveTime desc";
                    deckList = em.createQuery(query, LolProfileCard.class)
                            .setParameter("diamond", LolTier.diamond)
                            .setParameter("master", LolTier.master)
                            .setParameter("grandmaster", LolTier.grandmaster)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setParameter("owner", owner)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                break;
            case master:
                // D2, D1, M, GM
                query = "select lpc " +
                        "from LolProfileCard lpc " +
                        "where ((lpc.tier = :diamond and (lpc.tierLev = 2 or lpc.tierLev = 1)) " +
                        "or lpc.tier = :master " +
                        "or lpc.tier = :grandmaster) " +
                        "and (lpc.owner.status = :active) " +
                        "and (lpc.owner <> :owner) " +
                        "order by lpc.owner.lastActiveTime desc";
                deckList = em.createQuery(query, LolProfileCard.class)
                        .setParameter("diamond", LolTier.diamond)
                        .setParameter("master", LolTier.master)
                        .setParameter("grandmaster", LolTier.grandmaster)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setParameter("owner", owner)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case grandmaster:
                // D1, M, GM, C
                query = "select lpc " +
                        "from LolProfileCard lpc " +
                        "where ((lpc.tier = :diamond and lpc.tierLev = 1) " +
                        "or lpc.tier = :master " +
                        "or lpc.tier = :grandmaster " +
                        "or lpc.tier = :challenger) " +
                        "and (lpc.owner.status = :active) " +
                        "and (lpc.owner <> :owner) " +
                        "order by lpc.owner.lastActiveTime desc";

                deckList = em.createQuery(query, LolProfileCard.class)
                        .setParameter("diamond", LolTier.diamond)
                        .setParameter("master", LolTier.master)
                        .setParameter("grandmaster", LolTier.grandmaster)
                        .setParameter("challenger", LolTier.challenger)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setParameter("owner", owner)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case challenger:
                // GM, C
                query = "select lpc " +
                        "from LolProfileCard lpc " +
                        "where (lpc.tier = :grandmaster " +
                        "or lpc.tier = :challenger) " +
                        "and (lpc.owner.status = :active) " +
                        "and (lpc.owner <> :owner) " +
                        "order by lpc.owner.lastActiveTime desc";

                deckList = em.createQuery(query, LolProfileCard.class)
                        .setParameter("grandmaster", LolTier.grandmaster)
                        .setParameter("challenger", LolTier.challenger)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setParameter("owner", owner)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            default:
                break;
        }

        return deckList;
    }

    public List<LolProfileCard> findReferralCard(ReferralConditions conditions) {
        String query = "select lpc " +
                "from LolProfileCard lpc " +
                "where lpc.owner <> : apiCaller and lpc.tier =: apiCallersTier " +
                "order by lpc.owner.lastActiveTime desc";

        System.out.println(conditions.getTier());

        List<LolProfileCard> referrals = em.createQuery(query, LolProfileCard.class)
                .setParameter("apiCaller", conditions.getApiCaller())
                .setParameter("apiCallersTier", conditions.getTier())
                .setFirstResult(0)
                .setMaxResults(MAX_RECOMMENDATION_NUM + 1)
                .getResultList();



        // 추천 유저가 존재하지 않음
        if (referrals.isEmpty()) throw new NoUserException();

        return referrals;
    }
}
