package io.yodo.pragphil.api.auth;

import io.yodo.pragphil.entity.User;

public interface UserTokenService {
    User findByToken(Object token);
}
