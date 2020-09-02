package hanta.bbyuck.egoapiserver.api;

import hanta.bbyuck.egoapiserver.firebase.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/v0.0.1/")
@RequiredArgsConstructor
public class PushAlarmTestController {
    private static final String TEST_TOKEN = "cXV79iqyh7k:APA91bEBuKmVIwOZnZbcnpGz9JM9_iXQpaKhak1kjkXHvAcCsXIPgmQczeFl3EvLTSoPfp7tkEIFLVZXc2KNFbJzAB4GreY6UgiHEx2c7V0dPv5OerSsoacIU58rdFfXxTYvEcKn4u89";
    private final FcmService fcmService;

    @PostMapping("alarm")
    public String testMethod() {
        try {
            fcmService.sendMessageTo(TEST_TOKEN, "hi", "plz");
        }
        catch(IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "성공";
    }

}
