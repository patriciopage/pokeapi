package com.example.PokeApi.controller;

import com.example.PokeApi.exception.ElementNotFoundException;
import com.example.PokeApi.model.dto.PokemonDetailDTO;
import com.example.PokeApi.service.PokemonService;
import com.example.PokeApi.model.dto.PokemonDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class PokemonController extends BaseController{

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/pokemon")
    public ResponseEntity<List<PokemonDTO>> getPokemons(@RequestParam int limit, @RequestParam int offset) {
        return ResponseEntity.ok(this.pokemonService.getPokemons(limit, offset));
    }

    @GetMapping("/pokemon/{name}")
    public ResponseEntity<PokemonDetailDTO> getPokemonByName(@PathVariable String name) throws ElementNotFoundException, ExecutionException, InterruptedException {
        return ResponseEntity.ok(this.pokemonService.getPokemonDetailByName(name));
    }
}


