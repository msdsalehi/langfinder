package com.msdsalehi.langfinder.searchengine.impl;


import com.msdsalehi.langfinder.dto.SearchObject;
import com.msdsalehi.langfinder.model.ProgrammingLanguage;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author masoud
 */
public class FileBasedSearchEngineTest {
    
    private final ConcurrentSearchEngine instance;

    public FileBasedSearchEngineTest() {
        instance = new ConcurrentSearchEngine(new FileBasedDataProvider("/search-sources/", "pr.json"));
    }

    @Test
    public void caseNine() {
        SearchObject searchObject = new SearchObject("Object-oriented");
        List<ProgrammingLanguage> results = instance.search(searchObject);
        assertEquals(36, results.size());
    }

    @Test
    public void caseEight() {
        SearchObject searchObject = new SearchObject("'Object-oriented'");
        List<ProgrammingLanguage> results = instance.search(searchObject);
        assertEquals(36, results.size());
    }

    @Test
    public void caseSeven() {
        SearchObject searchObject = new SearchObject("Scripting Microsoft");
        List<ProgrammingLanguage> results = instance.search(searchObject);
        assertEquals(30, results.size());
    }

    @Test
    public void caseSix() {
        SearchObject searchObject = new SearchObject("john -array");
        List<ProgrammingLanguage> results = instance.search(searchObject);
        assertEquals(4, results.size());
    }

    @Test
    public void caseFive() {
        SearchObject searchObject = new SearchObject("Strachan");
        List<ProgrammingLanguage> results = instance.search(searchObject);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getName(), "Groovy");
    }

    @Test
    public void caseFour() {
        SearchObject searchObject = new SearchObject("\"Common Lisp\"");
        List<ProgrammingLanguage> results = instance.search(searchObject);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getName(), "Common Lisp");
    }

    @Test
    public void caseThree() {
        SearchObject searchObject = new SearchObject("'Lisp Common'");
        List<ProgrammingLanguage> results = instance.search(searchObject);
        assertEquals(0, results.size());
    }

    @Test
    public void caseTwo() {
        SearchObject searchObject = new SearchObject("Lisp -Common");
        List<ProgrammingLanguage> results = instance.search(searchObject);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getName(), "Lisp");
    }

    @Test
    public void caseOne() {
        SearchObject searchObject = new SearchObject("'C shell'");
        List<ProgrammingLanguage> results = instance.search(searchObject);
        assertEquals(1, results.size());
        assertEquals(results.get(0).getName(), "Hamilton C shell");
    }
    
}
