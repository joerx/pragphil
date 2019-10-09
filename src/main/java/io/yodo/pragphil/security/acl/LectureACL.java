package io.yodo.pragphil.security.acl;

import io.yodo.pragphil.entity.Lecture;
import io.yodo.pragphil.entity.RoleName;
import io.yodo.pragphil.entity.User;
import io.yodo.pragphil.security.PrincipalHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class LectureACL {

    private final Logger log = Logger.getLogger(getClass().getName());

    public boolean isAdmin(Lecture lecture, UserDetails principal) {
        User user = PrincipalHelper.getUser(principal);
        if (user.hasRole(RoleName.ROLE_ADMIN)) return true;
        if (!user.hasRole(RoleName.ROLE_LECTURER)) return false;

        log.info("Checking access for user " + user + " to lecture " + lecture + " by " + lecture.getLecturer());

        return user.equals(lecture.getLecturer());
    }

    public boolean canList(UserDetails principal) {
        User user = PrincipalHelper.getUser(principal);
        return user.hasRole(RoleName.ROLE_LECTURER) || user.hasRole(RoleName.ROLE_LECTURER);
    }
}
