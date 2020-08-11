package hanta.bbyuck.egoapiserver.exception.logical;

import hanta.bbyuck.egoapiserver.exception.http.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "U-100";

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }

    public UserNotFoundException(Throwable e) {
        super(e);
    }

    public UserNotFoundException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.OK;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
