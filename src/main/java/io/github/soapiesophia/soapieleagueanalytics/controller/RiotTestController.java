package io.github.soapiesophia.soapieleagueanalytics.controller;

import io.github.soapiesophia.soapieleagueanalytics.dto.AccountResponse;
import io.github.soapiesophia.soapieleagueanalytics.service.RiotApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiotTestController {
    private final RiotApiService service;

    public RiotTestController(RiotApiService service) {
        this.service = service;
    }

    @GetMapping("/test/BuscarJogador")
    public AccountResponse testarBuscarJogador() {
        return service.buscarJogador("Sophia Coelhinha", "owner");
    }

    @GetMapping("/test/BuscarPartidas")
    public String[] testarBuscarPartidas() {
        return service.buscarPartidas("T7rv7lHQ7XdOmX3paHv7hdw2uHVK01iAEQAaAXS2w_Z8LpFy7AiCz3zc-9tYJLJv3n-6yk3TewjQIQ", 20);
    }

    @GetMapping("/test/Concatenado1")
    public String[] testarConcatenado1() {
        String puuid = service.buscarJogador("Sophia Coelhinha", "owner").getPuuid();
        return service.buscarPartidas(puuid, 20);
    }

    @GetMapping("/test/partidas")
    public String[] buscarPartidas(@RequestParam String nome, @RequestParam String tag) {
        String puuid = service.buscarJogador(nome, tag).getPuuid();

        return service.buscarPartidas(puuid, 20);
    }
}
