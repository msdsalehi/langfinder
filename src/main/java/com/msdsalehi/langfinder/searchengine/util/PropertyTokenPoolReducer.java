package com.msdsalehi.langfinder.searchengine.util;

import com.msdsalehi.langfinder.dto.SearchObject;
import com.msdsalehi.langfinder.model.ProgrammingLanguage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 *
 * @author masoud
 */
public class PropertyTokenPoolReducer implements Callable<Boolean> {

    private final Map<ProgrammingLanguage, Integer> resultMap;
    private final Map<Integer, TokenPool> tokenPoolsMap;
    private final SearchObject searchObject;
    private final List<ProgrammingLanguage> languages;

    public PropertyTokenPoolReducer(Map<ProgrammingLanguage, Integer> resultMap, Map<Integer, TokenPool> tokenPoolsMap,
            SearchObject searchObject, List<ProgrammingLanguage> languages) {
        this.resultMap = resultMap;
        this.tokenPoolsMap = tokenPoolsMap;
        this.searchObject = searchObject;
        this.languages = languages;
    }

    @Override
    public Boolean call() throws Exception {
        Map<Integer, Integer> languageRelevancyLevelMap = new HashMap<>();
        for (Integer languageKey : tokenPoolsMap.keySet()) {
            int relevancyLevel = 0;
            for (String positiveSubPhrase : searchObject.getPositiveSubPhraseList()) {
                relevancyLevel += tokenPoolsMap.get(languageKey).checkRelevancyLevel(positiveSubPhrase);
            }
            if (relevancyLevel > 0) {
                Integer currentRelevancyLevel = languageRelevancyLevelMap.get(languageKey);
                if (currentRelevancyLevel == null) {
                    currentRelevancyLevel = 0;
                }
                languageRelevancyLevelMap.put(languageKey, currentRelevancyLevel + relevancyLevel);
            }
        }
        for (Integer langIndex : languageRelevancyLevelMap.keySet()) {
            final ProgrammingLanguage foundLang = languages.get(langIndex);
            resultMap.put(foundLang, languageRelevancyLevelMap.get(langIndex));
        }
        return true;
    }

}
