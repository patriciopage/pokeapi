package com.example.PokeApi.model;

import java.util.Objects;

public class EvolutionChain {

    private Integer id;
    private ChainLink baseLink;

    public EvolutionChain(Integer id, ChainLink baseLink) {
        this.id = id;
        this.baseLink = baseLink;
    }

    public Integer getId() {
        return id;
    }

    public ChainLink getBaseLink() {
        return baseLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EvolutionChain that = (EvolutionChain) o;
        return Objects.equals(id, that.id) && Objects.equals(baseLink, that.baseLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, baseLink);
    }
}
