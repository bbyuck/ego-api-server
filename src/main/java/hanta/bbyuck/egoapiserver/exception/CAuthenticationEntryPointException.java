package hanta.bbyuck.egoapiserver.exception;

import org.springframework.http.HttpStatus;

public class CAuthenticationEntryPointException extends AbstractResponseException {

    private static final String ERR_MSG = "유저 인증 에러 : 다시 로그인을 시도해주세요.";
    private static final String ERR_CODE = "AUTH-100";

    public CAuthenticationEntryPointException(String msg, Throwable t) {
        super(msg, t);
    }
    public CAuthenticationEntryPointException(String msg) {
        super(msg);
    }
    public CAuthenticationEntryPointException() {
        super(ERR_MSG);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public String getERR_CODE() {
        return ERR_CODE;
    }
}