package hanta.bbyuck.egoapiserver.exception.http;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class LockedException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "H-004";

    public LockedException() {
        super();
    }

    public LockedException(String msg) {
        super(msg);
    }

    public LockedException(Throwable e) {
        super(e);
    }

    public LockedException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.LOCKED;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}
