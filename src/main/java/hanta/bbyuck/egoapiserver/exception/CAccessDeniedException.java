package hanta.bbyuck.egoapiserver.exception;

import org.springframework.http.HttpStatus;

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
