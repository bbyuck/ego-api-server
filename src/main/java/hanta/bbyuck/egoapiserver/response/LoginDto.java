package hanta.bbyuck.egoapiserver.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoginDto {
    private String generatedId;
    private String generatedPw;
    private String generatedIdForGet;
    private String userAuthToken;
    private List<String> privileges = new ArrayList<>();
}