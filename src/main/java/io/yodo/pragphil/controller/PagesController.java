package io.yodo.pragphil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PagesController {

    @GetMapping("/")
    public String getHomepage(HttpServletRequest req) {
        return "home";
    }
}
