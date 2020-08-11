package hanta.bbyuck.egoapiserver.exception.lol;

import hanta.bbyuck.egoapiserver.exception.http.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class DuplicateSummonerNameException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "LOL-DP-501";

    public DuplicateSummonerNameException() {
        super();
    }

    public DuplicateSummonerNameException(String msg) {
        super(msg);
    }

    public DuplicateSummonerNameException(Throwable e) {
        super(e);
    }

    public DuplicateSummonerNameException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}
