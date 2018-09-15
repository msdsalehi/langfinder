package com.msdsalehi.langfinder.searchengine.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author masoud
 */
public class TokenPool {

    private final List<String> tokens = new ArrayList<>();

    public void addtoken(String token) {
        tokens.add(token);
    }

    public int checkRelevancyLevel(String phrase) {
        int relevancyLevel = 0;
        for (String token : tokens) {
            if (token.equalsIgnoreCase(phrase)) {
                relevancyLevel += 50;
            } else if (token.toLowerCase().contains(phrase.toLowerCase())) {
                relevancyLevel++;
            }
        }
        return relevancyLevel;
    }
}
