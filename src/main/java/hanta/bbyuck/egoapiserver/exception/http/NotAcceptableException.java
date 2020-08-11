package hanta.bbyuck.egoapiserver.exception.http;

import org.springframework.http.HttpStatus;

public class NotAcceptableException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "H-006";

    public NotAcceptableException() {
        super();
    }

    public NotAcceptableException(String msg) {
        super(msg);
    }

    public NotAcceptableException(Throwable e) {
        super(e);
    }

    public NotAcceptableException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_ACCEPTABLE;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
