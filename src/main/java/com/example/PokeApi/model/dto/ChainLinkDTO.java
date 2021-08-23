package com.example.PokeApi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public class ChainLinkDTO {

    private String species;
    private List<ChainLinkDTO> evolutionChain;

    public ChainLinkDTO(String species, List<ChainLinkDTO> evolutionChain) {
        this.species = species;
        this.evolutionChain = evolutionChain;
    }

    public String getSpecies() {
        return species;
    }

    @JsonProperty("chainLink")
    public List<ChainLinkDTO> getEvolutionChain() {
        return evolutionChain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChainLinkDTO that = (ChainLinkDTO) o;
        return Objects.equals(species, that.species) && Objects.equals(evolutionChain, that.evolutionChain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(species, evolutionChain);
    }
}
