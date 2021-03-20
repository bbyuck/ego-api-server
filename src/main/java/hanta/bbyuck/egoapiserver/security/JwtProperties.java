package hanta.bbyuck.egoapiserver.security;

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

public class JwtProperties {
    // 암호화 키
    public static final String SECRET_KEY = "bbyuck-kylpp2020";

    // 토큰 유효기간 15분
    public static final Long EXPIRATION_TIME = 15 * 60 * 1000L;
}
