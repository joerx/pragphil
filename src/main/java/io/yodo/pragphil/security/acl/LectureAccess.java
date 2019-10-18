package io.yodo.pragphil.security.acl;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.User;
import io.yodo.pragphil.security.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LectureAccess {

    private final AuthHelper authHelper;

    @Autowired
    public LectureAccess(AuthHelper authHelper) {
        this.authHelper = authHelper;
    }

    public boolean isAdmin(Object principal, Lecture lecture) {
        User user = authHelper.getUserForPrincipal(principal);
        if (user == null) return false;
        return user.isAdmin() || (user.isLecturer() && user.equals(lecture.getLecturer()));
    }

    public boolean canList(Object principal) {
        User user = authHelper.getUserForPrincipal(principal);
        if (user == null) return false;
        return user.isAdmin() || user.isLecturer();
    }
}
