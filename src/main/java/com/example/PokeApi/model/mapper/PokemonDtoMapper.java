package com.example.PokeApi.model.mapper;

import com.example.PokeApi.model.ChainLink;
import com.example.PokeApi.model.Pokemon;
import com.example.PokeApi.model.Sprite;
import com.example.PokeApi.model.SpriteType;
import com.example.PokeApi.model.dto.ChainLinkDTO;
import com.example.PokeApi.model.dto.PokemonDTO;
import com.example.PokeApi.model.dto.PokemonDetailDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PokemonDtoMapper {

    public PokemonDTO toDto(Pokemon pokemon) {
        String name = pokemon.getName();
        String spriteUrl = pokemon.getSprites().stream()
                .filter(sprite -> SpriteType.FRONT_DEFAULT.equals(sprite.getSpriteType()))
                .findAny().orElse(new Sprite(SpriteType.FRONT_DEFAULT, "")).getSpriteUrl();
        Integer height = pokemon.getHeight();
        Integer weight = pokemon.getWeight();
        List<String> types = pokemon.getTypes();
        List<String> abilities = pokemon.getAbilities();

        PokemonDTO dto = new PokemonDTO(name, spriteUrl, height, weight, types, abilities);
        return dto;
    }

    public PokemonDetailDTO toDetailDto(Pokemon pokemon) {
        PokemonDTO pokemonDTO = this.toDto(pokemon);
        String description = pokemon.getDescription();
        ChainLinkDTO evolutionChain = this.buildChain(pokemon.getChainLink().getBaseLink());
        PokemonDetailDTO dto = new PokemonDetailDTO(pokemonDTO, description, evolutionChain);
        return dto;
    }

    private ChainLinkDTO buildChain(ChainLink chainLink) {
        String species = chainLink.getSpecies();
        List<ChainLinkDTO> evolutionChain = new ArrayList<>();
        Iterator<ChainLink> chainIterator = chainLink.getEvolvesTo().iterator();
        while(chainIterator.hasNext()) {
            ChainLink next = chainIterator.next();
            ChainLinkDTO nextChain = this.buildChain(next);
            evolutionChain.add(nextChain);
        }
        return new ChainLinkDTO(species, evolutionChain);
    }
}
