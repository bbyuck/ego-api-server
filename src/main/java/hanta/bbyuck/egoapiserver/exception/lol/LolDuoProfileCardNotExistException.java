package hanta.bbyuck.egoapiserver.exception.lol;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

/*
 * HANTA - Lol Duo ProfileCard Not Exist Exception class
 *
 * @ description : Lol 비즈니스 로직 관련 Custom Exception
 *
 *
 * @      author : 강혁(bbyuck) (k941026h@naver.com)
 * @       since : 2020. 01. 01
 * @ last update : 2021. 02. 22
 *
 * <Copyright 2020. 한타. All rights reserved.>
 */

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
