package hanta.bbyuck.egoapiserver.exception;
import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class BadMatchRequestException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "ERR-REQ-888";
    private static final String ERR_MSG = "신청을 보내지 못함";

    public BadMatchRequestException() {
        super(ERR_MSG);
    }

    public BadMatchRequestException(Throwable e) {
        super(e);
    }

    public BadMatchRequestException(String errorMessage) {
        super(errorMessage);
    }

    public BadMatchRequestException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}

