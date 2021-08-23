package com.example.PokeApi.repository.impl.mapper;

import com.example.PokeApi.model.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PokemonDeserializerTest {

    private String source = "src/test/resources/pokemon.json";
    private ObjectMapper mapper;

    @BeforeAll
    public void setUp() {
        this.mapper = new ObjectMapper();
    }

    @Test
    public void deserializeShouldReturnPokemon() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(this.source)));
        PokemonDeserializer deserializer = new PokemonDeserializer();
        Pokemon actual = deserializer.deserialize(new JsonFactory().setCodec(mapper).createParser(json), null);
        Pokemon expected = this.buildExpected();
        assertEquals(expected, actual);
    }

    private Pokemon buildExpected() {
        List<Sprite> sprites = Arrays.asList(
                new Sprite(SpriteType.BACK_DEFAULT, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png"),
                new Sprite(SpriteType.BACK_SHINY, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/1.png"),
                new Sprite(SpriteType.FRONT_DEFAULT, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
                new Sprite(SpriteType.FRONT_SHINY, "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/1.png")
        );
        List<String> types = Arrays.asList("grass", "poison");
        List<String> abilities = Arrays.asList("overgrow", "clorophyll");
        Pokemon expected = new Pokemon(1, "bulbasaur", 7, 69, sprites, types, abilities, "bulbasaur", null, null);
        return expected;
    }
}
