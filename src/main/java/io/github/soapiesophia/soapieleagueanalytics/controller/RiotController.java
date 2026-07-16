package io.github.soapiesophia.soapieleagueanalytics.controller;

import io.github.soapiesophia.soapieleagueanalytics.dto.HistoryEntry;
import io.github.soapiesophia.soapieleagueanalytics.dto.Participant;
import io.github.soapiesophia.soapieleagueanalytics.dto.PlayerStatistics;
import io.github.soapiesophia.soapieleagueanalytics.service.AnalyticsService;
import org.apache.coyote.Request;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RiotController {
    public final AnalyticsService analyticsService;

    public RiotController(AnalyticsService analyticsService) {this.analyticsService = analyticsService;}

    @GetMapping("/history")
    public HistoryEntry[] partidas(
            @RequestParam String nome,
            @RequestParam String tag,
            @RequestParam int numero){
        return analyticsService.buscarDadosPartidas(nome, tag, numero);
    }

    @GetMapping("/history/champion")
    public List<HistoryEntry> partidasPorCampeao(
            @RequestParam String nome,
            @RequestParam String tag,
            @RequestParam int start,
            @RequestParam int numero,
            @RequestParam String campeao){
        return analyticsService.buscarPartidasPorCampeaoPresente(nome,tag,start,numero,campeao);
    }

    @GetMapping("/history/statistics")
    public PlayerStatistics estatisticas(
            @RequestParam String nome,
            @RequestParam String tag,
            @RequestParam int numero){
        return analyticsService.calcularEstatisticas(nome, tag, numero);
    }
}
