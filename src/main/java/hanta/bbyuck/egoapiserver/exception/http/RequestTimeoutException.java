package hanta.bbyuck.egoapiserver.exception.http;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

/*
 * HANTA - Request Timeout Exception class
 *
 * @ description : http 프로토콜 관련 Custom Exception
 *
 *
 * @      author : 강혁(bbyuck) (k941026h@naver.com)
 * @       since : 2020. 01. 01
 * @ last update : 2021. 02. 22
 *
 * <Copyright 2020. 한타. All rights reserved.>
 */

public class RequestTimeoutException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "H-010";

    public RequestTimeoutException() {
        super();
    }

    public RequestTimeoutException(String msg) {
        super(msg);
    }

    public RequestTimeoutException(Throwable e) {
        super(e);
    }

    public RequestTimeoutException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.REQUEST_TIMEOUT;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
