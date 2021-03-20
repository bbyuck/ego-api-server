package hanta.bbyuck.egoapiserver.exception;

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

public class CAuthenticationEntryPointException extends AbstractResponseException {

    private static final String ERR_MSG = "유저 인증 에러 : 다시 로그인을 시도해주세요.";
    private static final String ERR_CODE = "AUTH-100";

    public CAuthenticationEntryPointException(String msg, Throwable t) {
        super(msg, t);
    }
    public CAuthenticationEntryPointException(String msg) {
        super(msg);
    }
    public CAuthenticationEntryPointException() {
        super(ERR_MSG);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public String getERR_CODE() {
        return ERR_CODE;
    }
}