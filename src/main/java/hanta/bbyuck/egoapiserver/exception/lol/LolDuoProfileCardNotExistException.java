package hanta.bbyuck.egoapiserver.exception.lol;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

public class LolDuoProfileCardNotExistException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "ERR-LDPC-003";
    private static final String ERR_MSG = "프로필카드가 없습니다.";

    public LolDuoProfileCardNotExistException() {
        super(ERR_MSG);
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
