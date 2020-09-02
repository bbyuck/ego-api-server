package hanta.bbyuck.egoapiserver.firebase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class FcmRequest {
    private Boolean validate_only;
    private FcmMessage message;


    @Builder
    @AllArgsConstructor
    @Data
    public static class FcmMessage {
        private Notification notification;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Data
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }
}
