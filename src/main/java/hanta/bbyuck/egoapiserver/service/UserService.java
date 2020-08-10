package hanta.bbyuck.egoapiserver.service;

import hanta.bbyuck.egoapiserver.domain.User;
import hanta.bbyuck.egoapiserver.repository.UserRepository;
import hanta.bbyuck.egoapiserver.request.UserAuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Long join(UserAuthRequest request) {
        User user = new User();
        user.createUser(request.getSnsVendor(),
                request.getSnsId(),
                request.getEmail());
        userRepository.save(user);

        return user.getId();
    }

    public Long login(UserAuthRequest request) {
        User user = userRepository.findBySnsId(request.getSnsId());
        
        if(user == null) {
            return join(request);
        } else {
            return user.getId();
        }
    }

}
