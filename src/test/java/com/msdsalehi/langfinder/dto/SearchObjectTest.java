package com.msdsalehi.langfinder.dto;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 *
 * @author masoud
 */
public class SearchObjectTest {

    /**
     * Test of getPositiveSubPhraseList method, of class SearchObject.
     */
    @org.junit.Test
    public void testGetPositiveSubPhraseList() {
        List<String> expResult = new ArrayList<>();
        expResult.add("hey hey");
        expResult.add("Hello");
        expResult.add("How");
        expResult.add("Are");
        expResult.add("You");
        expResult.add("fine");
        expResult.add("ok");
        expResult.add("fine");
        expResult.add("I am");
        SearchObject instance = new SearchObject("'hey hey' Hello How Are You? \"I am\" fine -masoud ok "
                + "-'yeah really' fine");
        List<String> results = instance.getPositiveSubPhraseList();
        assertEquals(expResult.size(), results.size());
        for (String result : results) {
            assertTrue(expResult.contains(result));
        }
    }

    /**
     * Test of getNegativeSubPhraseList method, of class SearchObject.
     */
    @org.junit.Test
    public void testGetNegativeSubPhraseList() {
        List<String> expResult = new ArrayList<>();
        expResult.add("masoud");
        expResult.add("yeah really");
        expResult.add("great");
        expResult.add("trtr");
        expResult.add("thats great for sure");
        SearchObject instance = new SearchObject("'hey hey' Hello How Are You? \"I am\" fine -great -masoud -trtr ok "
                + "-\"yeah really\" fine good -'thats great for sure' enough ");
        List<String> results = instance.getNegativeSubPhraseList();
        assertEquals(expResult.size(), results.size());
        for (String result : results) {
            assertTrue(expResult.contains(result));
        }

    }
}
