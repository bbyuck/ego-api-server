package hanta.bbyuck.egoapiserver.api;

import com.google.firebase.auth.FirebaseAuthException;
import hanta.bbyuck.egoapiserver.firebase.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

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

@RestController
@RequestMapping(value = "/api/v0.0.1/")
@RequiredArgsConstructor
public class PushAlarmTestController {
    private static final String TEST_TOKEN = "czL3HnKHOUKxjaLT-tRtCk:APA91bGkTobZb6HUCfAghMMlDtO-4ReXp_1Mims4P82uE4jJs3dyEsxeuFT-JfLgA4KxGCTKw1o8arZ_0hD-R25azRTS0FHGGbuBMgDmJKuzKaw7TZzk5bw5jyQi4gl4ijMxmzlAfndY";
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

    @PostMapping("test")
    public String testMethod2(){
        try{
            fcmService.cancelUserToken(TEST_TOKEN);
        }
        catch (FirebaseAuthException e) {
            e.printStackTrace();
            return "error";
        }
        return "성공";

    }
}
