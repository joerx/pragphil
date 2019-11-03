package io.yodo.pragphil.web.view.helper;

import io.yodo.pragphil.core.domain.entity.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserHelper {

    public String listRoles(List<Role> roles) {
        List<String> names = roles.stream()
                .map(Role::getName)
                .map(s -> s.replace("ROLE_", ""))
                .sorted()
                .filter(s -> !s.equals("MEMBER"))
                .collect(Collectors.toList());
        return String.join(", ", names);
    }
}
