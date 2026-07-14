package io.github.soapiesophia.soapieleagueanalytics.service;

import io.github.soapiesophia.soapieleagueanalytics.dto.HistoryEntry;
import io.github.soapiesophia.soapieleagueanalytics.dto.MatchInfo;
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

    public HistoryEntry[] buscarEstatisticasPartidas(String nome, String tag){
        return buscarEstatisticasPartidas(nome, tag, 3);
    }

    public HistoryEntry criarHistoryEntry(Participant participant, MatchInfo info){
        HistoryEntry entry = new HistoryEntry();
        entry.setChampionName(participant.getChampionName());
        entry.setKills(participant.getKills());
        entry.setDeaths(participant.getDeaths());
        entry.setAssists(participant.getAssists());
        entry.setWin(participant.isWin());
        entry.setGameDuration(info.getGameDuration());
        entry.setGameMode(info.getGameMode());

        return entry;
    }

    public HistoryEntry[] buscarEstatisticasPartidas(String nome, String tag, int numeroPartidas){
        Participant target = null;
        String targetPuuid = riotApiService.buscarJogador(nome, tag).getPuuid();
        String[] partidas = riotApiService.buscarPartidas(targetPuuid, numeroPartidas);
        HistoryEntry[] historico = new HistoryEntry[partidas.length];
        // Método auxiliar de iterar por partidas?
        for (int i = 0; i < partidas.length; i++){
            target = null;
            MatchResponse matchResponse = riotApiService.respostaPartida(partidas[i]);
            MatchInfo matchInfo = matchResponse.getInfo();
            Participant[] participantes = matchResponse.getInfo().getParticipants();
            // Método auxiliar de iterar por players numa partida?
            for (int ii = 0; ii < participantes.length; ii++ ){
                if (participantes[ii].getPuuid().equals(targetPuuid)) {
                    target = participantes[ii];
                    break;
                }
            }
            historico[i] = criarHistoryEntry(target, matchInfo);
        }
        return historico;
    }
}
