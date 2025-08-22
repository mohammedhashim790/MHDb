package com.mhdb.mhdb.Bloc.Pattern;

public class Pattern {

    public String patternName;

    public String pattern;

    public Pattern(String patternName, String pattern) {
        this.patternName = patternName;
        this.pattern = pattern;
    }

    /**
     * Validates Input pattern with the pattern generated to verify by the system.
     * @param inputPattern
     * @return
     */
    public boolean validate(String inputPattern) {
        return inputPattern.equalsIgnoreCase(patternName);
    }

}
