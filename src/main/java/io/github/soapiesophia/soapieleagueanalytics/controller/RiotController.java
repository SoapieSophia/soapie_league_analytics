package io.github.soapiesophia.soapieleagueanalytics.controller;

import io.github.soapiesophia.soapieleagueanalytics.dto.HistoryEntry;
import io.github.soapiesophia.soapieleagueanalytics.dto.PlayerStatistics;
import io.github.soapiesophia.soapieleagueanalytics.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RiotController {
    public final AnalyticsService analyticsService;

    public RiotController(AnalyticsService analyticsService) {this.analyticsService = analyticsService;}

    @GetMapping("/history")
    public List<HistoryEntry> partidas(
            @RequestParam String nome,
            @RequestParam String tag,
            @RequestParam(defaultValue = "20") int numero){
        return analyticsService.buscarDadosPartidas(nome, tag, numero);
    }

    @GetMapping("/history/champion")
    public List<HistoryEntry> partidasPorCampeao(
            @RequestParam String nome,
            @RequestParam String tag,
            @RequestParam(defaultValue = "0") int start,
            @RequestParam(defaultValue = "5") int numero,
            @RequestParam String campeao){
        return analyticsService.buscarDadosPartidasPorCampeaoPresente(nome,tag,start,numero,campeao);
    }

    @GetMapping("/history/statistics")
    public PlayerStatistics historicoGamemode(
            @RequestParam String nome,
            @RequestParam String tag,
            @RequestParam(defaultValue = "0") int start,
            @RequestParam(defaultValue = "20") int numero,
            @RequestParam(required = false) String gameMode){
        return analyticsService.calcularEstatisticas(nome, tag, start, numero, gameMode);
    }
}
