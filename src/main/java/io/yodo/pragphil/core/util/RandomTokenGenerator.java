package io.yodo.pragphil.core.util;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomTokenGenerator {
    public static String generateRandomToken() {
        int length = 32;
        return RandomStringUtils.random(length, true, false);
    }
}
