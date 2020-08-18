package hanta.bbyuck.egoapiserver.controller;

import hanta.bbyuck.egoapiserver.exception.CAccessDeniedException;
import hanta.bbyuck.egoapiserver.exception.CAuthenticationEntryPointException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

    @GetMapping(value = "/entrypoint")
    public void entrypointException() {
        throw new CAuthenticationEntryPointException();
    }

    @GetMapping(value = "/accessdenied")
    public void accessdeniedException() {
        throw new CAccessDeniedException("");
    }
}
