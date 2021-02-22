package hanta.bbyuck.egoapiserver.util;

public class ResponseCode {

    // 공통 에러
    public static final String USER_AUTH_ERR = "ERR-AUTH-100";
    public static final String AUTHORITY_ERR = "ERR-AUTH-101";
    public static final String APP_VER_ERR = "ERR-APP-000";

    // UserController - success
    public static final String AUTH_SUCCESS = "AUTH-OBJ-001";
    public static final String FCM_TOKEN_UPDATE_SUCCESS = "AUTH-NONE-002";
    public static final String GAME_SELECT_SUCCESS = "GAME-NONE-001";
    public static final String LAST_SELECT_GAME_SUCCESS = "GAME-OBJ-001";
    public static final String GET_STATUS_SUCCESS = "USER-OBJ-002";
    public static final String OPPONENT_EVALUATION_SUCCESS = "USER-NONE-002";
    public static final String ESTI_APP_VER_SUCCESS = "EGO-OBJ-001";
    public static final String ESTI_WEB_VER_SUCCESS = "EGO-OBJ-002";

    // UserController - fail
    public static final String GAME_SELECT_FAIL = "ERR-GAME-001";

    // LolProfileCardController - success
    public static final String DUO_PROFILE_CARD_MAKE_SUCCESS = "LDPC-OBJ-001";

}
