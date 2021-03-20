package hanta.bbyuck.egoapiserver.controller;

import hanta.bbyuck.egoapiserver.exception.CAccessDeniedException;
import hanta.bbyuck.egoapiserver.exception.CAuthenticationEntryPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
