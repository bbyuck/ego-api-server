package hanta.bbyuck.egoapiserver.util;

import hanta.bbyuck.egoapiserver.exception.UnauthorizedAppVersionException;
import hanta.bbyuck.egoapiserver.exception.http.UnauthorizedException;

import java.util.*;

/*
 * <pre>
 * Copyright (c) 2020 HANTA
 * All rights reserved.
 *
 * This software is the proprietary information of HANTA
 * </pre>
 *
 * @ author 강혁(bbyuck) (k941026h@naver.com)
 * @ since  2020. 01. 01
 *
 * @History
 * <pre>
 * -----------------------------------------------------
 * 2020.01.01
 * bbyuck (k941026h@naver.com) 최초작성
 * -----------------------------------------------------
 * </pre>
 */

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
