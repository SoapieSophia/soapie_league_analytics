package io.github.soapiesophia.soapieleagueanalytics.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "partidasAnalisadas",
        "winRate",
        "kda",
        "kdaScore",
        "kdaScoreFormatted",
        "mediaKills",
        "mediaDeaths",
        "mediaAssists",
        "totalKills",
        "totalDeaths",
        "totalAssists",
        "winstreakCurrent",
        "winstreakBest",
        "campeaoMaisJogadoNome",
        "campeaoMaisJogadoQuantidade",
        "campeaoMaisJogadoVitorias",
        "campeaoMaisJogadoWinratePercent",
        "melhorPartida",
        "piorPartida"
})

public class PlayerStatistics {
    private int partidasAnalisadas;
    private int vitorias;
    private int derrotas;
    private float taxaVitoria;
    private float mediaKills;
    private float mediaDeaths;
    private float mediaAssists;
    private int totalKills;
    private int totalDeaths;
    private int totalAssists;
    private float melhorKda;
    private float piorKda;
    private HistoryEntry melhorPartida;
    private HistoryEntry piorPartida;
    private int winstreakCurrent;
    private int winstreakBest;
    private String campeaoMaisJogadoNome;
    private int campeaoMaisJogadoQuantidade;
    private int campeaoMaisJogadoVitorias;


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
        return String.format("%.0f%%", getTaxaVitoria() * 100);
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

    public int getTotalKills() {
        return totalKills;
    }

    @JsonIgnore
    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getTotalAssists() {
        return totalAssists;
    }

    @JsonIgnore
    public float getMelhorKda() {
        return melhorKda;
    }

    @JsonIgnore
    public float getPiorKda() {
        return piorKda;
    }

    public HistoryEntry getMelhorPartida() {
        return melhorPartida;
    }

    public HistoryEntry getPiorPartida() {
        return piorPartida;
    }

    public int getWinstreakCurrent() {
        return winstreakCurrent;
    }

    public int getWinstreakBest() {
        return winstreakBest;
    }

    public String getCampeaoMaisJogadoNome() {
        return campeaoMaisJogadoNome;
    }

    public int getCampeaoMaisJogadoQuantidade() {
        return campeaoMaisJogadoQuantidade;
    }

    public int getCampeaoMaisJogadoVitorias() {
        return campeaoMaisJogadoVitorias;
    }

    public String getKda() {
        return mediaKills + "/" + mediaDeaths + "/" + mediaAssists;
    }

    @JsonIgnore
    public float getKdaScore() {
        if (mediaDeaths == 0) {
            return mediaKills + mediaAssists;
        }
        return (mediaKills + mediaAssists) / mediaDeaths;
    }

    public float getKdaScoreFormatted() {
        float kda = getKdaScore();
        return Math.round(kda * 100) / 100f;
    }

    @JsonIgnore
    public float getCampeaoMaisJogadoWinrate(){
        return campeaoMaisJogadoVitorias / (float) campeaoMaisJogadoQuantidade;
    }

    public String getCampeaoMaisJogadoWinratePercent() {
        return String.format("%.0f%%", getCampeaoMaisJogadoWinrate() * 100);
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

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public void setTotalAssists(int totalAssists) {
        this.totalAssists = totalAssists;
    }

    public void setMelhorKda(float melhorKda) {
        this.melhorKda = melhorKda;
    }

    public void setPiorKda(float piorKda) {
        this.piorKda = piorKda;
    }

    public void setMelhorPartida(HistoryEntry melhorPartida) {
        this.melhorPartida = melhorPartida;
    }

    public void setPiorPartida(HistoryEntry piorPartida) {
        this.piorPartida = piorPartida;
    }

    public void setWinstreakCurrent(int winstreakCurrent) {
        this.winstreakCurrent = winstreakCurrent;
    }

    public void setWinstreakBest(int winstreakBest) {
        this.winstreakBest = winstreakBest;
    }

    public void setCampeaoMaisJogadoNome(String campeaoMaisJogadoNome) {
        this.campeaoMaisJogadoNome = campeaoMaisJogadoNome;
    }

    public void setCampeaoMaisJogadoQuantidade(int campeaoMaisJogadoQuantidade) {
        this.campeaoMaisJogadoQuantidade = campeaoMaisJogadoQuantidade;
    }

    public void setCampeaoMaisJogadoVitorias(int campeaoMaisJogadoVitorias) {
        this.campeaoMaisJogadoVitorias = campeaoMaisJogadoVitorias;
    }
}
