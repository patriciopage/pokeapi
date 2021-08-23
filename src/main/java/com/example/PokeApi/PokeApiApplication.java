package com.example.PokeApi;

import com.example.PokeApi.model.mapper.PokemonDtoMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.http.HttpClient;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class PokeApiApplication {

	@Bean
	HttpClient getHttpClient() {
		return HttpClient.newHttpClient();
	}

	@Bean
	PokeApiConfig getPokeApiConfig() {
		return new PokeApiConfig();
	}

	@Bean
	PokemonDtoMapper getPokemonDtoMapper() {
		return new PokemonDtoMapper();
	}

		public static void main(String[] args) {
		SpringApplication.run(PokeApiApplication.class, args);
	}

}
