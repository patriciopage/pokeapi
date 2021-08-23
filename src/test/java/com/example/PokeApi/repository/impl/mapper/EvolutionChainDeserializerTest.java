package com.example.PokeApi.repository.impl.mapper;

import com.example.PokeApi.model.ChainLink;
import com.example.PokeApi.model.EvolutionChain;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EvolutionChainDeserializerTest {

    private String source = "src/test/resources/evolutionchain.json";
    private ObjectMapper mapper;

    @BeforeAll
    public void setUp() {
        this.mapper = new ObjectMapper();
    }

    @Test
    public void deserializeShouldReturnEvolutionChain() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get(this.source)));
        EvolutionChainDeserializer deserializer = new EvolutionChainDeserializer();
        EvolutionChain actual = deserializer.deserialize(new JsonFactory().setCodec(mapper).createParser(json), null);
        assertEquals(buildExpected(), actual);
    }

    private EvolutionChain buildExpected() {
        List<ChainLink> evolvesTo = Arrays.asList(new ChainLink("venusaur", Collections.emptyList()));
        List<ChainLink> evolvesTo1 = Arrays.asList(new ChainLink("ivysaur", evolvesTo));
        ChainLink baseLink = new ChainLink("bulbasaur", evolvesTo1);
        EvolutionChain expected = new EvolutionChain(1, baseLink);
        return expected;
    }
}
