package com.msdsalehi.langfinder.searchengine.impl;

import com.msdsalehi.langfinder.dto.SearchObject;
import com.msdsalehi.langfinder.model.ProgrammingLanguage;
import com.msdsalehi.langfinder.searchengine.DataProvider;
import com.msdsalehi.langfinder.searchengine.SearchEngine;
import com.msdsalehi.langfinder.searchengine.util.PropertyTokenPoolMapper;
import com.msdsalehi.langfinder.searchengine.util.PropertyTokenPoolNarrower;
import com.msdsalehi.langfinder.searchengine.util.PropertyTokenPoolReducer;
import com.msdsalehi.langfinder.searchengine.util.TokenPool;
import com.msdsalehi.langfinder.throwable.exception.GeneralSystemException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.log4j.Logger;

/**
 *
 * @author masoud
 */
public class ConcurrentSearchEngine implements SearchEngine {

    private final static Logger logger = Logger.getLogger(ConcurrentSearchEngine.class);
    private Map<Integer, TokenPool> nameMap;
    private Map<Integer, TokenPool> typeMap;
    private Map<Integer, TokenPool> desigedByMap;
    private final List<ProgrammingLanguage> languages;

    public ConcurrentSearchEngine(DataProvider dataProvider) {
        this.languages = dataProvider.load();
        mapData();
    }

    @Override
    public List<ProgrammingLanguage> search(SearchObject searchObject) {
        Map<ProgrammingLanguage, Integer> resultMap = new HashMap<>();
        reduceDataForResults(searchObject, resultMap);
        narrowResultsDown(searchObject, resultMap);
        List<ProgrammingLanguage> resList = prepareSortedResultsAsList(resultMap);
        return resList;
    }

    private void narrowResultsDown(SearchObject searchObject, Map<ProgrammingLanguage, Integer> resultMap) {
        if (searchObject.getNegativeSubPhraseList().size() > 0) {
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
            PropertyTokenPoolNarrower nameNarrower
                    = new PropertyTokenPoolNarrower(resultMap, nameMap, searchObject, languages);
            PropertyTokenPoolNarrower typeNarrower
                    = new PropertyTokenPoolNarrower(resultMap, typeMap, searchObject, languages);
            PropertyTokenPoolNarrower desinedByNarrower
                    = new PropertyTokenPoolNarrower(resultMap, desigedByMap, searchObject, languages);
            executor.submit(nameNarrower);
            executor.submit(typeNarrower);
            executor.submit(desinedByNarrower);
            shutExecutorDown(executor);
        }
    }

    private void reduceDataForResults(SearchObject searchObject, Map<ProgrammingLanguage, Integer> resultMap) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        Map<ProgrammingLanguage, Integer> namesResult = new HashMap<>();
        Map<ProgrammingLanguage, Integer> typesResult = new HashMap<>();
        Map<ProgrammingLanguage, Integer> designedByResult = new HashMap<>();
        PropertyTokenPoolReducer nameReducer
                = new PropertyTokenPoolReducer(namesResult, nameMap, searchObject, languages);
        PropertyTokenPoolReducer typeReducer
                = new PropertyTokenPoolReducer(typesResult, typeMap, searchObject, languages);
        PropertyTokenPoolReducer desinedByReducer
                = new PropertyTokenPoolReducer(designedByResult, desigedByMap, searchObject, languages);
        executor.submit(nameReducer);
        executor.submit(typeReducer);
        executor.submit(desinedByReducer);
        shutExecutorDown(executor);
        mergePropertyResults(namesResult, resultMap);
        mergePropertyResults(typesResult, resultMap);
        mergePropertyResults(designedByResult, resultMap);
    }

    private void shutExecutorDown(ThreadPoolExecutor executor) {
        executor.shutdown();
        try {
            executor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            logger.error("Error while searching", ex);
        }
    }

    private void mapData() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        Future<Map<Integer, TokenPool>> nameFuture = executor.submit(new PropertyTokenPoolMapper("name", languages));
        Future<Map<Integer, TokenPool>> typeFuture = executor.submit(new PropertyTokenPoolMapper("type", languages));
        Future<Map<Integer, TokenPool>> designedByFuture = executor.submit(new PropertyTokenPoolMapper("designedBy", languages));
        try {
            nameMap = nameFuture.get(30, TimeUnit.SECONDS);
            typeMap = typeFuture.get(30, TimeUnit.SECONDS);
            desigedByMap = designedByFuture.get(30, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            String errorMsg = "Error loading data";
            logger.error(errorMsg, ex);
            throw new GeneralSystemException(errorMsg, ex);
        }
        executor.shutdown();
    }

    private void mergePropertyResults(Map<ProgrammingLanguage, Integer> typesResult,
            Map<ProgrammingLanguage, Integer> resultMap) {
        for (ProgrammingLanguage typeRes : typesResult.keySet()) {
            Integer currentRelevancyLevel = resultMap.get(typeRes);
            if (currentRelevancyLevel == null) {
                currentRelevancyLevel = 0;
            }
            resultMap.put(typeRes, currentRelevancyLevel + typesResult.get(typeRes));
        }
    }

    private List<ProgrammingLanguage> prepareSortedResultsAsList(Map<ProgrammingLanguage, Integer> resultMap) {
        List<ProgrammingLanguage> resList = new ArrayList<>();
        TreeMap<ProgrammingLanguage, Integer> sortedMap = new TreeMap<>();
        for (ProgrammingLanguage resLang : resultMap.keySet()) {
            ProgrammingLanguage finalResLang;
            try {
                finalResLang = resLang.clone();
                finalResLang.setRelevancyLevel(resultMap.get(resLang));
                resList.add(finalResLang);
            } catch (CloneNotSupportedException ex) {
                logger.error("Error occured while computing results", ex);
            }
        }
        Collections.sort(resList);
        return resList;
    }
}
