package io.github.soapiesophia.soapieleagueanalytics.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "winRate",
        "kda",
        "kdaScore",
        "kdaScoreFormatted",
        "mediaKills",
        "mediaDeaths",
        "mediaAssists"
})

public class PlayerStatistics {
    private int partidasAnalisadas;
    private int vitorias;
    private int derrotas;
    private float taxaVitoria;
    private float mediaKills;
    private float mediaDeaths;
    private float mediaAssists;

    public int getPartidasAnalisadas() {
        return partidasAnalisadas;
    }

    @JsonIgnore
    public int getVitorias() {
        return vitorias;
    }

    @JsonIgnore
    public int getDerrotas() {
        return derrotas;
    }

    @JsonIgnore
    public float getTaxaVitoria() {
        return taxaVitoria;
    }

    public String getWinRate() {
        return String.valueOf(taxaVitoria * 100) + "%";
    }

    @JsonIgnore
    public float getMediaKills() {
        return mediaKills;
    }

    @JsonIgnore
    public float getMediaDeaths() {
        return mediaDeaths;
    }

    @JsonIgnore
    public float getMediaAssists() {
        return mediaAssists;
    }

    public String getKda() {
        return mediaKills + "/" + mediaDeaths + "/" + mediaAssists;
    }

    @JsonIgnore
    public float getKdaScore() {
        return (mediaKills + mediaAssists) / mediaDeaths;
    }

    public float getKdaScoreFormatted() {
        float kda = (mediaKills + mediaAssists) / mediaDeaths;
        return Math.round(kda * 100) / 100f;
    }

    public void setPartidasAnalisadas(int partidasAnalisadas) {
        this.partidasAnalisadas = partidasAnalisadas;
    }

    public void setVitorias(int vitorias) {
        this.vitorias = vitorias;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }

    public void setTaxaVitoria(float taxaVitoria) {
        this.taxaVitoria = taxaVitoria;
    }

    public void setMediaKills(float mediaKills) {
        this.mediaKills = mediaKills;
    }

    public void setMediaDeaths(float mediaDeaths) {
        this.mediaDeaths = mediaDeaths;
    }

    public void setMediaAssists(float mediaAssists) {
        this.mediaAssists = mediaAssists;
    }
}
