package io.yodo.pragphil.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;

@Controller
public class HealthcheckController {

    private final SessionFactory sessionFactory;

    @Autowired
    public HealthcheckController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @GetMapping("/healthz")
    @Transactional
    public String getHealthCheck() throws Exception {
        Session s = sessionFactory.getCurrentSession();
        s.createNativeQuery("select 1"); // assuming all is good if we get this far
        return "health";
    }
}
