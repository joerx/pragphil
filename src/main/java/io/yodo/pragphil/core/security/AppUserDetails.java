package io.yodo.pragphil.core.security;

import io.yodo.pragphil.core.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface AppUserDetails extends UserDetails  {

    User getUser();
}
