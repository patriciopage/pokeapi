package com.example.PokeApi.repository.impl.mapper;

import com.example.PokeApi.model.Species;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SpeciesDeserializerTest {

    private String source = "src/test/resources/species.json";
    private ObjectMapper mapper;

    @BeforeAll
    public void setUp() {
        this.mapper = new ObjectMapper();
    }

    @Test
    public void deserializeShouldReturnSpecies() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(this.source)));
        SpeciesDeserializer deserializer = new SpeciesDeserializer();
        Species actual = deserializer.deserialize(new JsonFactory().setCodec(mapper).createParser(json), null);
        Species expected = this.buildExpected();
        assertEquals(expected, actual);
    }

    private Species buildExpected() {
        String description = "A strange seed was\n" +
                "planted on its\n" +
                "back at birth.\fThe plant sprouts\n" +
                "and grows with\n" +
                "this POKéMON.";
        String evolutionChainUrl = "https://pokeapi.co/api/v2/evolution-chain/1/";
        Species expected = new Species(1, "bulbasaur", description, evolutionChainUrl);
        return expected;
    }

}
