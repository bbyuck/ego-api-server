package hanta.bbyuck.egoapiserver.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired UserRepository userRepository;

    @Test
    public void single_test() throws Exception {
        // given
        userRepository.find("2323");
        // when

        // then
    }
}