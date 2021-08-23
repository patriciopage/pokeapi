package com.example.PokeApi.model;

import java.util.List;
import java.util.Objects;

public class ChainLink {

    private String species;
    private List<ChainLink> evolvesTo;

    public ChainLink(String species, List<ChainLink> evolvesTo) {
        this.species = species;
        this.evolvesTo = evolvesTo;
    }

    public String getSpecies() {
        return species;
    }

    public List<ChainLink> getEvolvesTo() {
        return evolvesTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChainLink chainLink = (ChainLink) o;
        return Objects.equals(species, chainLink.species) && Objects.equals(evolvesTo, chainLink.evolvesTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(species, evolvesTo);
    }
}
