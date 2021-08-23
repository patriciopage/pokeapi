package com.example.PokeApi.repository;

import com.example.PokeApi.model.Pokemon;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PokemonRepository {

    public CompletableFuture<Pokemon> findPokemonByName(String name);
    public List<String> getAllPokemons(int limit, int offset);
}
