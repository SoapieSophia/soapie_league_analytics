package io.github.soapiesophia.soapieleagueanalytics.controller;

import io.github.soapiesophia.soapieleagueanalytics.dto.Participant;
import io.github.soapiesophia.soapieleagueanalytics.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiotController {
    public final AnalyticsService analyticsService;

    public RiotController(AnalyticsService analyticsService) {this.analyticsService = analyticsService;}

    @GetMapping("/info")
    public Participant infoPartida(
            @RequestParam String nome,
            @RequestParam String tag){
        return analyticsService.buscarEstatisticasUltimaPartida(nome, tag);
    }
}
