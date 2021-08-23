package com.example.PokeApi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonDTO {

    public String name;
    public String defaultFrontSpriteUrl;
    public Integer height;
    public Integer weight;
    public List<String> types;
    public List<String> abilities;

    public PokemonDTO(String name) {
    }

    public PokemonDTO(String name, String defaultFrontSpriteUrl, Integer height, Integer weight, List<String> types, List<String> abilities) {
        this.name = name;
        this.defaultFrontSpriteUrl = defaultFrontSpriteUrl;
        this.height = height;
        this.weight = weight;
        this.types = types;
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }

    public String getDefaultFrontSpriteUrl() {
        return defaultFrontSpriteUrl;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public List<String> getTypes() {
        return types;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokemonDTO that = (PokemonDTO) o;
        return Objects.equals(name, that.name) && Objects.equals(defaultFrontSpriteUrl, that.defaultFrontSpriteUrl) && Objects.equals(height, that.height) && Objects.equals(weight, that.weight) && Objects.equals(types, that.types) && Objects.equals(abilities, that.abilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, defaultFrontSpriteUrl, height, weight, types, abilities);
    }
}
