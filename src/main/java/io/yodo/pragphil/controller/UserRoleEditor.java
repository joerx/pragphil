package io.yodo.pragphil.controller;

import io.yodo.pragphil.entity.Role;
import io.yodo.pragphil.service.UserService;

import java.beans.PropertyEditorSupport;
import java.util.logging.Logger;

public class UserRoleEditor extends PropertyEditorSupport {

    private final Logger log = Logger.getLogger(getClass().getName());

    UserRoleEditor() {}

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        log.info("Setting thing " + text);

        String[] parts = text.split(":");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];

        Role r = new Role();
        r.setRole(name);
        r.setId(id);
        setValue(r);
    }

    @Override
    public String getAsText() {
        Role r = (Role) super.getValue();
        log.info("Getting text for role " + r);
        return r.getId() + ":" + r.getRole();
    }
}
