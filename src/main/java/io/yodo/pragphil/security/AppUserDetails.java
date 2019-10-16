package io.yodo.pragphil.security;

import io.yodo.pragphil.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface AppUserDetails extends UserDetails  {

    User getUser();
}
