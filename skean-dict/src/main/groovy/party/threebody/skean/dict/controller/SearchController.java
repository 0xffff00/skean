package party.threebody.skean.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.dict.service.WordSearchEngine;

import java.util.Collection;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired WordSearchEngine searchEngine;

    /**
     * search by map-filter-tree
     * @param q
     * @return
     */
    @GetMapping("mft")
    public Collection<String> search(@RequestParam(required = false) String q) {
        return searchEngine.searchByJson(q);
    }

}
