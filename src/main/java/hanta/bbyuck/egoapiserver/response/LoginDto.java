package hanta.bbyuck.egoapiserver.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
 * <pre>
 * Copyright (c) 2020 HANTA
 * All rights reserved.
 *
 * This software is the proprietary information of HANTA
 * </pre>
 *
 * @ author 강혁(bbyuck) (k941026h@naver.com)
 * @ since  2020. 01. 01
 *
 * @History
 * <pre>
 * -----------------------------------------------------
 * 2020.01.01
 * bbyuck (k941026h@naver.com) 최초작성
 * -----------------------------------------------------
 * </pre>
 */

@Data
public class LoginDto {
    private String generatedId;
    private String generatedPw;
    private String generatedIdForGet;
    private String userAuthToken;
    private List<String> privileges = new ArrayList<>();
}