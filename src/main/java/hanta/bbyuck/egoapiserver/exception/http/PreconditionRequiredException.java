package hanta.bbyuck.egoapiserver.exception.http;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class PreconditionRequiredException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "H-009";

    public PreconditionRequiredException() {
        super();
    }

    public PreconditionRequiredException(String msg) {
        super(msg);
    }

    public PreconditionRequiredException(Throwable e) {
        super(e);
    }

    public PreconditionRequiredException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.PRECONDITION_REQUIRED;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
