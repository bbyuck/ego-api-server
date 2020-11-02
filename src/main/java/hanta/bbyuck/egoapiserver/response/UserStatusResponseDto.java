package hanta.bbyuck.egoapiserver.response;

import hanta.bbyuck.egoapiserver.domain.enumset.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserStatusResponseDto {
    private UserStatus status;
    private LocalDateTime lastActiveTime;
}
