package com.webflux.pokedex.env;

import com.webflux.pokedex.model.Pokemon;
import com.webflux.pokedex.repository.PokemonRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class TestService {

    public final ReactiveMongoOperations operations;
    public final PokemonRepository repository;

    public void init() {

        Flux<Pokemon> pokemonFlux = Flux.just(
                new Pokemon(null, "Blastoise", "Shellfish", "Torrent", 85.5),
                new Pokemon(null, "Caterpie", "Worm", "Shield Dust", 2.9),
                new Pokemon(null, "Bulbasaur", "Seed", "Overgrow", 6.9)
        )
                .flatMap(repository::save);

        pokemonFlux
                .thenMany(repository.findAll())
                .subscribe(System.out::println);
    }
}
