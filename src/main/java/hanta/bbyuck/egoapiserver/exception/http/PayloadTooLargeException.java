package hanta.bbyuck.egoapiserver.exception.http;

import org.springframework.http.HttpStatus;

public class PayloadTooLargeException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "H-008";

    public PayloadTooLargeException() {
        super();
    }

    public PayloadTooLargeException(String msg) {
        super(msg);
    }

    public PayloadTooLargeException(Throwable e) {
        super(e);
    }

    public PayloadTooLargeException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.PAYLOAD_TOO_LARGE;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
