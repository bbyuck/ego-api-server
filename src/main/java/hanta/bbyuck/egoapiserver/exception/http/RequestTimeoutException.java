package hanta.bbyuck.egoapiserver.exception.http;

import org.springframework.http.HttpStatus;

public class RequestTimeoutException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "H-010";

    public RequestTimeoutException() {
        super();
    }

    public RequestTimeoutException(String msg) {
        super(msg);
    }

    public RequestTimeoutException(Throwable e) {
        super(e);
    }

    public RequestTimeoutException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.REQUEST_TIMEOUT;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
