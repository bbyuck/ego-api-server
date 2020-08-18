package hanta.bbyuck.egoapiserver.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LoginDto {
    private String id;
    private String generatedId;
    private String generatedPw;
    private List<String> privileges = new ArrayList<>();
}