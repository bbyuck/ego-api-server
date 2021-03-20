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

public class CAccessDeniedException extends AbstractResponseException{
    private static final String ERR_MSG = "유저 권한 에러 : 해당 리소스에 대한 권한이 없습니다.";
    private static final String ERR_CODE = "AUTH-200";

    public CAccessDeniedException(String msg, Throwable t) {
        super(msg, t);
    }
    public CAccessDeniedException(String msg) {
        super(msg);
    }
    public CAccessDeniedException() {
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
