package io.github.soapiesophia.soapieleagueanalytics.exception;

public class RiotRateLimitException extends RuntimeException {

    public RiotRateLimitException() {
        super("Limite de requisições da Riot API atingido.");
    }

}
