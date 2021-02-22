package hanta.bbyuck.egoapiserver.controller;

import hanta.bbyuck.egoapiserver.exception.CAccessDeniedException;
import hanta.bbyuck.egoapiserver.exception.CAuthenticationEntryPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * HANTA - Exception Controller class
 *
 * @ description : Authentication (Spring security filter)관련 에러 핸들링 컨트롤러
 *
 * @      author : 강혁(bbyuck) (k941026h@naver.com)
 * @       since : 2020. 01. 01
 * @ last update : 2021. 02. 22
 *
 * <Copyright 2020. 한타. All rights reserved.>
 */

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExceptionController {

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/entrypoint")
    public void entrypointException() {
        throw new CAuthenticationEntryPointException();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(value = "/accessdenied")
    public void accessdeniedException() {
        throw new CAccessDeniedException("");
    }
}
