package hanta.bbyuck.egoapiserver.exception.lol;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class UserAuthenticationException extends AbstractResponseException {

    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "ALL-999";

    public UserAuthenticationException() {
        super();
    }

    public UserAuthenticationException(Throwable e) {
        super(e);
    }

    public UserAuthenticationException(String errorMessage) {
        super(errorMessage);
    }

    public UserAuthenticationException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
