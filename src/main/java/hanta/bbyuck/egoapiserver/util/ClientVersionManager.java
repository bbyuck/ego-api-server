package hanta.bbyuck.egoapiserver.util;

import hanta.bbyuck.egoapiserver.exception.UnauthorizedAppVersionException;
import hanta.bbyuck.egoapiserver.exception.http.UnauthorizedException;

import java.util.*;

public class ClientVersionManager {
    private final static Map<String, Boolean> AUTHORIZED_CLIENT_VERSION_INFO = Collections.unmodifiableMap(new HashMap<String, Boolean>() {
        {
            put("v1.00", true);
            put("v1.01", true);
            put("v1.02", true);
        }
    });

    public static void checkClientVersion(String clientVersion) {
        if (AUTHORIZED_CLIENT_VERSION_INFO.get(clientVersion) == null) {
            throw new UnauthorizedAppVersionException();
        }
    }
}
