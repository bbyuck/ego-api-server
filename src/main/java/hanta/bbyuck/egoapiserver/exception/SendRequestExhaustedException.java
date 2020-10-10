package hanta.bbyuck.egoapiserver.exception;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;
public class SendRequestExhaustedException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "ERR-REQ-001";
    private static final String ERR_MSG = "보낼 수 있는 요청 횟수를 모두 소모";

    public SendRequestExhaustedException() {
        super(ERR_MSG);
    }

    public SendRequestExhaustedException(Throwable e) {
        super(e);
    }

    public SendRequestExhaustedException(String errorMessage) {
        super(errorMessage);
    }

    public SendRequestExhaustedException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}

