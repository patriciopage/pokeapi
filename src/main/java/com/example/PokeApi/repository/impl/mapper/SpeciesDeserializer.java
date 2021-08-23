package com.example.PokeApi.repository.impl.mapper;

import com.example.PokeApi.model.Species;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Iterator;

public class SpeciesDeserializer extends StdDeserializer<Species> {

    public SpeciesDeserializer() {
        super(Species.class);
    }

    @Override
    public Species deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode speciesNode = p.getCodec().readTree(p);
        Integer id = (Integer) speciesNode.get("id").numberValue();
        String name = speciesNode.get("name").asText();
        String description = "";
        Iterator<JsonNode> iterator = speciesNode.get("flavor_text_entries").elements();
        while(iterator.hasNext()) {
            JsonNode currentDescriptionNode = iterator.next();
            String version = currentDescriptionNode.get("version").get("name").asText();
            String language = currentDescriptionNode.get("language").get("name").asText();
            if("red".equals(version) && "en".equals(language)) {
                description = currentDescriptionNode.get("flavor_text").asText();
                break;
            }
        }
        String evolutionChainUrl = speciesNode.get("evolution_chain").get("url").asText();
        return new Species(id, name, description, evolutionChainUrl);
    }

}
