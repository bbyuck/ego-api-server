package hanta.bbyuck.egoapiserver.exception.logical;

import hanta.bbyuck.egoapiserver.exception.http.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class UnknownException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "N-000";

    public UnknownException() {
        super();
    }

    public UnknownException(String msg) {
        super(msg);
    }

    public UnknownException(Throwable e) {
        super(e);
    }

    public UnknownException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
