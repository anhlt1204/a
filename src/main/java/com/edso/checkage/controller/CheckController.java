package com.edso.checkage.controller;

import com.edso.checkage.service.CheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CheckController {

    private final CheckService service;

    public CheckController(CheckService service) {
        this.service = service;
    }

    @GetMapping("/check")
    Boolean checkAge(@RequestParam String name, @RequestParam Integer age) throws InterruptedException {
        log.info("Run check");
        return service.runCheck(name, age);
    }
}
