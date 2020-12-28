package hanta.bbyuck.egoapiserver.exception;

import org.springframework.http.HttpStatus;

public class RecommendRequestOverException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "ERR-REC-002";
    private static final String ERR_MSG = "추천 기능을 통해서는 한 번에 한 명의 유저에게만 매치 신청 보낼 수 있음";

    public RecommendRequestOverException() {
        super(ERR_MSG);
    }

    public RecommendRequestOverException(Throwable e) {
        super(e);
    }

    public RecommendRequestOverException(String errorMessage) {
        super(errorMessage);
    }

    public RecommendRequestOverException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}
