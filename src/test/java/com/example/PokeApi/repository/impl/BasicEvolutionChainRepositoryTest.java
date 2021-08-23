package com.example.PokeApi.repository.impl;

import com.example.PokeApi.PokeApiConfig;
import com.example.PokeApi.model.EvolutionChain;
import com.example.PokeApi.repository.EvolutionChainRepository;
import com.example.PokeApi.repository.PokemonRepository;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BasicEvolutionChainRepositoryTest {

    EvolutionChainRepository evolutionChainRepository;
    HttpClient httpClientMock = mock(HttpClient.class);
    ObjectMapper mapperMock = mock(ObjectMapper.class);
    HttpResponse responseMock = mock(HttpResponse.class);

    @BeforeAll
    public void setUp() {
        this.evolutionChainRepository = new BasicEvolutionChainRepository(httpClientMock, mapperMock);
    }

    @Test
    public void findEvolutionChainByUrlShouldSendRequest() throws ExecutionException, InterruptedException, IOException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://pokeapi.co/api/v2/evolution-chain/1")).build();
        when(responseMock.body()).thenReturn("body");
        when(httpClientMock.send(any(), any())).thenReturn(responseMock);
        when(mapperMock.readValue(responseMock.body().toString(), EvolutionChain.class)).thenReturn(null);
        this.evolutionChainRepository.findEvolutionChainByUrl("https://pokeapi.co/api/v2/evolution-chain/1");
        verify(httpClientMock).send(request, HttpResponse.BodyHandlers.ofString());
    }
}
