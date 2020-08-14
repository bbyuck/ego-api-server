package hanta.bbyuck.egoapiserver.config;

import hanta.bbyuck.egoapiserver.util.AES256Util;
import hanta.bbyuck.egoapiserver.util.SHA256Util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public AES256Util aes256Util() {
        return AES256Util.getInstance();
    }

    @Bean
    public SHA256Util sha256Util() { return new SHA256Util(); }
}
