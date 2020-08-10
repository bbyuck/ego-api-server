package hanta.bbyuck.egoapiserver.request;

import hanta.bbyuck.egoapiserver.domain.MatchingStatus;
import hanta.bbyuck.egoapiserver.domain.SnsVendor;
import lombok.Data;

@Data
public class UserAuthRequest {
    public SnsVendor snsVendor;
    public String snsId;
    public String email;
}
