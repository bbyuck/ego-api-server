package hanta.bbyuck.egoapiserver.exception.lol;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

/*
 * HANTA - Lol Duo Profile Card Update Exception class
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

public class LolDuoProfileCardUpdateException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "LOL-DP-101";

    public LolDuoProfileCardUpdateException() {
        super();
    }

    public LolDuoProfileCardUpdateException(String msg) {
        super(msg);
    }

    public LolDuoProfileCardUpdateException(Throwable e) {
        super(e);
    }

    public LolDuoProfileCardUpdateException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}
