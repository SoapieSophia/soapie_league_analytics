package io.github.soapiesophia.soapieleagueanalytics.dto;

public class MatchInfo {
    private int gameDuration;
    private String gameMode;
    private Participant[] participants;

    public int getGameDuration() {
        return gameDuration;
    }

    public String getGameMode() {
        return gameMode;
    }

    public Participant[] getParticipants() {
        return participants;
    }
}
