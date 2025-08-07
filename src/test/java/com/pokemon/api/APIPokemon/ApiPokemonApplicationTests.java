package com.pokemon.api.APIPokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pokemon.api.APIPokemon.DTO.PokemonDTO;
import com.pokemon.api.APIPokemon.exception.PokemonNotFoundException;
import com.pokemon.api.APIPokemon.service.PokemonService;

@SpringBootTest
class ApiPokemonApplicationTests {
	
    @MockBean
    private HttpClient httpClient;

    @Autowired
    private PokemonService pokemonService;

    @SuppressWarnings("unchecked")
	@Test
    void testGetPokemonData_WithValidResponse() throws Exception {
        String mockJson = """
        {
          "name": "pikachu",
          "species": { "name": "pikachu" },
          "types": [ { "type": { "name": "electric" } } ],
          "abilities": [ { "ability": { "name": "static" } } ],
          "moves": [ { "move": { "name": "thunder-punch" } } ],
          "stats": [ { "stat": { "name": "speed" }, "base_stat": 90 } ],
          "sprites": {
            "front_default": "https://fake.url/image.png"
          }
        }
        """;

        @SuppressWarnings("unchecked")
		HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(mockJson);

        PokemonDTO info = pokemonService.getPokemon("pikachu");

        assertEquals("pikachu", info.getNombre());
        assertEquals("electric", info.getTipos().get(0));
        assertEquals("static", info.getHabilidades().get(0));
        assertEquals("thunder-punch", info.getAtaques().get(0));
        assertEquals(90, info.getEstadistias().get("speed"));
    }

	 @SuppressWarnings("unchecked")
	@Test
    void testGetPokemonData_NotFound() throws Exception {
        @SuppressWarnings("unchecked")
		HttpResponse<String> mockResponse = mock(HttpResponse.class);

        when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(404);

        assertThrows(PokemonNotFoundException.class, () -> {
            pokemonService.getPokemon("noexiste");
        });
    }

}
