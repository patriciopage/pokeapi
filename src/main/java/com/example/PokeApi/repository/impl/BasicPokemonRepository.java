package com.example.PokeApi.repository.impl;

import com.example.PokeApi.PokeApiConfig;
import com.example.PokeApi.model.Pokemon;
import com.example.PokeApi.repository.PokemonRepository;
import com.example.PokeApi.repository.impl.mapper.PokemonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Repository
public class BasicPokemonRepository implements PokemonRepository {

    Logger logger = LoggerFactory.getLogger(BasicPokemonRepository.class);

    private final HttpClient httpClient;
    private final ObjectMapper pokemonMapper;
    private final String resourceUrl;

    public BasicPokemonRepository(HttpClient httpClient, PokeApiConfig pokeApiConfig, ObjectMapper pokemonMapper) {
        this.httpClient = httpClient;
        this.resourceUrl = pokeApiConfig.getBaseUrl()+pokeApiConfig.getPokemonResource();
        this.pokemonMapper = pokemonMapper;
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Pokemon.class, new PokemonDeserializer());
        this.pokemonMapper.registerModule(module);
    }

    @Override
    @Cacheable("pokemon")
    @Async
    public CompletableFuture<Pokemon> findPokemonByName(String name) {
        String url = this.resourceUrl+name;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        Pokemon pokemon = null;
        try {
            HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            pokemon = pokemonMapper.readValue(response.body().toString(), Pokemon.class);
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }
        return CompletableFuture.completedFuture(pokemon);
    }

    @Override
    @Cacheable("pokemons")
    public List<String> getAllPokemons(int limit, int offset) {
        String url = this.resourceUrl+"?limit="+limit+"&offset="+offset;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        List<String> pokemons = new ArrayList<>();
        try {
            HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            pokemons = pokemonMapper.readTree(response.body().toString())
                    .get("results")
                    .findValues("name").stream()
                    .map(JsonNode::asText)
                    .collect(Collectors.toList());
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }
        return pokemons;
    }
}
