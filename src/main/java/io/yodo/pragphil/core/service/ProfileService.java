package io.yodo.pragphil.core.service;

import io.yodo.pragphil.core.entity.User;
import io.yodo.pragphil.core.entity.UserProfile;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ProfileService {

    @PreAuthorize("isAuthenticated()")
    UserProfile findPublicProfile(String username);

    @PreAuthorize("principal.username == #username")
    UserProfile findPrivateProfile(String username);
}
