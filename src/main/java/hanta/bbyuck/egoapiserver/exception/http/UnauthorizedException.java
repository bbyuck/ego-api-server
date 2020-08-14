package hanta.bbyuck.egoapiserver.exception.http;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "A-990";

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(Throwable e) {
        super(e);
    }

    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }

    public UnauthorizedException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
