package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.UserStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolDuoProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.LolTier;
import hanta.bbyuck.egoapiserver.request.lol.LolDuoProfileCardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LolDuoProfileCardRepository {
    private final EntityManager em;
    private static final int MAX_CARD_RETURN = 50;

    @Transactional
    public void save(LolDuoProfileCard lolDuoProfileCard) {
        em.persist(lolDuoProfileCard);
    }

    public Boolean isExistSummonerName(String summonerName) {
        String query = "select count(dpc) from LolDuoProfileCard dpc where dpc.summonerName = :summonerName";
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
    public void update(LolDuoProfileCard lolDuoProfileCard, LolDuoProfileCardUpdateRequestDto requestDto) {
        lolDuoProfileCard.updateProfileCard(
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
                requestDto.getMainLolPosition());
    }

    public LolDuoProfileCard findById(Long id) {
       return em.find(LolDuoProfileCard.class, id);
    }

    public LolDuoProfileCard find(User owner) throws NoResultException{
        String query = "select ldpc from LolDuoProfileCard ldpc where ldpc.owner = :owner";
        return em.createQuery(query, LolDuoProfileCard.class)
                .setParameter("owner", owner)
                .getSingleResult();
    }

    public List<LolDuoProfileCard> findAll() {
        String query = "select ldpc from LolDuoProfileCard ldpc join fetch ldpc.owner";
        return em.createQuery(query, LolDuoProfileCard.class)
                .getResultList();
    }

    public List<LolDuoProfileCard> findAllOrderByActiveTime() {
        String query = "select ldpc from LolDuoProfileCard ldpc join fetch ldpc.owner order by ldpc.owner.lastActiveTime";
        return em.createQuery(query, LolDuoProfileCard.class)
                .setFirstResult(0)
                .setMaxResults(MAX_CARD_RETURN)
                .getResultList();
    }

    public List<LolDuoProfileCard> findCustomizedListV1(User owner) {
        LolDuoProfileCard reqUserCard = find(owner);

        /*
         * 1. 티어로 구분
         */
        String query = "";
        List<LolDuoProfileCard> deckList = new ArrayList<>();

        switch (reqUserCard.getTier()) {
            case I:
            case B:
                // I, B, S
                query = "select ldpc " +
                        "from LolDuoProfileCard ldpc " +
                        "where (ldpc.tier = :iron or ldpc.tier = :bronze or ldpc.tier = :silver) " +
                        "and (ldpc.owner.status = :active or ldpc.owner.status = :inactive) " +
                        "order by ldpc.owner.lastActiveTime";
                deckList = em.createQuery(query, LolDuoProfileCard.class)
                        .setParameter("iron", LolTier.I)
                        .setParameter("bronze", LolTier.B)
                        .setParameter("silver", LolTier.S)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setParameter("inactive", UserStatus.INACTIVE)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case S:
                // I, B, S, G
                query = "select ldpc " +
                        "from LolDuoProfileCard ldpc " +
                        "where (ldpc.tier = :iron or ldpc.tier = :bronze or ldpc.tier = :silver or ldpc.tier = :gold) " +
                        "and (ldpc.owner.status = :active or ldpc.owner.status = :inactive) " +
                        "order by ldpc.owner.lastActiveTime";
                deckList = em.createQuery(query, LolDuoProfileCard.class)
                        .setParameter("iron", LolTier.I)
                        .setParameter("bronze", LolTier.B)
                        .setParameter("silver", LolTier.S)
                        .setParameter("gold", LolTier.G)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setParameter("inactive", UserStatus.INACTIVE)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case G:
                // S, G, P
                query = "select ldpc " +
                        "from LolDuoProfileCard ldpc " +
                        "where (ldpc.tier = :silver or ldpc.tier = :gold or ldpc.tier = :platinum) " +
                        "and (ldpc.owner.status = :active or ldpc.owner.status = :inactive) " +
                        "order by ldpc.owner.lastActiveTime";
                deckList = em.createQuery(query, LolDuoProfileCard.class)
                        .setParameter("silver", LolTier.S)
                        .setParameter("gold", LolTier.G)
                        .setParameter("platinum", LolTier.P)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setParameter("inactive", UserStatus.INACTIVE)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case P:
                // G, P, if(P1 == D3, D4), if(P2 == D4)

                if(reqUserCard.getTierLev() == 1) {
                    query = "select ldpc " +
                            "from LolDuoProfileCard ldpc " +
                            "where (ldpc.tier = :gold " +
                            "or ldpc.tier = :platinum " +
                            "or (ldpc.tier = :diamond and (ldpc.tierLev = 3 or ldpc.tierLev = 4))) " +
                            "and (ldpc.owner.status = :active or ldpc.owner.status = :inactive) " +
                            "order by ldpc.owner.lastActiveTime";
                    deckList = em.createQuery(query, LolDuoProfileCard.class)
                            .setParameter("gold", LolTier.G)
                            .setParameter("platinum", LolTier.P)
                            .setParameter("diamond", LolTier.D)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setParameter("inactive", UserStatus.INACTIVE)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                else if(reqUserCard.getTierLev() == 2) {
                    query = "select ldpc " +
                            "from LolDuoProfileCard ldpc " +
                            "where (ldpc.tier = :gold or ldpc.tier = :platinum or (ldpc.tier = :diamond and ldpc.tierLev = 4)) " +
                            "and (ldpc.owner.status = :active or ldpc.owner.status = :inactive) " +
                            "order by ldpc.owner.lastActiveTime";
                    deckList = em.createQuery(query, LolDuoProfileCard.class)
                            .setParameter("gold", LolTier.G)
                            .setParameter("platinum", LolTier.P)
                            .setParameter("diamond", LolTier.D)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setParameter("inactive", UserStatus.INACTIVE)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                } else {
                    query = "select ldpc " +
                            "from LolDuoProfileCard ldpc " +
                            "where (ldpc.tier = :gold or ldpc.tier = :platinum) " +
                            "and (ldpc.owner.status = :active or ldpc.owner.status = :inactive) " +
                            "order by ldpc.owner.lastActiveTime";
                    deckList = em.createQuery(query, LolDuoProfileCard.class)
                            .setParameter("gold", LolTier.G)
                            .setParameter("platinum", LolTier.P)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setParameter("inactive", UserStatus.INACTIVE)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                break;
            case D:
                // if(D4 == P2, P1 - D4, D3, D2), if(D3 == P1, D4, D3, D2, D1), if(D2 == D4, D3, D2, D1, M1), if(D1 == D3, D2, D1, M, GM)
                if(reqUserCard.getTierLev() == 4) {
                    query = "select ldpc " +
                            "from LolDuoProfileCard ldpc " +
                            "where ((ldpc.tier = :platinum and (ldpc.tierLev = 2 or ldpc.tierLev = 1)) " +
                            "or (ldpc.tier =: diamond and (ldpc.tierLev = 4 or ldpc.tierLev = 3 or ldpc.tierLev = 2))) " +
                            "and (ldpc.owner.status = :active or ldpc.owner.status = :inactive) " +
                            "order by ldpc.owner.lastActiveTime";
                    deckList = em.createQuery(query, LolDuoProfileCard.class)
                            .setParameter("platinum", LolTier.P)
                            .setParameter("diamond", LolTier.D)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setParameter("inactive", UserStatus.INACTIVE)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                else if (reqUserCard.getTierLev() == 3) {
                    query = "select ldpc " +
                            "from LolDuoProfileCard ldpc " +
                            "where ((ldpc.tier = :platinum and ldpc.tierLev = 1) " +
                            "or ldpc.tier = :diamond) " +
                            "and (ldpc.owner.status = :active or ldpc.owner.status = :inactive) " +
                            "order by ldpc.owner.lastActiveTime";
                    deckList = em.createQuery(query, LolDuoProfileCard.class)
                            .setParameter("platinum", LolTier.P)
                            .setParameter("diamond", LolTier.D)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setParameter("inactive", UserStatus.INACTIVE)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                else if (reqUserCard.getTierLev() == 2) {
                    query = "select ldpc " +
                            "from LolDuoProfileCard ldpc " +
                            "where (ldpc.tier = :diamond or ldpc.tier = :master) " +
                            "and (ldpc.owner.status = :active or ldpc.owner.status = :inactive) " +
                            "order by ldpc.owner.lastActiveTime";
                    deckList = em.createQuery(query, LolDuoProfileCard.class)
                            .setParameter("diamond", LolTier.D)
                            .setParameter("master", LolTier.M)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setParameter("inactive", UserStatus.INACTIVE)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                else {
                    query = "select ldpc " +
                            "from LolDuoProfileCard ldpc " +
                            "where ((ldpc.tier = :diamond and (ldpc.tierLev = 3 or ldpc.tierLev = 2 or ldpc.tierLev = 1)) " +
                            "or ldpc.tier = :master " +
                            "or ldpc.tier = :grandmaster) " +
                            "and (ldpc.owner.status = :active or ldpc.owner.status = :inactive) " +
                            "order by ldpc.owner.lastActiveTime";
                    deckList = em.createQuery(query, LolDuoProfileCard.class)
                            .setParameter("diamond", LolTier.D)
                            .setParameter("master", LolTier.M)
                            .setParameter("grandmaster", LolTier.GM)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setParameter("inactive", UserStatus.INACTIVE)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                break;
            case M:
                // D2, D1, M, GM
                query = "select ldpc " +
                        "from LolDuoProfileCard ldpc " +
                        "where ((ldpc.tier = :diamond and (ldpc.tierLev = 2 or ldpc.tierLev = 1)) " +
                        "or ldpc.tier = :master " +
                        "or ldpc.tier = :grandmaster) " +
                        "and (ldpc.owner.status = :active or ldpc.owner.status = :inactive) " +
                        "order by ldpc.owner.lastActiveTime";
                deckList = em.createQuery(query, LolDuoProfileCard.class)
                        .setParameter("diamond", LolTier.D)
                        .setParameter("master", LolTier.M)
                        .setParameter("grandmaster", LolTier.GM)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setParameter("inactive", UserStatus.INACTIVE)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case GM:
                // D1, M, GM, C
                query = "select ldpc " +
                        "from LolDuoProfileCard ldpc " +
                        "where ((ldpc.tier = :diamond and ldpc.tierLev = 1) " +
                        "or ldpc.tier = :master " +
                        "or ldpc.tier = :grandmaster " +
                        "or ldpc.tier = :challenger) " +
                        "and (ldpc.owner.status = :active or ldpc.owner.status = :inactive) " +
                        "order by ldpc.owner.lastActiveTime";

                deckList = em.createQuery(query, LolDuoProfileCard.class)
                        .setParameter("diamond", LolTier.D)
                        .setParameter("master", LolTier.M)
                        .setParameter("grandmaster", LolTier.GM)
                        .setParameter("challenger", LolTier.C)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setParameter("inactive", UserStatus.INACTIVE)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case C:
                // GM, C
                query = "select ldpc " +
                        "from LolDuoProfileCard ldpc " +
                        "where (ldpc.tier = :grandmaster " +
                        "or ldpc.tier = :challenger) " +
                        "and (ldpc.owner.status = :active or ldpc.owner.status = :inactive) " +
                        "order by ldpc.owner.lastActiveTime";

                deckList = em.createQuery(query, LolDuoProfileCard.class)
                        .setParameter("grandmaster", LolTier.GM)
                        .setParameter("challenger", LolTier.C)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setParameter("inactive", UserStatus.INACTIVE)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            default:
                break;
        }

        return deckList;
    }
}
