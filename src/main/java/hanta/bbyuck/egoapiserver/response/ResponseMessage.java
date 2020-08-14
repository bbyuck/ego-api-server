package hanta.bbyuck.egoapiserver.response;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import hanta.bbyuck.egoapiserver.response.error.ErrorMessage;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseMessage {

    private static final long serialVersionUID = 1L;

    private String code;
    private Boolean status;
    private String message;
    private LocalDateTime timestamp;
    private Object returnObj;
    private ErrorMessage error;

    public ResponseMessage(String msg, Object returnObj) {
        this.code = "success";
        this.status = true;
        this.message = msg;
        this.timestamp = LocalDateTime.now();
        this.returnObj = returnObj;
        this.error = null;
    }

    public ResponseMessage(String msg) {
        this.code = "success";
        this.status = true;
        this.message = msg;
        this.timestamp = LocalDateTime.now();
        this.returnObj = null;
        this.error = null;
    }

    public ResponseMessage(AbstractResponseException exception, String referedUrl) {
        this.returnObj = null;
        this.code = exception.getERR_CODE();
        this.status = false;
        this.message = null;
        this.error = new ErrorMessage(this.code, exception.getMessage(), referedUrl);
        this.timestamp = LocalDateTime.now();
    }


    public void envelop(Object dto) {
        this.returnObj = dto;
    }

    public void removeReturnObj() {
        this.returnObj = null;
    }
}