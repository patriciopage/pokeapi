package com.example.PokeApi.service;

import com.example.PokeApi.exception.ElementNotFoundException;
import com.example.PokeApi.model.EvolutionChain;
import com.example.PokeApi.model.Pokemon;
import com.example.PokeApi.model.Species;
import com.example.PokeApi.model.dto.PokemonDetailDTO;
import com.example.PokeApi.model.mapper.PokemonDtoMapper;
import com.example.PokeApi.repository.EvolutionChainRepository;
import com.example.PokeApi.repository.PokemonRepository;
import com.example.PokeApi.model.dto.PokemonDTO;
import com.example.PokeApi.repository.SpeciesRepository;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;
    private final SpeciesRepository speciesRepository;
    private final EvolutionChainRepository evolutionChainRepository;
    private final ObjectMapper mapper;
    private final PokemonDtoMapper pokemonMapper;

    public PokemonService(PokemonRepository pokemonRepository, SpeciesRepository speciesRepository, EvolutionChainRepository evolutionChainRepository, ObjectMapper mapper, PokemonDtoMapper pokemonDtoMapper) {
        this.pokemonRepository = pokemonRepository;
        this.speciesRepository = speciesRepository;
        this.evolutionChainRepository = evolutionChainRepository;
        this.pokemonMapper = pokemonDtoMapper;
        this.mapper = mapper;
    }

    public List<PokemonDTO> getPokemons(int limit, int offset) {
        List<String> pokemonNames = this.pokemonRepository.getAllPokemons(limit, offset);
        List<CompletableFuture<Pokemon>> pokemonFutures = new ArrayList<>();
        for(String pokemonName : pokemonNames) {
            pokemonFutures.add(this.pokemonRepository.findPokemonByName(pokemonName));
        }
        List<PokemonDTO> pokemons = pokemonFutures.stream()
                .map(CompletableFuture::join)
                .map(pokemon -> pokemonMapper.toDto(pokemon))
                .collect(Collectors.toList());
        return pokemons;
    }

    public PokemonDetailDTO getPokemonDetailByName(String name) throws ElementNotFoundException, ExecutionException, InterruptedException {
        Pokemon pokemon = this.pokemonRepository.findPokemonByName(name).get();
        if( pokemon == null ) throw new ElementNotFoundException(name, "pokemon");
        Species species = this.speciesRepository.findSpeciesByName(pokemon.getSpecies());
        EvolutionChain evolutionChain = this.evolutionChainRepository.findEvolutionChainByUrl(species.getEvolutionChainUrl());
        pokemon = pokemon.withDescription(species.getDescription()).withEvolutionChain(evolutionChain);
        return pokemonMapper.toDetailDto(pokemon);
    }
}
