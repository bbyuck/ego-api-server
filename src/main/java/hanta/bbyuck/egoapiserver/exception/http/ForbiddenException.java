package hanta.bbyuck.egoapiserver.exception.http;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends AbstractResponseException{
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "H-003";

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(Throwable e) {
        super(e);
    }

    public ForbiddenException(String errorMessage) {
        super(errorMessage);
    }

    public ForbiddenException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}
