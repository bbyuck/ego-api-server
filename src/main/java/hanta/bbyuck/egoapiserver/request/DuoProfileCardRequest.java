package hanta.bbyuck.egoapiserver.request;

import lombok.Data;

@Data
public class DuoProfileCardRequest {
    Long duoProfileCardId;
    String ownerAuth;
}
