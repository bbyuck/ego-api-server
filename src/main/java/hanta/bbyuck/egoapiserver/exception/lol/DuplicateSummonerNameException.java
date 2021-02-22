package hanta.bbyuck.egoapiserver.exception.lol;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import org.springframework.http.HttpStatus;

/*
 * HANTA - Duplicate Summoner Name Exception class
 *
 * @ description : Lol 비즈니스 로직 관련 Custom Exception
 *
 *
 * @      author : 강혁(bbyuck) (k941026h@naver.com)
 * @       since : 2020. 01. 01
 * @ last update : 2021. 02. 22
 *
 * <Copyright 2020. 한타. All rights reserved.>
 */

public class DuplicateSummonerNameException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "ERR-LDPC-002";
    private static final String ERR_MSG = "이미 등록된 소환사명";

    public DuplicateSummonerNameException() {
        super(ERR_MSG);
    }

    public DuplicateSummonerNameException(String msg) {
        super(msg);
    }

    public DuplicateSummonerNameException(Throwable e) {
        super(e);
    }

    public DuplicateSummonerNameException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}
