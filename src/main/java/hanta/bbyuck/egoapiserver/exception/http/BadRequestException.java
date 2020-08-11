package hanta.bbyuck.egoapiserver.exception.http;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "H-001";

    public BadRequestException() {
        super();
    }

    public BadRequestException(Throwable e) {
        super(e);
    }

    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }

    public BadRequestException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}