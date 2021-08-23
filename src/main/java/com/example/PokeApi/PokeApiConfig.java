package com.example.PokeApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PokeApiConfig {

    @Value("${pokeapi.baseUrl}")
    private String baseUrl;
    @Value("${pokeapi.resource.pokemon}")
    private String pokemonResource;
    @Value("${pokeapi.resource.species}")
    private String speciesResource;
    @Value("${pokeapi.resource.evolutionChain}")
    private String chainLinkResource;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getPokemonResource() {
        return pokemonResource;
    }

    public String getSpeciesResource() {
        return speciesResource;
    }

    public String getChainLinkResource() {
        return chainLinkResource;
    }
}
