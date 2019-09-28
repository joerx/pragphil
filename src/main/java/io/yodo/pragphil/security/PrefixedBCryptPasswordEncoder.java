package io.yodo.pragphil.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PrefixedBCryptPasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence charSequence) {
        return "{bcrypt}" + bCryptPasswordEncoder.encode(charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String unprefixed = s.replace("{bcrypt}", "");
        return bCryptPasswordEncoder.matches(charSequence, unprefixed);
    }
}
