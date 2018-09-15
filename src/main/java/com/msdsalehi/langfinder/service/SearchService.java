package com.msdsalehi.langfinder.service;

import com.msdsalehi.langfinder.model.ProgrammingLanguage;
import java.util.List;

/**
 *
 * @author masoud
 */
public interface SearchService {
    List<ProgrammingLanguage> search(String searchPhrase);
}
