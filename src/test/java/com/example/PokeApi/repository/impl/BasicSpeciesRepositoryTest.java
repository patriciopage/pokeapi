package com.example.PokeApi.repository.impl;

import com.example.PokeApi.PokeApiConfig;
import com.example.PokeApi.model.EvolutionChain;
import com.example.PokeApi.repository.EvolutionChainRepository;
import com.example.PokeApi.repository.SpeciesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BasicSpeciesRepositoryTest {

    PokeApiConfig pokeApiConfigMock = mock(PokeApiConfig.class);
    SpeciesRepository speciesRepository;
    HttpClient httpClientMock = mock(HttpClient.class);
    ObjectMapper mapperMock = mock(ObjectMapper.class);
    HttpResponse responseMock = mock(HttpResponse.class);

    @BeforeAll
    public void setUp() {
        when(pokeApiConfigMock.getBaseUrl()).thenReturn("https://pokeapi.co/api/v2/");
        when(pokeApiConfigMock.getSpeciesResource()).thenReturn("pokemon-species/");
        this.speciesRepository = new BasicSpeciesRepository(httpClientMock, pokeApiConfigMock, mapperMock);
    }

    @Test
    public void findEvolutionChainByUrlShouldSendRequest() throws ExecutionException, InterruptedException, IOException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://pokeapi.co/api/v2/pokemon-species/bulbasaurus")).build();
        when(responseMock.body()).thenReturn("body");
        when(httpClientMock.send(any(), any())).thenReturn(responseMock);
        when(mapperMock.readValue(responseMock.body().toString(), EvolutionChain.class)).thenReturn(null);
        this.speciesRepository.findSpeciesByName("bulbasaurus");
        verify(httpClientMock).send(request, HttpResponse.BodyHandlers.ofString());
    }
}
