package hanta.bbyuck.egoapiserver.exception.http;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class MethodNotAllowedException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "H-005";

    public MethodNotAllowedException() {
        super();
    }

    public MethodNotAllowedException(Throwable e) {
        super(e);
    }

    public MethodNotAllowedException(String errorMessage) {
        super(errorMessage);
    }

    public MethodNotAllowedException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.METHOD_NOT_ALLOWED;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
