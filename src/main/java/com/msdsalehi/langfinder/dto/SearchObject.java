package com.msdsalehi.langfinder.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 *
 * @author masoud
 */
public class SearchObject {

    private final static Logger logger = Logger.getLogger(SearchObject.class);
    private final List<String> positiveSubPhraseList = new ArrayList<>();
    private final List<String> negativeSubPhraseList = new ArrayList<>();

    public SearchObject(String searchPhrase) {
        fillUpSubPhrases(searchPhrase);
    }

    public List<String> getPositiveSubPhraseList() {
        return positiveSubPhraseList;
    }

    public List<String> getNegativeSubPhraseList() {
        return negativeSubPhraseList;
    }

    private void fillUpSubPhrases(String searchPhrase) {
        searchPhrase = cleanUpSearchPhrase(searchPhrase, "[,;?!$~*]");
        final List<String> quotedNegativeSubPhrases = findSubPhrasesByPattern(searchPhrase, "-\"([^\"]+)\"|-'([^']+)'");
        searchPhrase = removeSubPhrases(searchPhrase, quotedNegativeSubPhrases);
        final List<String> quotedPositiveSubPhrases = findSubPhrasesByPattern(searchPhrase, "\"([^\"]+)\"|'([^']+)'");
        searchPhrase = removeSubPhrases(searchPhrase, quotedPositiveSubPhrases);
        negativeSubPhraseList.addAll(quotedNegativeSubPhrases);
        positiveSubPhraseList.addAll(quotedPositiveSubPhrases);
        searchPhrase = cleanUpSearchPhrase(searchPhrase, "-\\s|-'|-\"");
        searchPhrase = cleanUpSearchPhrase(searchPhrase, "[\"']");
        fillUpSimplePhrases(searchPhrase);
    }

    private String cleanUpSearchPhrase(String searchPhrase, String badCharactersPattern) {
        searchPhrase = searchPhrase.replaceAll(badCharactersPattern, " ");
        return searchPhrase;
    }

    private String removeSubPhrases(String searchPhrase, List<String> phrases) {
        for (String phrase : phrases) {
            searchPhrase = searchPhrase.replace(phrase, " ");
        }
        return searchPhrase;
    }

    private static List<String> findSubPhrasesByPattern(String searchPhrase, String patternStr) {
        List<String> retList = new ArrayList<>();
        try {
            Pattern pattern = Pattern.compile(patternStr);
            final Matcher matcher = pattern.matcher(searchPhrase);
            int groupCount = matcher.groupCount();
            for (int i = 0; i < groupCount; i++) {
                matcher.find();
                String group = matcher.group(0);
                group = group.replaceAll("[\"']", "");
                group = group.trim();
                if (group.startsWith("-")) {
                    group = group.substring(1);
                }
                retList.add(group);
            }
        } catch (Exception ex) {
            String errorMsg = "Error in processing search phrase";
            logger.debug(errorMsg, ex);
        }
        return retList;
    }

    private void fillUpSimplePhrases(String searchPhrase) {
        if (searchPhrase.trim().length() > 0) {
            final List<String> simplePhrases = Arrays.asList(searchPhrase.trim().split("\\s+"));
            for (String simplePhrase : simplePhrases) {
                String trimmed = simplePhrase.trim();
                if (trimmed.startsWith("-")) {
                    negativeSubPhraseList.add(trimmed.substring(1));
                } else {
                    positiveSubPhraseList.add(trimmed);
                }
            }
        }
    }
}
