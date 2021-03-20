package hanta.bbyuck.egoapiserver.exception.lol;

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

public class AlreadyOwnProfileCardException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "ERR-LDPC-001";
    private static final String ERR_MESSAGE = "이미 듀오 프로필 카드를 보유하고 있습니다.";

    public AlreadyOwnProfileCardException() {
        super(ERR_MESSAGE);
    }

    public AlreadyOwnProfileCardException(Throwable e) {
        super(e);
    }

    public AlreadyOwnProfileCardException(String errorMessage) {
        super(errorMessage);
    }

    public AlreadyOwnProfileCardException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}
