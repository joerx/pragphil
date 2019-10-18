package io.yodo.pragphil.security.acl;

import io.yodo.pragphil.entity.User;
import io.yodo.pragphil.security.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentAccess {

    private final AuthHelper authHelper;

    @Autowired
    public StudentAccess(AuthHelper authHelper) {
        this.authHelper = authHelper;
    }

    public boolean canList(Object principal) {
        User user = authHelper.getUserForPrincipal(principal);
        if (user == null) return false;
        return user.isAdmin() || user.isStudent();
    }

    public boolean isAdmin(Object principal, int studentId) {
        User user = authHelper.getUserForPrincipal(principal);
        if (user == null) return false;
        return user.isAdmin() || (user.isStudent() && user.getId() == studentId);
    }
}
