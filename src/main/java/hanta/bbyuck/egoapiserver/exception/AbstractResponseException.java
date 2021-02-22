package hanta.bbyuck.egoapiserver.exception;

import org.springframework.http.HttpStatus;



public abstract class AbstractResponseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AbstractResponseException() {
        super();
    }

    public AbstractResponseException(String msg) {
        super(msg);
    }

    public AbstractResponseException(Throwable e) {
        super(e);
    }

    public AbstractResponseException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public abstract HttpStatus getHttpStatus();

    public abstract String getERR_CODE();
}
