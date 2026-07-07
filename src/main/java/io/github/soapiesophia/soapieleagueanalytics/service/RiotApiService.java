package io.github.soapiesophia.soapieleagueanalytics.service;

import io.github.soapiesophia.soapieleagueanalytics.dto.AccountResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RiotApiService {
//Buscar jogador com base no nome e tagline
    private final RestClient restClient;

    @Value("${riot.api.url.base}")
    private String urlBase;
    @Value("${riot.api.key}")
    private String apiKey;

    public RiotApiService(){
        this.restClient = RestClient.create();
    }

    public AccountResponse buscarJogador(String nome, String tag){

        URI uri = UriComponentsBuilder
                .fromUriString(urlBase)
                .path("/riot/account/v1/accounts/by-riot-id/{nome}/{tag}")
                .buildAndExpand(nome, tag)
                .encode()
                .toUri();

        AccountResponse resposta = restClient.get()
                .uri(uri)
                .header("X-Riot-Token", apiKey)
                .retrieve()
                .body(AccountResponse.class);

        return resposta;

    }
}
