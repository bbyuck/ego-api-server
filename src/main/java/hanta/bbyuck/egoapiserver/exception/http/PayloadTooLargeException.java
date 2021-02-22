package hanta.bbyuck.egoapiserver.exception.http;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

/*
 * HANTA - Payload Too Large Exception class
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

public class PayloadTooLargeException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "H-008";

    public PayloadTooLargeException() {
        super();
    }

    public PayloadTooLargeException(String msg) {
        super(msg);
    }

    public PayloadTooLargeException(Throwable e) {
        super(e);
    }

    public PayloadTooLargeException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.PAYLOAD_TOO_LARGE;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
