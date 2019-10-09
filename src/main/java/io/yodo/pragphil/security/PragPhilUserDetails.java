package io.yodo.pragphil.security;

import io.yodo.pragphil.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface PragPhilUserDetails extends UserDetails  {

    User getUser();
}
