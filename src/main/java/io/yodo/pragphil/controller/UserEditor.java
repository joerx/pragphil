package io.yodo.pragphil.controller;

import io.yodo.pragphil.entity.User;
import io.yodo.pragphil.service.UserService;

import java.beans.PropertyEditorSupport;
import java.util.logging.Logger;

public class UserEditor extends PropertyEditorSupport {

    private final UserService userService;

    private final Logger log = Logger.getLogger(getClass().getName());

    UserEditor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        log.info("Getting user for id " + text);
        int userId = Integer.parseInt(text);
        setValue(userService.findById(userId));
    }

    @Override
    public String getAsText() {
        User u = (User) super.getValue();
        log.info("Getting user " + u + " as text");
        if (u != null) {
            return u.getId() + "";
        } else {
            return "0";
        }
    }
}
