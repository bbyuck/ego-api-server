package hanta.bbyuck.egoapiserver.service;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.UserAuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String join(UserAuthRequest request) {
        User user = new User();
        user.createUser(request.getSnsVendor(),
                request.getSnsId(),
                request.getEmail());
        user.updateLoginTime();
        userRepository.save(user);

        return user.getUserAuth();
    }

    public String login(UserAuthRequest request) {
        try {
            User user = userRepository.findBySnsId(request.getSnsVendor(), request.getSnsId());
            user.updateLoginTime();
            return user.getUserAuth();
        }
        catch (NoResultException e) {
            return join(request);
        }
    }
}
