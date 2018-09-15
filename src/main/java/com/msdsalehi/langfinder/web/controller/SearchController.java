package com.msdsalehi.langfinder.web.controller;

import com.msdsalehi.langfinder.model.ProgrammingLanguage;
import com.msdsalehi.langfinder.service.SearchService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author masoud
 */
@Controller
public class SearchController {

    @Resource(name = "searchService")
    private SearchService searchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public @ResponseBody
    List<ProgrammingLanguage> search(@RequestParam("phrase") String phrase) {
        final String searchedPhrase = phrase.replace("!-!", "#");
        return searchService.search(searchedPhrase);
    }
}
