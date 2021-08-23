package com.example.PokeApi.repository;

import com.example.PokeApi.model.Species;

public interface SpeciesRepository {

    Species findSpeciesByName(String name);

}
