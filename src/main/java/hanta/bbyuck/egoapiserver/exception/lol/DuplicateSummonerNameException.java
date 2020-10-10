package hanta.bbyuck.egoapiserver.exception.lol;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class DuplicateSummonerNameException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "ERR-LDPC-002";
    private static final String ERR_MSG = "이미 등록된 소환사명";

    public DuplicateSummonerNameException() {
        super(ERR_MSG);
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
