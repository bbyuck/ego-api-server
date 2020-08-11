package hanta.bbyuck.egoapiserver.repository;

import hanta.bbyuck.egoapiserver.domain.SnsVendor;
import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.exception.logical.UserAuthenticationException;
import hanta.bbyuck.egoapiserver.util.AES256Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.security.NoSuchAlgorithmException;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRepository {
    private final EntityManager em;
    private final AES256Util aes256Util;

    public void authCheck(User checkUser, String auth) {
        String query = "select u from User u where u.userAuth = :auth";
        User foundUser = em.createQuery(query, User.class)
                .setParameter("auth", auth)
                .getSingleResult();

        if(foundUser != checkUser) {
            throw new UserAuthenticationException("유저 인증 실패 : 잘못된 키 입력입니다.");
        }
    }

    @ExceptionHandler({NoSuchAlgorithmException.class,})
    @Transactional
    public String save(User user) {
        em.persist(user);
        String newAuth = aes256Util.encode(String.format("%019d", user.getId()));
        user.assignAuth(newAuth);

        return user.getUserAuth();
    }

    // 성능 튜닝 필
    public User find(String userAuth) throws NoResultException{
        String query = "select u from User u where u.userAuth = :userAuth";

        return em.createQuery(query, User.class)
                .setParameter("userAuth", userAuth)
                .getSingleResult();
    }

    public User findBySnsId(SnsVendor snsVendor, String snsId) throws NoResultException{
        String query = "select u from User u where u.snsVendor = :snsVendor and u.snsId = :snsId";
        return em.createQuery(query, User.class)
                .setParameter("snsVendor", snsVendor)
                .setParameter("snsId", snsId)
                .getSingleResult();
    }
}
