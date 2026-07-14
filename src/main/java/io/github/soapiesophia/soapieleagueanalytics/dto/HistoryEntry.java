package io.github.soapiesophia.soapieleagueanalytics.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "championName",
        "win",
        "kills",
        "deaths",
        "assists",
        "kda",
        "gameDuration",
        "gameMode"
})

public class HistoryEntry {
    private String championName;
    private boolean win;
    private int kills;
    private int deaths;
    private int assists;
    private int gameDuration;
    private String gameMode;

    public String getChampionName() {
        return championName;
    }

    public boolean isWin() {
        return win;
    }

    @JsonIgnore
    public int getKills() {
        return kills;
    }

    @JsonIgnore
    public int getDeaths() {
        return deaths;
    }

    @JsonIgnore
    public int getAssists() {
        return assists;
    }

    public int getGameDuration() {
        return gameDuration;
    }

    public String getGameMode() {
        return gameMode;
    }

    public String getKda() {
        return kills + "/" + deaths + "/" + assists;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void setGameDuration(int gameDuration) {
        this.gameDuration = gameDuration;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }
}
