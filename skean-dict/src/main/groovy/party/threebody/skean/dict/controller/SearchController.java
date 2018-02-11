package party.threebody.skean.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;
import party.threebody.skean.dict.domain.criteria.CritTreeNode;
import party.threebody.skean.dict.service.WordSearchEngine;
import party.threebody.skean.dict.service.WordService;
import party.threebody.skean.web.data.CriteriaBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired CriteriaBuilder criteriaBuilder;
    @Autowired WordSearchEngine searchEngine;

    @Autowired WordService wordService;

    /**
     * search by map-filter-tree
     *
     * @param q
     * @return
     * @deprecated
     */
    @GetMapping("mft")
    public Collection<String> search(@RequestParam(required = false) String q) {
        return searchEngine.searchByJson(q);
    }

    /**
     * A standard RESTful HTTP Endpoint cannot suffice.
     */
    @PostMapping("mft")
    public Collection<String> search1(@RequestBody List<CritTreeNode> critTrees) {
        return searchEngine.search(critTrees);
    }

    @GetMapping("mentioned")
    public List<String> mentioned(@RequestParam Map<String, Object> requestParamMap) {
        CriteriaAndSortingAndPaging csp = criteriaBuilder.toCriteriaAndSortingAndPaging(requestParamMap);
        return wordService.listAllWordsMentioned(csp);
    }

}
