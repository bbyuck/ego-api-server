package hanta.bbyuck.egoapiserver.exception;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class UpdateFailureException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "ERR-UP-001";
    private static final String ERR_MSG = "업데이트에 실패";

    public UpdateFailureException() {
        super(ERR_MSG);
    }

    public UpdateFailureException(Throwable e) {
        super(e);
    }

    public UpdateFailureException(String errorMessage) {
        super(errorMessage);
    }

    public UpdateFailureException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}

