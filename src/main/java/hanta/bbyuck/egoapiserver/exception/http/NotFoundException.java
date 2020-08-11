package hanta.bbyuck.egoapiserver.exception.http;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractResponseException{
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "H-007";

    public NotFoundException() {
        super();
    }

    public NotFoundException(Throwable e) {
        super(e);
    }

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public NotFoundException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
