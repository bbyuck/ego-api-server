package hanta.bbyuck.egoapiserver.response;

import hanta.bbyuck.egoapiserver.exception.AbstractResponseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ResponseMessage {

    private static final long serialVersionUID = 1L;

    private String code;
    private Boolean status;
    private String message;
    private LocalDateTime timestamp;
    private String referedURL;
    private Object returnObj;

    public ResponseMessage(String msg, String code ,Object returnObj) {
        this.code = code;
        this.status = true;
        this.message = msg;
        this.timestamp = LocalDateTime.now();
        this.returnObj = returnObj;
        this.referedURL = "";
    }

    public ResponseMessage(String msg, String code) {
        this.code = code;
        this.status = true;
        this.message = msg;
        this.timestamp = LocalDateTime.now();
        this.returnObj = returnObj;
        this.referedURL = "";
    }

    public ResponseMessage(String msg) {
        this.code = "success";
        this.status = true;
        this.message = msg;
        this.timestamp = LocalDateTime.now();
        this.returnObj = null;
        this.referedURL = "";
    }



    public ResponseMessage(AbstractResponseException exception, String referedUrl) {
        this.returnObj = null;
        this.code = exception.getERR_CODE();
        this.status = false;
        this.message = exception.getMessage();
        this.timestamp = LocalDateTime.now();
        this.referedURL = referedUrl;
    }


    public void envelop(Object dto) {
        this.returnObj = dto;
    }

    public void removeReturnObj() {
        this.returnObj = null;
    }
}