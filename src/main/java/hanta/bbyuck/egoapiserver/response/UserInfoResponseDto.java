package hanta.bbyuck.egoapiserver.response;

import hanta.bbyuck.egoapiserver.domain.enumset.Game;
import lombok.Data;

@Data
public class UserInfoResponseDto {

    private Long id;
    private Game game;
}
