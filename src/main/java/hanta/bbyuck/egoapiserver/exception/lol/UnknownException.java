package hanta.bbyuck.egoapiserver.exception.lol;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

/*
 * HANTA - Unknown Exception class
 *
 * @ description : Lol 비즈니스 로직 관련 Custom Exception
 *                 로그 확인해서 발생시 에러 원인 체크 필요
 *
 * @      author : 강혁(bbyuck) (k941026h@naver.com)
 * @       since : 2020. 01. 01
 * @ last update : 2021. 02. 22
 *
 * <Copyright 2020. 한타. All rights reserved.>
 */

public class UnknownException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "UNKNOWN-000";

    public UnknownException() {
        super();
    }

    public UnknownException(String msg) {
        super(msg);
    }

    public UnknownException(Throwable e) {
        super(e);
    }

    public UnknownException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
