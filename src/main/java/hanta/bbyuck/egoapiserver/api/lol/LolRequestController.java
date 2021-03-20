package hanta.bbyuck.egoapiserver.api.lol;

import hanta.bbyuck.egoapiserver.request.lol.LolRequestDto;
import hanta.bbyuck.egoapiserver.request.lol.LolRequestGetDto;
import hanta.bbyuck.egoapiserver.response.ResponseMessage;
import hanta.bbyuck.egoapiserver.response.lol.LolProfileCardDeckDto;
import hanta.bbyuck.egoapiserver.service.lol.LolRequestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value = "/user/api/v0.0.1/lol/duo")
@RequiredArgsConstructor
public class LolRequestController {
    private final LolRequestService lolRequestService;

    @ApiOperation(value = "롤 듀오 신청",
            notes = "롤 듀오 신청 보내기 API\n" +
                    "1. 듀오 신청을 보낸 시점에서 유저가 이미 매칭이 성사 되었으면(status 변경) 실패 response 반납\n" +
                    "2. 듀오 신청을 보낸 시점에서 유저가 이미 MAX_REQUEST_COUNT만큼의 요청을 받은 상태라면 실패 response 반납\n" +
                    "3. 해당 실패 response 받으면 클라이언트단에서 해당 유저 카드를 현재 보여지는 덱에서 빼고 한 명의 유저를 추가로 보여줌\n" +
                    "4. 만일 클라이언트로 받은 모든 유저덱을 소진했다면 새로운 프로필카드 덱 요청")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "opponentProfileCardId", value = "요청받는 유저의 프로필 카드 id (Number 숫자타입)", defaultValue = "13"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/match-request")
    public ResponseMessage sendRequest(@RequestBody LolRequestDto lolRequestDto) {
        lolRequestService.sendRequest(lolRequestDto);
        return new ResponseMessage("Match Request Send API Call Success!", "LDR-NONE-001");
    }


    @ApiOperation(value = "롤 듀오 보낸 신청 목록",
            notes = "롤 듀오 보낸 신청 목록 가져오기 API\n" +
                    "1. 신청을 받은 유저가 프로필 카드 수정시 변경 사항 적용\n" +
                    "2. 현재 MAX_REQUEST_COUNT = 5\n" +
                    "3. 신청 받은 유저 정보 대신 유저의 프로필 카드 정보 리턴" +
                    "4. 가공 처리 된 프로필 카드 정보(아이디 가려짐)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/sent-requests")
    public ResponseMessage getSentRequest(@RequestParam String clientVersion,
                                          @RequestParam String generatedId) {
        LolRequestGetDto requestDto = new LolRequestGetDto();
        requestDto.setClientVersion(clientVersion);
        requestDto.setGeneratedId(generatedId);
        LolProfileCardDeckDto sentRequestDeck = lolRequestService.getSendRequest(requestDto);
        return new ResponseMessage("Sent Request List Get API Call Success", "LDR-OBJ-001", sentRequestDeck);
    }

    @ApiOperation(value = "롤 듀오 받은 신청 목록",
            notes = "롤 듀오 받은 신청 목록 가져오기 API\n" +
                    "1. 신청을 받은 유저가 프로필 카드 수정시 변경 사항 적용\n" +
                    "2. 현재 MAX_REQUEST_COUNT = 5\n" +
                    "3. 신청 보낸 유저 정보 대신 유저의 프로필 카드 정보 리턴" +
                    "4. 가공 처리 된 프로필 카드 정보(아이디 가려짐)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/received-requests")
    public ResponseMessage getReceivedRequest(@RequestParam String clientVersion,
                                              @RequestParam String generatedId) {
        LolRequestGetDto requestDto = new LolRequestGetDto();
        requestDto.setClientVersion(clientVersion);
        requestDto.setGeneratedId(generatedId);
        LolProfileCardDeckDto receivedRequestDeck = lolRequestService.getReceiveRequest(requestDto);
        return new ResponseMessage("Sent Request List Get API Call Success", "LDR-OBJ-002" ,receivedRequestDeck);
    }

    @ApiOperation(value = "롤 듀오 보낸 신청 취소",
            notes = "롤 듀오 보낸 요청 취소하기 API\n" +
                    "1. 단건 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "opponentProfileCardId", value = "대상 유저 프로필 카드 id", defaultValue = "51"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/sent-request")
    public ResponseMessage cancelRequest(@RequestBody LolRequestDto requestDto) {
        lolRequestService.cancelRequest(requestDto);
        return new ResponseMessage("Cancel Sent Request API Call Success", "LDR-NONE-002");
    }

    @ApiOperation(value = "롤 듀오 받은 요청 거절",
            notes = "롤 듀오 받은 요청 거절하기 API\n" +
                    "1. 단건 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "opponentProfileCardId", value = "대상 유저 프로필 카드 id", defaultValue = "51"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/received-request")
    public ResponseMessage rejectRequest(@RequestBody LolRequestDto requestDto) {
        lolRequestService.rejectRequest(requestDto);
        return new ResponseMessage("Reject Received Request API Call Success", "LDR-NONE-003");
    }

    @ApiOperation(value = "롤 듀오 보낸 요청 전체 삭제",
            notes = "롤 듀오 보낸 요청 전체 삭제하기 API\n" +
                    "1. 전체 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/all-sent-request")
    public ResponseMessage cancelAllRequest(@RequestBody LolRequestDto requestDto) {
        lolRequestService.cancelAllRequest(requestDto);
        return new ResponseMessage("Cancel All Sent Request API Call Success", "LDR-NONE-004");
    }

    @ApiOperation(value = "롤 듀오 받은 요청 전체 거절",
            notes = "롤 듀오 받은 요청 전체 거절하기 API\n" +
                    "1. 전체 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @PostMapping("/all-received-request")
    public ResponseMessage rejectAllRequest(@RequestBody LolRequestDto requestDto) {
        lolRequestService.rejectAllRequest(requestDto);
        return new ResponseMessage("Reject All Received Request API Call Success", "LDR-NONE-005");
    }


    @ApiOperation(value = "롤 듀오 받은 요청 전체 거절",
            notes = "롤 듀오 받은 요청 전체 거절하기 API\n" +
                    "1. 전체 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "generatedId", value = "회원가입 및 로그인시 제공받은 Id", defaultValue = "sdsnadnsao21n3o1ni3o1"),
            @ApiImplicitParam(name = "clientVersion", value = "클라이언트 애플리케이션 버전", defaultValue = "v1.00"),
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/sent-request-count")
    public ResponseMessage sentRequestCount(@RequestParam String generatedId,
                                            @RequestParam String clientVersion) {
        LolRequestGetDto requestDto = new LolRequestGetDto();
        requestDto.setClientVersion(clientVersion);
        requestDto.setGeneratedId(generatedId);

        Integer sentRequestCount = lolRequestService.getSentRequestCount(requestDto);

        return new ResponseMessage("롤 듀오 보낸 신청 갯수", "LDR-OBJ-003", sentRequestCount);
    }
}
