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

public class MatchNotExistException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "ERR-MATCH-001";
    private static final String ERR_MSG = "해당 매치가 존재하지 않음";

    public MatchNotExistException() {
        super(ERR_MSG);
    }

    public MatchNotExistException(Throwable e) {
        super(e);
    }

    public MatchNotExistException(String errorMessage) {
        super(errorMessage);
    }

    public MatchNotExistException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}

