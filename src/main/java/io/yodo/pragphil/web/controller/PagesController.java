package io.yodo.pragphil.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PagesController {

    @GetMapping("/")
    public String getHomepage() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/access-denied", method = {RequestMethod.GET, RequestMethod.POST})
    public String accessDenied() {
        return "access-denied";
    }
}
