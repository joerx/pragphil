package io.yodo.pragphil.api.endpoints;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DemoApiController {

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public Map helloApi(Model model) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "hello");
        return response;
    }

    @RequestMapping(path = "/boom", method = RequestMethod.GET)
    public String unavailable(Model model) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
