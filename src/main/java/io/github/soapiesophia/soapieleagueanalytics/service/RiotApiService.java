package io.github.soapiesophia.soapieleagueanalytics.service;

import io.github.soapiesophia.soapieleagueanalytics.dto.AccountResponse;
import io.github.soapiesophia.soapieleagueanalytics.dto.MatchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RiotApiService {
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

        AccountResponse playerAccount = restClient.get()
                .uri(uri)
                .header("X-Riot-Token", apiKey)
                .retrieve()
                .body(AccountResponse.class);

        return playerAccount;

    }

    public String[] buscarPartidas(String puuid, int count){
        return buscarPartidas(puuid, 0, count);
    }

    public String[] buscarPartidas(String puuid, int start, int count){

        URI uri = UriComponentsBuilder
                .fromUriString(urlBase)
                .path("/lol/match/v5/matches/by-puuid/{puuid}/ids")
                .queryParam("start", start)
                .queryParam("count", count)
                .buildAndExpand(puuid)
                .encode()
                .toUri();

        String[] matchIds = restClient.get()
                .uri(uri)
                .header("X-Riot-Token", apiKey)
                .retrieve()
                .body(String[].class);

        return matchIds;
    }

    public MatchResponse infoPartida(String matchId){
        URI uri = UriComponentsBuilder
                .fromUriString(urlBase)
                .path("/lol/match/v5/matches/{matchId}")
                .buildAndExpand(matchId)
                .encode()
                .toUri();

        MatchResponse matchInfo = restClient.get()
                .uri(uri)
                .header("X-Riot-Token", apiKey)
                .retrieve()
                .body(MatchResponse.class);

        return matchInfo;
    }
}
