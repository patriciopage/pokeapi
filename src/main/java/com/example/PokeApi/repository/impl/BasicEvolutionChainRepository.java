package com.example.PokeApi.repository.impl;

import com.example.PokeApi.model.EvolutionChain;
import com.example.PokeApi.repository.EvolutionChainRepository;
import com.example.PokeApi.repository.impl.mapper.EvolutionChainDeserializer;
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
public class BasicEvolutionChainRepository implements EvolutionChainRepository {

    Logger logger = LoggerFactory.getLogger(BasicEvolutionChainRepository.class);

    private final HttpClient httpClient;
    private final ObjectMapper evolutionChainMapper;

    public BasicEvolutionChainRepository(HttpClient httpClient, ObjectMapper evolutionChainMapper) {
        this.httpClient = httpClient;
        this.evolutionChainMapper = evolutionChainMapper;
        SimpleModule module = new SimpleModule();
        module.addDeserializer(EvolutionChain.class, new EvolutionChainDeserializer());
        this.evolutionChainMapper.registerModule(module);
    }

    @Override
    @Cacheable("evolution")
    public EvolutionChain findEvolutionChainByUrl(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse response;
        EvolutionChain evolutionChain = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            evolutionChain = evolutionChainMapper.readValue(response.body().toString(), EvolutionChain.class);
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage());
        }
        return evolutionChain;
    }
}
