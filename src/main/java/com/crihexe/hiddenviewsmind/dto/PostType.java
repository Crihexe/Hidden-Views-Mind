package com.crihexe.hiddenviewsmind.dto;

public enum PostType {
    IMAGE(null),
    CAROUSEL("CAROUSEL"),
    REEL("REELS"),
    STORY("STORIES"),
    ;

    private String name;

    private PostType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
