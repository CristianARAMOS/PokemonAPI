package com.pokemon.api.APIPokemon.DTO;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class PokemonDTO {
    private String nombre;
    private List<String> tipos;
    private List<String> habilidades;
    private List<String> ataques;
    private Map<String , Integer> estadistias;
    private String imagenBase64;
}
