package io.github.soapiesophia.soapieleagueanalytics.dto;

public class Participant {
    private String puuid;
    private String championName;
    private int kills;
    private int deaths;
    private int assists;
    private boolean win;

    public String getPuuid() {
        return puuid;
    }

    public String getChampionName() {
        return championName;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getAssists() {
        return assists;
    }

    public boolean isWin() {
        return win;
    }
}
