package com.example.PokeApi.repository.impl.mapper;

import com.example.PokeApi.model.ChainLink;
import com.example.PokeApi.model.EvolutionChain;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EvolutionChainDeserializer extends StdDeserializer<EvolutionChain> {

    public EvolutionChainDeserializer() {
        super(EvolutionChain.class);
    }

    @Override
    public EvolutionChain deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode evolutionChainNode = p.getCodec().readTree(p);
        Integer id = (Integer) evolutionChainNode.get("id").numberValue();
        ChainLink baseLink = this.buildChainLink(evolutionChainNode.get("chain"));
        return new EvolutionChain(id, baseLink);
    }

    private ChainLink buildChainLink(JsonNode chainLinkNode) {
        List<ChainLink> evolvesTo = new LinkedList<>();
        String species = chainLinkNode.get("species").get("name").asText();
        Iterator<JsonNode> chainIterator = chainLinkNode.get("evolves_to").elements();
        while(chainIterator.hasNext()) {
            JsonNode nextChainLinkNode = chainIterator.next();
            ChainLink nextChainLink = this.buildChainLink(nextChainLinkNode);
            evolvesTo.add(nextChainLink);
        }
        return new ChainLink(species, evolvesTo);
    }

}
