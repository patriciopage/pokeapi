package com.example.PokeApi.repository;

import com.example.PokeApi.model.EvolutionChain;

public interface EvolutionChainRepository {

    EvolutionChain findEvolutionChainByUrl(String url);
}
