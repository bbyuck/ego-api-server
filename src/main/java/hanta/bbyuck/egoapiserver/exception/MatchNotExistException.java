package hanta.bbyuck.egoapiserver.exception;
import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class MatchNotExistException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "ERR-MATCH-001";
    private static final String ERR_MSG = "해당 매치가 존재하지 않음";

    public MatchNotExistException() {
        super(ERR_MSG);
    }

    public MatchNotExistException(Throwable e) {
        super(e);
    }

    public MatchNotExistException(String errorMessage) {
        super(errorMessage);
    }

    public MatchNotExistException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}

