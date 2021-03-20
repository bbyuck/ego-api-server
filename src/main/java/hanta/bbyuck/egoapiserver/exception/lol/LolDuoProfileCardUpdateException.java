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
