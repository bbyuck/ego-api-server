package hanta.bbyuck.egoapiserver.response.dto;

import hanta.bbyuck.egoapiserver.domain.Position;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DuoProfileCardResponse {
    private Boolean voice;
    private String summonerName;
    private String tier;
    private Integer tierLev;
    private Integer lp;
    private String champion1;
    private String champion2;
    private String champion3;
    private Boolean top;
    private Boolean jungle;
    private Boolean mid;
    private Boolean ad;
    private Boolean support;
    private Position mainPosition;
}
