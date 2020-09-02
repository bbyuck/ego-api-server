package hanta.bbyuck.egoapiserver.exception.lol;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class AlreadyOwnProfileCardException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "LOL-DP-500";
    private static final String ERR_MESSAGE = "이미 듀오 프로필 카드를 보유하고 있습니다.";

    public AlreadyOwnProfileCardException() {
        super(ERR_MESSAGE);
    }

    public AlreadyOwnProfileCardException(Throwable e) {
        super(e);
    }

    public AlreadyOwnProfileCardException(String errorMessage) {
        super(errorMessage);
    }

    public AlreadyOwnProfileCardException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}
