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

public abstract class AbstractResponseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AbstractResponseException() {
        super();
    }

    public AbstractResponseException(String msg) {
        super(msg);
    }

    public AbstractResponseException(Throwable e) {
        super(e);
    }

    public AbstractResponseException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public abstract HttpStatus getHttpStatus();

    public abstract String getERR_CODE();
}
