package com.msdsalehi.langfinder.searchengine.util;

import com.msdsalehi.langfinder.model.ProgrammingLanguage;
import com.msdsalehi.langfinder.throwable.error.DataFormatError;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;

/**
 *
 * @author masoud
 */
public class PropertyTokenPoolMapper implements Callable<Map<Integer, TokenPool>> {

    private final static Logger logger = Logger.getLogger(PropertyTokenPoolMapper.class);
    private final String relatedPropertyOfEachLanguage;
    private final List<ProgrammingLanguage> languages;

    public PropertyTokenPoolMapper(String relatedPropertyOfEachLanguage, List<ProgrammingLanguage> languages) {
        this.relatedPropertyOfEachLanguage = relatedPropertyOfEachLanguage;
        this.languages = languages;
    }

    @Override
    public Map<Integer, TokenPool> call() throws Exception {
        return prepareTokenPoolMap(languages);
    }

    private Map<Integer, TokenPool> prepareTokenPoolMap(List<ProgrammingLanguage> languages) {
        Map<Integer, TokenPool> propertyPoolMap = new HashMap<>();
        int index = 0;
        for (ProgrammingLanguage language : languages) {
            TokenPool pool = prepareTokenPool(language);
            propertyPoolMap.put(index++, pool);
        }
        return propertyPoolMap;
    }

    private TokenPool prepareTokenPool(ProgrammingLanguage language) {
        try {
            TokenPool pool = new TokenPool();
            String relatedPropertyValue = getRelatedProperty(language);
            String[] splittedByComma = relatedPropertyValue.split("[,][\\s]");
            addtokensToPool(pool, splittedByComma);
            for (String splittedValue : splittedByComma) {
                String[] splittedBySpace = splittedValue.split("[\\s]");
                if (splittedBySpace.length > 1) {
                    addtokensToPool(pool, splittedBySpace);
                }
            }
            return pool;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException ex) {
            final String errorMsg = "Error Loading data";
            logger.error(errorMsg, ex);
            throw new DataFormatError(errorMsg, ex);
        }
    }

    private void addtokensToPool(TokenPool pool, String[] splittedValues) {
        for (String splittedValue : splittedValues) {
            pool.addtoken(splittedValue);
        }
    }

    private String getRelatedProperty(ProgrammingLanguage language)
            throws SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        String relatedPropertyValueStr = "";
        String getterMethodName = "get"
                + relatedPropertyOfEachLanguage.substring(0, 1).toUpperCase()
                + relatedPropertyOfEachLanguage.substring(1);
        Method relatedPropertyGetterMethod = language.getClass().getMethod(getterMethodName);
        Object relatedPropertyObject = relatedPropertyGetterMethod.invoke(language);
        if (relatedPropertyObject != null) {
            relatedPropertyValueStr = (String) relatedPropertyObject;
        }
        return relatedPropertyValueStr;
    }
}
