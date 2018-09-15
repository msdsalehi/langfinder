package com.msdsalehi.langfinder.searchengine.util;

import com.msdsalehi.langfinder.dto.SearchObject;
import com.msdsalehi.langfinder.model.ProgrammingLanguage;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 *
 * @author masoud
 */
public class PropertyTokenPoolNarrower implements Callable<Boolean> {

    private final Map<ProgrammingLanguage, Integer> resultMap;
    private final Map<Integer, TokenPool> tokenPoolsMap;
    private final SearchObject searchObject;
    private final List<ProgrammingLanguage> languages;

    public PropertyTokenPoolNarrower(Map<ProgrammingLanguage, Integer> resultMap, Map<Integer, TokenPool> tokenPoolsMap,
            SearchObject searchObject, List<ProgrammingLanguage> languages) {
        this.resultMap = resultMap;
        this.tokenPoolsMap = tokenPoolsMap;
        this.searchObject = searchObject;
        this.languages = languages;
    }

    @Override
    public Boolean call() throws Exception {
        for (Integer languageKey : tokenPoolsMap.keySet()) {
            int relevancyLevel = 0;
            for (String negativeSubPhrase : searchObject.getNegativeSubPhraseList()) {
                relevancyLevel += tokenPoolsMap.get(languageKey).checkRelevancyLevel(negativeSubPhrase);
            }
            if (relevancyLevel > 0) {
                resultMap.remove(languages.get(languageKey));
            }
        }
        return true;
    }

}
