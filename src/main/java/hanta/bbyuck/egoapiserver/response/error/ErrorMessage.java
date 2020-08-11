package hanta.bbyuck.egoapiserver.response.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {
    private static final long serialVersionUID = 1L;

    private String code;
    private String errorMessage;
    private String referedUrl;

    public ErrorMessage(String code, String errorMessage, String referedUrl) {
        this.code = code;
        this.errorMessage = errorMessage;
        this.referedUrl = referedUrl;
    }
}
