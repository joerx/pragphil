package io.yodo.pragphil.core.security.api;

import io.yodo.pragphil.core.domain.entity.User;

public interface UserTokenService {
    User findByToken(Object token);
}
