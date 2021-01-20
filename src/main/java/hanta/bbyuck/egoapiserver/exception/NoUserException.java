package hanta.bbyuck.egoapiserver.exception;

import org.springframework.http.HttpStatus;

public class NoUserException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "ERR-USER-987";
    private static final String ERR_MSG = "유저가 존재하지 않음";

    public NoUserException() {
        super(ERR_MSG);
    }

    public NoUserException(Throwable e) {
        super(e);
    }

    public NoUserException(String errorMessage) {
        super(errorMessage);
    }

    public NoUserException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}
