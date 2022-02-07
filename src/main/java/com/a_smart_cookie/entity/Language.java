package com.a_smart_cookie.entity;

import java.util.Arrays;

public enum Language {
    UKRAINIAN("ua"),
    ENGLISH("eng");

    private final String abbr;

    Language(String abbr) {
        this.abbr = abbr;
    }

    public static Language fromString(String inputAbbr) throws IllegalArgumentException {
        return Arrays.stream(Language.values())
                .filter(language -> language.abbr.equals(inputAbbr))
                .findFirst()
                .orElse(UKRAINIAN);
    }
}
