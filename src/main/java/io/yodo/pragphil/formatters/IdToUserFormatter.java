package io.yodo.pragphil.formatters;

import io.yodo.pragphil.entity.User;
import io.yodo.pragphil.service.UserService;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class IdToUserFormatter implements Formatter<User> {

    private final UserService userService;

    public IdToUserFormatter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User parse(String s, Locale locale) {
        if (s.equals("") || s.equals("0")) return null;
        int userId = Integer.parseInt(s);
        return userService.findById(userId);
    }

    @Override
    public String print(User user, Locale locale) {
        return Integer.toString(user.getId());
    }
}
