package com.example.PokeApi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Species {

    private Integer id;
    private String name;
    private String description;
    @JsonIgnore
    private String evolutionChainUrl;

    public Species(Integer id, String name, String description, String evolutionChainUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.evolutionChainUrl = evolutionChainUrl;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEvolutionChainUrl() {
        return evolutionChainUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Species species = (Species) o;
        return Objects.equals(id, species.id) && Objects.equals(name, species.name) && Objects.equals(description, species.description) && Objects.equals(evolutionChainUrl, species.evolutionChainUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, evolutionChainUrl);
    }
}
