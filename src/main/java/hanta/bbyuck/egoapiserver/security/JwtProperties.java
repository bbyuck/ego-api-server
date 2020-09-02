package hanta.bbyuck.egoapiserver.security;

public class JwtProperties {
    // 암호화 키
    public static final String SECRET_KEY = "bbyuck-kylpp2020";

    // 토큰 유효기간 15분
    public static final Long EXPIRATION_TIME = 15 * 60 * 1000L;
}
