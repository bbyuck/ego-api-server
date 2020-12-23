package hanta.bbyuck.egoapiserver.repository.lol;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.domain.enumset.UserStatus;
import hanta.bbyuck.egoapiserver.domain.lol.LolProfileCard;
import hanta.bbyuck.egoapiserver.domain.lol.enumset.LolTier;
import hanta.bbyuck.egoapiserver.request.lol.LolProfileCardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true, noRollbackFor = Exception.class)
public class LolProfileCardRepository {
    private final EntityManager em;
    private static final int MAX_CARD_RETURN = 50;

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
        String query = "select lpc from LolProfileCard lpc join fetch lpc.owner order by lpc.owner.lastActiveTime";
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
            case I:
            case B:
                // I, B, S
                query = "select lpc " +
                        "from LolProfileCard lpc " +
                        "where (lpc.tier = :iron or lpc.tier = :bronze or lpc.tier = :silver) " +
                        "and (lpc.owner.status = :active) " +
                        "order by lpc.owner.lastActiveTime";
                deckList = em.createQuery(query, LolProfileCard.class)
                        .setParameter("iron", LolTier.I)
                        .setParameter("bronze", LolTier.B)
                        .setParameter("silver", LolTier.S)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case S:
                // I, B, S, G
                query = "select lpc " +
                        "from LolProfileCard lpc " +
                        "where (lpc.tier = :iron or lpc.tier = :bronze or lpc.tier = :silver or lpc.tier = :gold) " +
                        "and (lpc.owner.status = :active) " +
                        "order by lpc.owner.lastActiveTime";
                deckList = em.createQuery(query, LolProfileCard.class)
                        .setParameter("iron", LolTier.I)
                        .setParameter("bronze", LolTier.B)
                        .setParameter("silver", LolTier.S)
                        .setParameter("gold", LolTier.G)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case G:
                // S, G, P
                query = "select lpc " +
                        "from LolProfileCard lpc " +
                        "where (lpc.tier = :silver or lpc.tier = :gold or lpc.tier = :platinum) " +
                        "and (lpc.owner.status = :active) " +
                        "order by lpc.owner.lastActiveTime";
                deckList = em.createQuery(query, LolProfileCard.class)
                        .setParameter("silver", LolTier.S)
                        .setParameter("gold", LolTier.G)
                        .setParameter("platinum", LolTier.P)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case P:
                // G, P, if(P1 == D3, D4), if(P2 == D4)

                if(reqUserCard.getTierLev() == 1) {
                    query = "select lpc " +
                            "from LolProfileCard lpc " +
                            "where (lpc.tier = :gold " +
                            "or lpc.tier = :platinum " +
                            "or (lpc.tier = :diamond and (lpc.tierLev = 3 or lpc.tierLev = 4))) " +
                            "and (lpc.owner.status = :active) " +
                            "order by lpc.owner.lastActiveTime";
                    deckList = em.createQuery(query, LolProfileCard.class)
                            .setParameter("gold", LolTier.G)
                            .setParameter("platinum", LolTier.P)
                            .setParameter("diamond", LolTier.D)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                else if(reqUserCard.getTierLev() == 2) {
                    query = "select lpc " +
                            "from LolProfileCard lpc " +
                            "where (lpc.tier = :gold or lpc.tier = :platinum or (lpc.tier = :diamond and lpc.tierLev = 4)) " +
                            "and (lpc.owner.status = :active) " +
                            "order by lpc.owner.lastActiveTime";
                    deckList = em.createQuery(query, LolProfileCard.class)
                            .setParameter("gold", LolTier.G)
                            .setParameter("platinum", LolTier.P)
                            .setParameter("diamond", LolTier.D)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                } else {
                    query = "select lpc " +
                            "from LolProfileCard lpc " +
                            "where (lpc.tier = :gold or lpc.tier = :platinum) " +
                            "and (lpc.owner.status = :active) " +
                            "order by lpc.owner.lastActiveTime";
                    deckList = em.createQuery(query, LolProfileCard.class)
                            .setParameter("gold", LolTier.G)
                            .setParameter("platinum", LolTier.P)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                break;
            case D:
                // if(D4 == P2, P1 - D4, D3, D2), if(D3 == P1, D4, D3, D2, D1), if(D2 == D4, D3, D2, D1, M1), if(D1 == D3, D2, D1, M, GM)
                if(reqUserCard.getTierLev() == 4) {
                    query = "select lpc " +
                            "from LolProfileCard lpc " +
                            "where ((lpc.tier = :platinum and (lpc.tierLev = 2 or lpc.tierLev = 1)) " +
                            "or (lpc.tier =: diamond and (lpc.tierLev = 4 or lpc.tierLev = 3 or lpc.tierLev = 2))) " +
                            "and (lpc.owner.status = :active) " +
                            "order by lpc.owner.lastActiveTime";
                    deckList = em.createQuery(query, LolProfileCard.class)
                            .setParameter("platinum", LolTier.P)
                            .setParameter("diamond", LolTier.D)
                            .setParameter("active", UserStatus.ACTIVE)
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
                            "order by lpc.owner.lastActiveTime";
                    deckList = em.createQuery(query, LolProfileCard.class)
                            .setParameter("platinum", LolTier.P)
                            .setParameter("diamond", LolTier.D)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                else if (reqUserCard.getTierLev() == 2) {
                    query = "select lpc " +
                            "from LolProfileCard lpc " +
                            "where (lpc.tier = :diamond or lpc.tier = :master) " +
                            "and (lpc.owner.status = :active) " +
                            "order by lpc.owner.lastActiveTime";
                    deckList = em.createQuery(query, LolProfileCard.class)
                            .setParameter("diamond", LolTier.D)
                            .setParameter("master", LolTier.M)
                            .setParameter("active", UserStatus.ACTIVE)
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
                            "order by lpc.owner.lastActiveTime";
                    deckList = em.createQuery(query, LolProfileCard.class)
                            .setParameter("diamond", LolTier.D)
                            .setParameter("master", LolTier.M)
                            .setParameter("grandmaster", LolTier.GM)
                            .setParameter("active", UserStatus.ACTIVE)
                            .setFirstResult(0)
                            .setMaxResults(MAX_CARD_RETURN)
                            .getResultList();
                }
                break;
            case M:
                // D2, D1, M, GM
                query = "select lpc " +
                        "from LolProfileCard lpc " +
                        "where ((lpc.tier = :diamond and (lpc.tierLev = 2 or lpc.tierLev = 1)) " +
                        "or lpc.tier = :master " +
                        "or lpc.tier = :grandmaster) " +
                        "and (lpc.owner.status = :active) " +
                        "order by lpc.owner.lastActiveTime";
                deckList = em.createQuery(query, LolProfileCard.class)
                        .setParameter("diamond", LolTier.D)
                        .setParameter("master", LolTier.M)
                        .setParameter("grandmaster", LolTier.GM)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case GM:
                // D1, M, GM, C
                query = "select lpc " +
                        "from LolProfileCard lpc " +
                        "where ((lpc.tier = :diamond and lpc.tierLev = 1) " +
                        "or lpc.tier = :master " +
                        "or lpc.tier = :grandmaster " +
                        "or lpc.tier = :challenger) " +
                        "and (lpc.owner.status = :active) " +
                        "order by lpc.owner.lastActiveTime";

                deckList = em.createQuery(query, LolProfileCard.class)
                        .setParameter("diamond", LolTier.D)
                        .setParameter("master", LolTier.M)
                        .setParameter("grandmaster", LolTier.GM)
                        .setParameter("challenger", LolTier.C)
                        .setParameter("active", UserStatus.ACTIVE)
                        .setFirstResult(0)
                        .setMaxResults(MAX_CARD_RETURN)
                        .getResultList();
                break;
            case C:
                // GM, C
                query = "select lpc " +
                        "from LolProfileCard lpc " +
                        "where (lpc.tier = :grandmaster " +
                        "or lpc.tier = :challenger) " +
                        "and (lpc.owner.status = :active) " +
                        "order by lpc.owner.lastActiveTime";

                deckList = em.createQuery(query, LolProfileCard.class)
                        .setParameter("grandmaster", LolTier.GM)
                        .setParameter("challenger", LolTier.C)
                        .setParameter("active", UserStatus.ACTIVE)
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
