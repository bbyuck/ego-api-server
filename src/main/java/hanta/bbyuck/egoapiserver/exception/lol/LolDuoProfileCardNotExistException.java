package hanta.bbyuck.egoapiserver.exception.lol;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class LolDuoProfileCardNotExistException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "LOL-DP-100";

    public LolDuoProfileCardNotExistException() {
        super();
    }

    public LolDuoProfileCardNotExistException(String msg) {
        super(msg);
    }

    public LolDuoProfileCardNotExistException(Throwable e) {
        super(e);
    }

    public LolDuoProfileCardNotExistException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
