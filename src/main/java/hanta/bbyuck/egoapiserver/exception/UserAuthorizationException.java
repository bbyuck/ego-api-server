package hanta.bbyuck.egoapiserver.exception;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

/*
 * <pre>
 * Copyright (c) 2020 HANTA
 * All rights reserved.
 *
 * This software is the proprietary information of HANTA
 * </pre>
 *
 * @ author 강혁(bbyuck) (k941026h@naver.com)
 * @ since  2020. 01. 01
 *
 * @History
 * <pre>
 * -----------------------------------------------------
 * 2020.01.01
 * bbyuck (k941026h@naver.com) 최초작성
 * -----------------------------------------------------
 * </pre>
 */

public class UserAuthorizationException extends AbstractResponseException {

    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "AUTH-101";
    private static final String ERR_MSG = "인증 실패 : 권한이 없습니다.";

    public UserAuthorizationException() {
        super();
    }

    public UserAuthorizationException(Throwable e) {
        super(e);
    }

    public UserAuthorizationException(String errorMessage) {
        super(ERR_MSG + errorMessage);
    }

    public UserAuthorizationException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

    public String getERR_CODE(){
        return ERR_CODE;
    }
}
