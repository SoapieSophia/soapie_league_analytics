package io.github.soapiesophia.soapieleagueanalytics.service;

import io.github.soapiesophia.soapieleagueanalytics.dto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyticsService {

    private final RiotApiService riotApiService;
    private static final int TAMANHO_LOTE = 20;
    private static final int LIMITE_BUSCA_PARTIDAS = 100;

    public AnalyticsService(RiotApiService riotApiService) {
        this.riotApiService = riotApiService;
    }

    public HistoryEntry[] buscarDadosPartidas(String nome, String tag){
        return buscarDadosPartidas(nome, tag, TAMANHO_LOTE);
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

    public HistoryEntry[] buscarDadosPartidas(String nome, String tag, int numeroPartidas){
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

    // Método feito para buscar por partida que contenha dado campeão, seja esse campeão pilotado
    // por qualquer jogador da partida, retornando as estatísticas do participante que o pilotou.
    public List<HistoryEntry> buscarPartidasPorCampeaoPresente(String nome, String tag, int start, int numeroPartidas, String campeao){
        int indicePartida = 0;
        int partidasAnalisadas = 0;

        String targetPuuid = riotApiService.buscarJogador(nome, tag).getPuuid();
        String[] partidas = riotApiService.buscarPartidas(targetPuuid, start, numeroPartidas);
        List<HistoryEntry> historico = new ArrayList<>();

        while (historico.size() < numeroPartidas && partidasAnalisadas < LIMITE_BUSCA_PARTIDAS){
            if (indicePartida == partidas.length) {

                start += TAMANHO_LOTE;

                partidas = riotApiService.buscarPartidas(targetPuuid, start, TAMANHO_LOTE);

                indicePartida = 0;
            }
            MatchResponse matchResponse = riotApiService.respostaPartida(partidas[indicePartida]);
            partidasAnalisadas++;
            MatchInfo matchInfo = matchResponse.getInfo();
            Participant[] participantes = matchResponse.getInfo().getParticipants();
            for (Participant participante : participantes) {
                if (participante.getChampionName().equals(campeao)) {
                    HistoryEntry entry = criarHistoryEntry(participante, matchInfo);
                    historico.add(entry);
                    break;
                }
            }
            indicePartida++;
        }
        return historico;
    }

    public PlayerStatistics calcularEstatisticas(String nome, String tag){
        return calcularEstatisticas(nome, tag, TAMANHO_LOTE);
    }

    public PlayerStatistics calcularEstatisticas(String nome, String tag, int numeroPartidas){
        PlayerStatistics playerStatistics = new PlayerStatistics();
        HistoryEntry[] historico = buscarDadosPartidas(nome, tag, numeroPartidas);
        int vitorias = 0;
        int derrotas = 0;
        float kills = 0;
        float deaths = 0;
        float assists = 0;
        for (HistoryEntry partida : historico){
            if (partida.isWin()){
                vitorias++;
            }
            else{
                derrotas++;
            }
            kills += partida.getKills();
            deaths += partida.getDeaths();
            assists += partida.getAssists();
        }
        //region Sets Player Statistics
        playerStatistics.setPartidasAnalisadas(historico.length);
        playerStatistics.setVitorias(vitorias);
        playerStatistics.setDerrotas(derrotas);
        playerStatistics.setTaxaVitoria( (float) vitorias/numeroPartidas);
        playerStatistics.setMediaKills(kills/numeroPartidas);
        playerStatistics.setMediaDeaths(deaths/numeroPartidas);
        playerStatistics.setMediaAssists(assists/numeroPartidas);
        //endregion

        return playerStatistics;
    }
}
