package com.test.online.store.common.model.helper;

public abstract class SlugHelper {
    public static String toSlug(String input) {
        if (input == null) {
            return null;
        }
        return input.toLowerCase().replaceAll("\\s+", "-");
    }
}
