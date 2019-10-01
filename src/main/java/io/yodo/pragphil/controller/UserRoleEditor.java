package io.yodo.pragphil.controller;

import io.yodo.pragphil.entity.Role;
import io.yodo.pragphil.service.UserService;

import java.beans.PropertyEditorSupport;
import java.util.logging.Logger;

public class UserRoleEditor extends PropertyEditorSupport {

    private final Logger log = Logger.getLogger(getClass().getName());

    private final UserService userService;

    UserRoleEditor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        int roleId = Integer.parseInt(text);
        setValue(userService.findRoleById(roleId));
    }

    @Override
    public String getAsText() {
        Role r = (Role) super.getValue();
        return r.getId() + "";
    }
}
