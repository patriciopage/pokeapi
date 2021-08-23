package com.example.PokeApi.repository.impl;

import com.example.PokeApi.PokeApiConfig;
import com.example.PokeApi.model.Pokemon;
import com.example.PokeApi.model.Species;
import com.example.PokeApi.repository.SpeciesRepository;
import com.example.PokeApi.repository.impl.mapper.SpeciesDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Repository
public class BasicSpeciesRepository implements SpeciesRepository {

    Logger logger = LoggerFactory.getLogger(BasicSpeciesRepository.class);

    private final HttpClient httpClient;
    private final ObjectMapper speciesMapper;
    private final String resourceUrl;

    public BasicSpeciesRepository(HttpClient httpClient, PokeApiConfig pokeApiConfig, ObjectMapper speciesMapper) {
        this.httpClient = httpClient;
        this.resourceUrl = pokeApiConfig.getBaseUrl() + pokeApiConfig.getSpeciesResource();
        this.speciesMapper = speciesMapper;
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Species.class, new SpeciesDeserializer());
        this.speciesMapper.registerModule(module);
    }

    @Override
    @Cacheable("species")
    public Species findSpeciesByName(String name) {
        String url = this.resourceUrl+name;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse response;
        Species species = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            species = speciesMapper.readValue(response.body().toString(), Species.class);
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }
        return species;
    }
}
