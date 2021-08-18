package com.webflux.pokedex.controller;

import com.webflux.pokedex.model.Pokemon;
import com.webflux.pokedex.model.PokemonEvent;
import com.webflux.pokedex.repository.PokemonRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/pokemons")
public class PokemonController {

    private final PokemonRepository repository;

    @GetMapping
    public Flux<Pokemon> getAllPokemons() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Pokemon>> getPokemonById(@PathVariable String id) {
        return repository
                .findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Pokemon> savePokemon(@RequestBody Pokemon pokemon) {
        return repository.save(pokemon);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Pokemon>> updatePokemon(@PathVariable(value = "id") String id,
                                                       @RequestBody Pokemon pokemon) {
        return repository.
                findById(id).
                flatMap(existingPokemon -> {
                    Optional.ofNullable(pokemon.getName()).ifPresent(existingPokemon::setName);
                    Optional.ofNullable(pokemon.getCategory()).ifPresent(existingPokemon::setCategory);
                    Optional.ofNullable(pokemon.getAbilities()).ifPresent(existingPokemon::setAbilities);
                    Optional.ofNullable(pokemon.getWeight()).ifPresent(existingPokemon::setWeight);

                    return repository.save(existingPokemon);
                })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Object>> deletePokemon(@PathVariable(value = "id") String id) {
        return repository
                .findById(id)
                .flatMap(existingPokemon -> repository
                        .delete(existingPokemon)
                        .then(Mono.just(ResponseEntity.ok().build()))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<Void> deleteAllPokemons() {
        return repository.deleteAll();
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PokemonEvent> pokemonEvents() {
        return Flux
                .interval(Duration.ofSeconds(5))
                .map(value -> new PokemonEvent(value, "Pokemons"));
    }
}
