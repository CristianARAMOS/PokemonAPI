package com.pokemon.api.APIPokemon.utils;

import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

public class ConversorBase64 {
      public static String convertirImagenABase64(String imageUrl) {
        try (InputStream in = new URL(imageUrl).openStream()) {
            byte[] imageBytes = in.readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            return null;
        }
    }
}
