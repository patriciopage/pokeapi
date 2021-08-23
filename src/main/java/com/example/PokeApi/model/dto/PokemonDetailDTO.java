package com.example.PokeApi.model.dto;

import java.util.Objects;

public class PokemonDetailDTO extends PokemonDTO{

    private String description;
    private ChainLinkDTO evolutionChain;

    public PokemonDetailDTO(String name) {
        super(name);
    }

    public PokemonDetailDTO(PokemonDTO pokemonDTO, String description, ChainLinkDTO evolutionChain) {
        super(pokemonDTO.getName(), pokemonDTO.getDefaultFrontSpriteUrl(), pokemonDTO.getHeight(), pokemonDTO.getWeight(), pokemonDTO.getAbilities(), pokemonDTO.getAbilities());
        this.description = description;
        this.evolutionChain = evolutionChain;
    }

    public String getDescription() {
        return description;
    }

    public ChainLinkDTO getEvolutionChain() {
        return evolutionChain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PokemonDetailDTO that = (PokemonDetailDTO) o;
        return Objects.equals(description, that.description) && Objects.equals(evolutionChain, that.evolutionChain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description, evolutionChain);
    }
}
