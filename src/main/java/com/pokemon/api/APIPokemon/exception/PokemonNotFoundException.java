package com.pokemon.api.APIPokemon.exception;

public class PokemonNotFoundException extends RuntimeException{
     public PokemonNotFoundException(String name) {
        super("Pokémon no encontrado: " + name);
    }

}
