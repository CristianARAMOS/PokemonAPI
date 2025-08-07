package com.pokemon.api.APIPokemon.exception;

public class ExternalApiException extends RuntimeException{
    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
