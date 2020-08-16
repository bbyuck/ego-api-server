package hanta.bbyuck.egoapiserver.util;

import hanta.bbyuck.egoapiserver.exception.UnauthorizedAppVersionException;
import hanta.bbyuck.egoapiserver.exception.http.UnauthorizedException;

import java.util.*;

public class ClientVersionManager {
    private static volatile ClientVersionManager INSTANCE;

    private final static Map<String, Boolean> AUTHORIZED_CLIENT_VERSION_INFO = Collections.unmodifiableMap(new HashMap<String, Boolean>() {
        {
            put("v1.00", true);
            put("v1.01", true);
            put("v1.02", true);
        }
    });

    public static ClientVersionManager getInstance() {
        if (INSTANCE == null) {
            synchronized (AES256Util.class) {
                if (INSTANCE == null)
                    INSTANCE = new ClientVersionManager();
            }
        }
        return INSTANCE;
    }

    private ClientVersionManager() {}

    public void checkClientVersion(String clientVersion) {

        if (AUTHORIZED_CLIENT_VERSION_INFO.get(clientVersion) == null) {
            throw new UnauthorizedAppVersionException();
        }
    }
}
