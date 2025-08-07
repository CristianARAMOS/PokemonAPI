package com.pokemon.api.APIPokemon.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "busquedas")
@Data
public class Busqueda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombrePokemon;

    private boolean exitosa;

    private LocalDateTime fechaHora;

    public Busqueda() {}

    public Busqueda(String nombrePokemon, boolean exitosa) {
        this.nombrePokemon = nombrePokemon;
        this.exitosa = exitosa;
        this.fechaHora = LocalDateTime.now();
    }
}
