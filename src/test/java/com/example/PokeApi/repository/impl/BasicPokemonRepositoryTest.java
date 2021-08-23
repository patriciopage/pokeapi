package com.example.PokeApi.repository.impl;

import com.example.PokeApi.PokeApiConfig;
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

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BasicPokemonRepositoryTest {

    PokemonRepository pokemonRepository;
    PokeApiConfig pokeApiConfigMock = mock(PokeApiConfig.class);
    HttpClient httpClientMock = mock(HttpClient.class);
    ObjectMapper mapperSpy = spy(ObjectMapper.class);
    HttpResponse responseMock = mock(HttpResponse.class);

    @BeforeAll
    public void setUp() {
        when(pokeApiConfigMock.getBaseUrl()).thenReturn("https://pokeapi.co/api/v2/");
        when(pokeApiConfigMock.getPokemonResource()).thenReturn("pokemon/");
        this.pokemonRepository = new BasicPokemonRepository(httpClientMock, pokeApiConfigMock, mapperSpy);
    }

    @Test
    public void findPokemonByNameShouldSendRequest() throws ExecutionException, InterruptedException, IOException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://pokeapi.co/api/v2/pokemon/bulbasaurus")).build();
        when(responseMock.body()).thenReturn("body");
        when(httpClientMock.send(any(), any())).thenReturn(responseMock);
        this.pokemonRepository.findPokemonByName("bulbasaurus").get();
        verify(httpClientMock).send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    public void getAllPokemonsShouldSendRequest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://pokeapi.co/api/v2/pokemon/?limit=20&offset=0")).build();
        when(responseMock.body()).thenReturn("{\"results\":[{\"name\":\"bulbasaurus\"}]}");
        when(httpClientMock.send(any(), any())).thenReturn(responseMock);
        this.pokemonRepository.getAllPokemons(20, 0);
        verify(httpClientMock).send(request, HttpResponse.BodyHandlers.ofString());
    }
}
