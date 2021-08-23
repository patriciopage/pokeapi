package com.example.PokeApi.repository.impl.mapper;

import com.example.PokeApi.model.Pokemon;
import com.example.PokeApi.model.Sprite;
import com.example.PokeApi.model.SpriteType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PokemonDeserializer extends StdDeserializer<Pokemon> {

    Logger logger = LoggerFactory.getLogger(PokemonDeserializer.class);

    public PokemonDeserializer() {
        super(Pokemon.class);
    }

    @Override
    public Pokemon deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode pokemonNode = p.getCodec().readTree(p);
        Integer id = (Integer) pokemonNode.get("id").numberValue();
        String name = pokemonNode.get("name").asText();
        Integer height = (Integer) pokemonNode.get("height").numberValue();
        Integer weight = (Integer) pokemonNode.get("weight").numberValue();
        List<Sprite> sprites = new ArrayList<>();
        pokemonNode.get("sprites").fields()
                .forEachRemaining(spriteNodeEntry -> buildSprite(spriteNodeEntry)
                        .ifPresent(sprites::add));
        List<String> types = pokemonNode.get("types").findValues("name").stream()
                .map(JsonNode::asText)
                .collect(Collectors.toList());
        List<String> abilities = pokemonNode.get("abilities").findValues("name").stream()
                .map(JsonNode::asText)
                .collect(Collectors.toList());
        String species = pokemonNode.get("species").get("name").asText();
        return new Pokemon(id, name, height, weight, sprites, types, abilities, species, null, null);
    }

    private Optional<Sprite> buildSprite(Map.Entry<String,JsonNode> spriteNode) {
        Optional<Sprite> sprite = Optional.empty();
        try {
            SpriteType spriteType = SpriteType.valueOf(spriteNode.getKey().toUpperCase());
            if(!"null".equals(spriteNode.getValue().asText()))
                sprite = Optional.of(new Sprite(spriteType, spriteNode.getValue().asText()));
        } catch(IllegalArgumentException iae) {
            logger.info("Sprite Type {} not supported", spriteNode.getKey());
        }
        return sprite;
    }
}
