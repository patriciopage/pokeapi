package com.example.PokeApi.model.mapper;

import com.example.PokeApi.model.*;
import com.example.PokeApi.model.dto.ChainLinkDTO;
import com.example.PokeApi.model.dto.PokemonDTO;
import com.example.PokeApi.model.dto.PokemonDetailDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PokemonDtoMapperTest {

    private Pokemon pokemon;
    private PokemonDTO expectedPokemonDTO;
    private PokemonDetailDTO expectedPokemonDetailDTO;
    private PokemonDtoMapper pokemonDtoMapper;

    @BeforeAll
    public void setUp() {
        this.pokemonDtoMapper = new PokemonDtoMapper();
        this.pokemon = this.buildPokemon(SpriteType.FRONT_DEFAULT);
        ChainLinkDTO evolutionChainDTO = new ChainLinkDTO("bulbasaur", Collections.emptyList());
        this.expectedPokemonDTO = this.buildPokemonDTO(this.pokemon, null);
        this.expectedPokemonDetailDTO = new PokemonDetailDTO(expectedPokemonDTO, pokemon.getDescription(), evolutionChainDTO);
    }

    @Test
    public void toDtoShouldReturnPokemonDTO() {
        PokemonDTO pokemonDTO = this.pokemonDtoMapper.toDto(this.pokemon);
        assertEquals(expectedPokemonDTO, pokemonDTO);
    }

    @Test
    public void toDtoShouldReturnPokemonDTOWhenSpriteTypeNotSupported() throws IllegalAccessException {
        Pokemon pokemonWithUnsupportedSprite = this.buildPokemon(SpriteType.BACK_DEFAULT);
        PokemonDTO pokemonDTOWithNoSprite = this.buildPokemonDTO(pokemonWithUnsupportedSprite, "");
        PokemonDTO pokemonDTO = this.pokemonDtoMapper.toDto(pokemonWithUnsupportedSprite);
        assertEquals(pokemonDTOWithNoSprite, pokemonDTO);
    }

    @Test
    public void toDetailDtoShouldReturnPokemonDetailDTO() {
        PokemonDetailDTO pokemonDetailDTO = this.pokemonDtoMapper.toDetailDto(this.pokemon);
        assertEquals(expectedPokemonDetailDTO, pokemonDetailDTO);
    }

    private Pokemon buildPokemon(SpriteType spriteType) {
        Integer id = 1;
        String name = "bulbasaur";
        Integer height = 10;
        Integer weight = 10;
        List<Sprite> sprites = Arrays.asList(new Sprite(spriteType, "url"));
        List<String> types = Collections.singletonList("type");
        List<String> abilities = Arrays.asList("ability");
        String species = "bulbasaur";
        String description = "description";
        EvolutionChain evolutionChain = new EvolutionChain(1, new ChainLink("bulbasaur", Collections.emptyList()));
        return new Pokemon(id, name, height, weight, sprites, types, abilities, species, description, evolutionChain);
    }

    private PokemonDTO buildPokemonDTO(Pokemon pokemon, String spriteUrl) {
        String url = pokemon.getSprites().get(0).getSpriteUrl();
        if(spriteUrl != null) url = spriteUrl;
        return new PokemonDTO(pokemon.getName(), url, pokemon.getHeight(), pokemon.getWeight(), pokemon.getTypes(), pokemon.getAbilities());
    }

}
