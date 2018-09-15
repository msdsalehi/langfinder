package com.msdsalehi.langfinder.searchengine;

import com.msdsalehi.langfinder.dto.SearchObject;
import com.msdsalehi.langfinder.model.ProgrammingLanguage;
import java.util.List;

/**
 *
 * @author masoud
 */
public interface SearchEngine {

    List<ProgrammingLanguage> search(SearchObject searchObject);
}
