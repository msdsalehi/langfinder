package com.msdsalehi.langfinder.searchengine;

import com.msdsalehi.langfinder.model.ProgrammingLanguage;
import java.util.List;

/**
 *
 * @author masoud
 */
public interface DataProvider {
    List<ProgrammingLanguage> load();
}
