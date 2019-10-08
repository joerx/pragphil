package io.yodo.pragphil.formatters;

import io.yodo.pragphil.dao.UserDAO;
import io.yodo.pragphil.entity.User;
import org.springframework.format.Formatter;

import java.util.Locale;

public class IdToUserFormatter implements Formatter<User> {

//    private final UserDAO userDAO;

    public IdToUserFormatter() {
//        this.userDAO = userDAO;
    }

    @Override
    public User parse(String s, Locale locale) {
        if (s.equals("") || s.equals("0")) return null;
        int userId = Integer.parseInt(s);
        User u = new User();
        u.setId(userId);
        return u;
//        return userDAO.findById(userId);
    }

    @Override
    public String print(User user, Locale locale) {
        return Integer.toString(user.getId());
    }
}
