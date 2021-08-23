package com.example.PokeApi.model;

import java.util.Objects;

public class Sprite {

    private SpriteType spriteType;
    private String spriteUrl;

    public Sprite(SpriteType spriteType, String spriteUrl) {
        this.spriteType = spriteType;
        this.spriteUrl = spriteUrl;
    }

    public SpriteType getSpriteType() {
        return spriteType;
    }

    public String getSpriteUrl() {
        return spriteUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sprite sprite = (Sprite) o;
        return spriteType == sprite.spriteType && Objects.equals(spriteUrl, sprite.spriteUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spriteType, spriteUrl);
    }
}
