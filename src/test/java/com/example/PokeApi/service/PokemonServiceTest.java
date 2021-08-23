package com.example.PokeApi.service;

import com.example.PokeApi.exception.ElementNotFoundException;
import com.example.PokeApi.model.ChainLink;
import com.example.PokeApi.model.EvolutionChain;
import com.example.PokeApi.model.Pokemon;
import com.example.PokeApi.model.Species;
import com.example.PokeApi.model.dto.PokemonDetailDTO;
import com.example.PokeApi.model.mapper.PokemonDtoMapper;
import com.example.PokeApi.repository.EvolutionChainRepository;
import com.example.PokeApi.repository.PokemonRepository;
import com.example.PokeApi.model.dto.PokemonDTO;
import com.example.PokeApi.repository.SpeciesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PokemonServiceTest {

    PokemonService service;

    PokemonRepository pokemonRepository = mock(PokemonRepository.class);
    SpeciesRepository speciesRepository = mock(SpeciesRepository.class);
    EvolutionChainRepository evolutionChainRepository = mock(EvolutionChainRepository.class);
    ObjectMapper mapperMock = mock(ObjectMapper.class);
    PokemonDtoMapper pokemonDtoMapperMock = mock(PokemonDtoMapper.class);

    @BeforeAll
    public void setUp() {
        Pokemon pokemon = new Pokemon(1, "bulbasaurus", 1, 1, null, null, null, "bulbasaurus", "", null);
        List<String> pokemons = Arrays.asList("bulbasaurus");
        service = new PokemonService(pokemonRepository, speciesRepository, evolutionChainRepository, mapperMock, pokemonDtoMapperMock);
        when(pokemonRepository.getAllPokemons(1,0)).thenReturn(pokemons);
        when(pokemonRepository.findPokemonByName("bulbasaurus")).thenReturn(CompletableFuture.completedFuture(pokemon));
        when(speciesRepository.findSpeciesByName("bulbasaurus")).thenReturn(new Species(1, "bulbasaurus", "description", "evolutionChainUrl"));
        when(evolutionChainRepository.findEvolutionChainByUrl("evolutionChainUrl")).thenReturn(new EvolutionChain(1, new ChainLink("bulbasaurus", new LinkedList<>())));
        when(pokemonDtoMapperMock.toDto(any())).thenReturn(new PokemonDTO("bulbasaurus"));
        when(pokemonDtoMapperMock.toDetailDto(any())).thenReturn(new PokemonDetailDTO("bulbasaurus"));
    }

    @Test
    public void getPokemonsShouldReturnListFromRepository() {
        List<PokemonDTO> pokemons = service.getPokemons(1,0);
        verify(pokemonRepository).getAllPokemons(1,0);
    }

    @Test
    public void getPokemonDetailByNameShouldReturnSingleFromRepository() throws ElementNotFoundException, ExecutionException, InterruptedException {
        PokemonDetailDTO pokemon = service.getPokemonDetailByName("bulbasaurus");
        verify(pokemonRepository).findPokemonByName("bulbasaurus");

    }
}
