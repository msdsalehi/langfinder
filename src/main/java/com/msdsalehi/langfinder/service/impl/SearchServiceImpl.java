package com.msdsalehi.langfinder.service.impl;

import com.msdsalehi.langfinder.dto.SearchObject;
import com.msdsalehi.langfinder.model.ProgrammingLanguage;
import com.msdsalehi.langfinder.searchengine.SearchEngine;
import com.msdsalehi.langfinder.service.SearchService;
import java.util.List;

/**
 *
 * @author masoud
 */
public class SearchServiceImpl implements SearchService{

    private SearchEngine searchEngine;

    public void setSearchEngine(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }
    
    @Override
    public List<ProgrammingLanguage> search(String searchPhrase) {        SearchObject searchObject = new SearchObject(searchPhrase);
        List<ProgrammingLanguage> searchResult = searchEngine.search(searchObject);
        return searchResult;
    }
    
}
