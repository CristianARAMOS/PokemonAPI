package com.pokemon.api.APIPokemon.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokemon.api.APIPokemon.DTO.PokemonDTO;
import com.pokemon.api.APIPokemon.exception.ExternalApiException;
import com.pokemon.api.APIPokemon.exception.PokemonNotFoundException;
import com.pokemon.api.APIPokemon.models.Busqueda;
import com.pokemon.api.APIPokemon.repository.BusquedaRepository;
import com.pokemon.api.APIPokemon.utils.ConversorBase64;

@Service
public class PokemonService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BusquedaRepository busquedaRepository;
    public PokemonService(HttpClient httpClient, BusquedaRepository busquedaRepository) {
        this.httpClient = httpClient;
        this.busquedaRepository = busquedaRepository;
    }
    
    public PokemonDTO getPokemon(String name){
        boolean success = false;
        try {
            String url = "https://pokeapi.co/api/v2/pokemon/" + name.toLowerCase();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
            HttpResponse<String> response = httpClient
            .send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 404) {
                throw new PokemonNotFoundException(name);
            } else if (response.statusCode() != 200) {
                throw new ExternalApiException("Error al obtener datos del Pokémon", null);
            }
            JsonNode root = objectMapper.readTree(response.body());

            PokemonDTO pokemon = new PokemonDTO();
            pokemon.setNombre(root.get("name").asText());
            List<String> tipos = new ArrayList<>();
            root.get("types").forEach(t -> tipos.add(t.get("type")
            .get("name").asText()));
            pokemon.setTipos(tipos);

            List<String> habilidades = new ArrayList<>();
            root.get("abilities").forEach(a -> habilidades.add(a.get("ability").get("name").asText()));
            pokemon.setHabilidades(habilidades);

            List<String> ataques = new ArrayList<>();
            root.get("moves").forEach(m->ataques.add(m.get("move").get("name").asText()));
            pokemon.setAtaques(ataques);

            Map<String, Integer> estadisticas = new LinkedHashMap<>();
            root.get("stats").forEach(e-> {
                estadisticas.put(e.get("stat").get("name").asText(),
                e.get("base_stat").asInt());
            });
            pokemon.setEstadistias(estadisticas);

            String imagenUrl = root.get("sprites").get("front_default").asText();
            pokemon.setImagenBase64(ConversorBase64.convertirImagenABase64(imagenUrl)); 
            success = true;
            return pokemon;   
        } catch (IOException | InterruptedException e) {
           throw new ExternalApiException("Error al comunicarse con la PokeAPI", e);
        } finally {
            busquedaRepository.save(new Busqueda(name.toLowerCase(), success));
        }
        
    }

   

}
