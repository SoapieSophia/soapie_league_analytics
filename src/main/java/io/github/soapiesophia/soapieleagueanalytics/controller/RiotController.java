package io.github.soapiesophia.soapieleagueanalytics.controller;

import io.github.soapiesophia.soapieleagueanalytics.dto.AccountResponse;
import io.github.soapiesophia.soapieleagueanalytics.service.RiotApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiotController {
    private final RiotApiService service;

    public RiotController(RiotApiService service){
        this.service = service;
    }

    @GetMapping("/teste")
    public AccountResponse testar() {
        return service.buscarJogador("Sophia Coelhinha","owner");
    }
}
