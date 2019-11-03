package io.yodo.pragphil.web.binding;

import io.yodo.pragphil.core.domain.entity.Role;
import io.yodo.pragphil.core.service.UserService;

import java.beans.PropertyEditorSupport;

public class UserRoleEditor extends PropertyEditorSupport {

    private final UserService userService;

    public UserRoleEditor(UserService userService) {
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
