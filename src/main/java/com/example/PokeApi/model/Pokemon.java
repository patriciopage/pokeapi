package com.example.PokeApi.model;

import java.util.List;
import java.util.Objects;

public class Pokemon {

    private Integer id;
    private String name;
    private Integer height;
    private Integer weight;
    private List<Sprite> sprites;
    private List<String> types;
    private List<String> abilities;
    private String species;
    private String description;
    private EvolutionChain evolutionChain;

    public Pokemon(String name) {
        this.name = name;
    }

    public Pokemon(Integer id, String name, Integer height, Integer weight, List<Sprite> sprites, List<String> types, List<String> abilities, String species, String description, EvolutionChain evolutionChain) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.sprites = sprites;
        this.types = types;
        this.abilities = abilities;
        this.species = species;
        this.description = description;
        this.evolutionChain = evolutionChain;
    }

    public Integer getId() {
        return id;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWeight() {
        return weight;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }

    public List<String> getTypes() {
        return types;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public String getSpecies() {
        return species;
    }

    public String getDescription() {
        return description;
    }

    public EvolutionChain getChainLink() {
        return evolutionChain;
    }

    public String getName() {
        return this.name;
    }

    public Pokemon withDescription(String description) {
        this.description = description;
        return this;
    }

    public Pokemon withEvolutionChain(EvolutionChain evolutionChain) {
        this.evolutionChain = evolutionChain;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return Objects.equals(id, pokemon.id) && Objects.equals(name, pokemon.name) && Objects.equals(height, pokemon.height) && Objects.equals(weight, pokemon.weight) && Objects.equals(sprites, pokemon.sprites) && Objects.equals(species, pokemon.species);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, height, weight, sprites, species);
    }
}
