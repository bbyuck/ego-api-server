package hanta.bbyuck.egoapiserver.exception.lol;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class AlreadyMatchingException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "LOL-M-100";

    public AlreadyMatchingException() {
        super();
    }

    public AlreadyMatchingException(String msg) {
        super(msg);
    }

    public AlreadyMatchingException(Throwable e) {
        super(e);
    }

    public AlreadyMatchingException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}
