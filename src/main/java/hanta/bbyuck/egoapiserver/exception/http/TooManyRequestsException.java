package hanta.bbyuck.egoapiserver.exception.http;

import org.springframework.http.HttpStatus;

public class TooManyRequestsException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "H-011";

    public TooManyRequestsException() {
        super();
    }

    public TooManyRequestsException(String msg) {
        super(msg);
    }

    public TooManyRequestsException(Throwable e) {
        super(e);
    }

    public TooManyRequestsException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.TOO_MANY_REQUESTS;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
