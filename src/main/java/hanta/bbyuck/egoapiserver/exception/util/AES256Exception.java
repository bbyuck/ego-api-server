package hanta.bbyuck.egoapiserver.exception.util;

import hanta.bbyuck.egoapiserver.exception.http.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class AES256Exception extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "I-001";

    public AES256Exception() {
        super();
    }

    public AES256Exception(String msg) {
        super(msg);
    }

    public AES256Exception(Throwable e) {
        super(e);
    }

    public AES256Exception(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    public String getERR_CODE() { return ERR_CODE; }
}
