package hanta.bbyuck.egoapiserver.request;

import hanta.bbyuck.egoapiserver.domain.enumset.ESTIVersion;
import lombok.Data;

@Data
public class ESTITestAnswerRequestDto {
    private String userAuth;
    private ESTIVersion version;
    private Boolean answer_1;
    private Boolean answer_2;
    private Boolean answer_3;
    private Boolean answer_4;
    private Boolean answer_5;
    private Boolean answer_6;
    private Boolean answer_7;
    private Boolean answer_8;
    private Boolean answer_9;
    private Boolean answer_10;
    private Boolean answer_11;
    private Boolean answer_12;
    private Boolean answer_13;
}
