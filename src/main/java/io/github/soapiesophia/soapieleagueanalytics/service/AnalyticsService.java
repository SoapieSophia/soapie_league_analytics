package io.github.soapiesophia.soapieleagueanalytics.service;

import io.github.soapiesophia.soapieleagueanalytics.dto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    private final RiotApiService riotApiService;
    private static final int TAMANHO_LOTE = 20;
    private static final int LIMITE_BUSCA_PARTIDAS = 100;

    public AnalyticsService(RiotApiService riotApiService) {
        this.riotApiService = riotApiService;
    }

    private HistoryEntry criarHistoryEntry(Participant participant, MatchInfo info){
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

    private Participant buscarParticipante(MatchResponse partida, String puuid){
        for (Participant participante : partida.getInfo().getParticipants()){
            if (participante.getPuuid().equals(puuid)){
                return participante;
            }
        }
        return null;
    }

    public List<HistoryEntry> buscarDadosPartidas(String nome, String tag){
        return buscarDadosPartidas(nome, tag, TAMANHO_LOTE);
    }

    public List<HistoryEntry> buscarDadosPartidas(String nome, String tag, int numeroPartidas){
        return buscarDadosPartidas(nome, tag, 0, numeroPartidas);
    }

    public List<HistoryEntry> buscarDadosPartidas(String nome, String tag, int start, int numeroPartidas){
        return buscarDadosPartidas(nome, tag, start, numeroPartidas, null);
    }

    public List<HistoryEntry> buscarDadosPartidas(String nome, String tag, int start, int numeroPartidas, String gameMode){
        int indicePartida = 0;
        int partidasAnalisadas = 0;

        String targetPuuid = riotApiService.buscarJogador(nome, tag).getPuuid();
        String[] partidas = riotApiService.buscarPartidas(targetPuuid, start, numeroPartidas);
        List<HistoryEntry> historico = new ArrayList<>();

        while (historico.size() < numeroPartidas && partidasAnalisadas < LIMITE_BUSCA_PARTIDAS){
            if (indicePartida == partidas.length){
                start += TAMANHO_LOTE;
                partidas = riotApiService.buscarPartidas(targetPuuid, start, TAMANHO_LOTE);
                indicePartida = 0;
            }
            MatchResponse matchResponse = riotApiService.respostaPartida(partidas[indicePartida]);
            partidasAnalisadas++;
            MatchInfo matchInfo = matchResponse.getInfo();
            Participant[] participantes = matchResponse.getInfo().getParticipants();
            for (Participant participante : participantes) {
                if (participante.getPuuid().equals(targetPuuid) && (gameMode == null || matchInfo.getGameMode().equals(gameMode))) {
                    HistoryEntry entry = criarHistoryEntry(participante, matchInfo);
                    historico.add(entry);
                    break;
                }
            }
            indicePartida++;
        }
        return historico;
    }

    // Método feito para buscar por partida que contenha dado campeão, seja esse campeão pilotado
    // por qualquer jogador da partida, retornando as estatísticas do participante que o pilotou.
    public List<HistoryEntry> buscarDadosPartidasPorCampeaoPresente(String nome, String tag, int start, int numeroPartidas, String campeao){
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
        return calcularEstatisticas(nome, tag, 0, numeroPartidas);
    }

    public PlayerStatistics calcularEstatisticas(String nome, String tag, int start, int numeroPartidas){
        return calcularEstatisticas(nome, tag, start, numeroPartidas, null);
    }

    public PlayerStatistics calcularEstatisticas(String nome, String tag, int start, int numeroPartidas, String gameMode){
        PlayerStatistics playerStatistics = new PlayerStatistics();
        List<HistoryEntry> historico = buscarDadosPartidas(nome, tag, start, numeroPartidas, gameMode);
        HistoryEntry melhorPartida = null;
        HistoryEntry piorPartida = null;
        Map<String, Integer> championsGames = new HashMap<>();
        Map<String, Integer> championsWins = new HashMap<>();
        int vitorias = 0;
        int derrotas = 0;
        int kills = 0;
        int deaths = 0;
        int assists = 0;
        float melhorKda = Float.NEGATIVE_INFINITY;
        float piorKda = Float.POSITIVE_INFINITY;
        int winStreakCurrent = 0;
        int winStreakBest = 0;
        for (HistoryEntry partida : historico){
            if (partida.isWin()){
                vitorias++;
                winStreakCurrent++;
                if (winStreakCurrent > winStreakBest){
                    winStreakBest = winStreakCurrent;
                }
            }
            else{
                derrotas++;
                winStreakCurrent = 0;
            }
            kills += partida.getKills();
            deaths += partida.getDeaths();
            assists += partida.getAssists();

            float kda = partida.getKdaScore();
            if (kda > melhorKda){
                melhorKda = kda;
                melhorPartida = partida;
            }
            if (kda < piorKda &&
                    (
                            partida.getKills() != 0 ||
                                    partida.getDeaths() != 0 ||
                                    partida.getAssists() != 0)){
                piorKda = kda;
                piorPartida = partida;
            }

            String champion = partida.getChampionName();

            if (championsGames.containsKey(champion)){
                championsGames.put(champion, championsGames.get(champion) + 1);
            }
            else {
                championsGames.put(champion, 1);
            }

            if (championsWins.containsKey(champion) && partida.isWin()){
                championsWins.put(champion, championsWins.get(champion) + 1);
            } else if (!championsWins.containsKey(champion) && partida.isWin()) {
                championsWins.put(champion, 1);
            } else if (!championsWins.containsKey(champion)){
                championsWins.put(champion, 0);
            }
        }

        String campeaoMaisJogado = null;
        int campeaoMaisJogadoQuantidade = 0;
        int campeaoMaisJogadoVitorias = 0;

        for (Map.Entry<String, Integer> entry : championsGames.entrySet()){

            if (entry.getValue() > campeaoMaisJogadoQuantidade){
                campeaoMaisJogadoQuantidade = entry.getValue();
                campeaoMaisJogado = entry.getKey();
            }

        }

        if (campeaoMaisJogado != null) {
            campeaoMaisJogadoVitorias = championsWins.get(campeaoMaisJogado);
        }

        //region Sets Player Statistics
        playerStatistics.setPartidasAnalisadas(historico.size());
        playerStatistics.setVitorias(vitorias);
        playerStatistics.setDerrotas(derrotas);
        playerStatistics.setTaxaVitoria( (float) vitorias/numeroPartidas);
        playerStatistics.setMediaKills( (float) kills/numeroPartidas);
        playerStatistics.setMediaDeaths( (float) deaths/numeroPartidas);
        playerStatistics.setMediaAssists( (float) assists/numeroPartidas);
        playerStatistics.setTotalKills(kills);
        playerStatistics.setTotalDeaths(deaths);
        playerStatistics.setTotalAssists(assists);
        playerStatistics.setMelhorKda(melhorKda);
        playerStatistics.setPiorKda(piorKda);
        playerStatistics.setMelhorPartida(melhorPartida);
        playerStatistics.setPiorPartida(piorPartida);
        playerStatistics.setWinstreakBest(winStreakBest);
        playerStatistics.setWinstreakCurrent(winStreakCurrent);
        playerStatistics.setCampeaoMaisJogadoNome(campeaoMaisJogado);
        playerStatistics.setCampeaoMaisJogadoQuantidade(campeaoMaisJogadoQuantidade);
        playerStatistics.setCampeaoMaisJogadoVitorias(campeaoMaisJogadoVitorias);

        //endregion

        return playerStatistics;
    }
}


