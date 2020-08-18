package hanta.bbyuck.egoapiserver.security;

public class JwtProperties {
    public static final String SECRET_KEY = "bbyuck-kylpp2020";
    public static final Long EXPIRATION_TIME = 15 * 60 * 1000L;
    public static final String TOKEN_PREFIX = "HANTA-EGO ";
    public static final String HEADER_STRING = "Authorization";
}
