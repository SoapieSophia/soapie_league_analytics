package io.github.soapiesophia.soapieleagueanalytics.service;

import io.github.soapiesophia.soapieleagueanalytics.dto.MatchResponse;
import io.github.soapiesophia.soapieleagueanalytics.dto.Participant;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class AnalyticsService {

    private final RiotApiService riotApiService;

    public AnalyticsService(RiotApiService riotApiService) {
        this.riotApiService = riotApiService;
    }

    public Participant buscarEstatisticasUltimaPartida(String nome, String tag){
        Participant target = null;
        String puuid = riotApiService.buscarJogador(nome, tag).getPuuid();
        String[] partidas = riotApiService.buscarPartidas(puuid, 1);
        MatchResponse partida = riotApiService.infoPartida(partidas[0]);
        Participant[] participantes = partida.getInfo().getParticipants();
        for (int i = 0; i < participantes.length; i++ ){
            if (participantes[i].getPuuid().equals(puuid)) {
                target = participantes[i];
                break;
            }
        }
        return target;
    }
}
