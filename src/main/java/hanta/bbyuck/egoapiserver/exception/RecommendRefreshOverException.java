package hanta.bbyuck.egoapiserver.exception;

import org.springframework.http.HttpStatus;

public class RecommendRefreshOverException extends AbstractResponseException {
    private static final long serialVersionUID = 1L;
    private static final String ERR_CODE = "ERR-REC-001";
    private static final String ERR_MSG = "하루에 2번만 새로고침 가능";

    public RecommendRefreshOverException() {
        super(ERR_MSG);
    }

    public RecommendRefreshOverException(Throwable e) {
        super(e);
    }

    public RecommendRefreshOverException(String errorMessage) {
        super(errorMessage);
    }

    public RecommendRefreshOverException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    public String getERR_CODE() {
        return ERR_CODE;
    }
}
