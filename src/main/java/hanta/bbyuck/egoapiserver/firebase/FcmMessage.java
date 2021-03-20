package hanta.bbyuck.egoapiserver.firebase;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
@Builder
@AllArgsConstructor
public class FcmMessage {
    private boolean validate_only;
    private Message message;


    @Data
    @Builder
    @AllArgsConstructor
    public static class Message {
        private Notification notification;
        private String token;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }
}
