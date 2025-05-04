package com.example.qysqaserver.entities.enums;

public enum Language {
    KAZ, RU, ENG;

    public static Language getLanguage(String language) {
        return switch (language.toUpperCase()) {
            case "KAZ" -> KAZ;
            case "RU" -> RU;
            default -> ENG;
        };
    }

}
