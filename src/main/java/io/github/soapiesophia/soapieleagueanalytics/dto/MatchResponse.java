package io.github.soapiesophia.soapieleagueanalytics.dto;

public class MatchResponse {
    private MatchMetadata metadata;
    private MatchInfo info;

    public MatchMetadata getMetadata() {
        return metadata;
    }

    public MatchInfo getInfo() {
        return info;
    }
}
