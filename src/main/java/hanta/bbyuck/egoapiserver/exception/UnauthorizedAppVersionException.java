package hanta.bbyuck.egoapiserver.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedAppVersionException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "APP-VER-000";
    private static final String ERR_MSG = "인증 실패 : 잘못된 애플리케이션 버전입니다. 업데이트를 진행해주세요.";

    public UnauthorizedAppVersionException() {
        super(ERR_MSG);
    }

    public UnauthorizedAppVersionException(Throwable e) {
        super(e);
    }

    public UnauthorizedAppVersionException(String errorMessage) {
        super(errorMessage);
    }

    public UnauthorizedAppVersionException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }


}
