package party.threebody.herd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import party.threebody.herd.domain.Media;
import party.threebody.herd.domain.Repo;
import party.threebody.herd.service.HerdService;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.mvc.generic.AffectCount;
import party.threebody.skean.mvc.generic.ControllerUtils;
import party.threebody.skean.mvc.util.QueryParamsBuildUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/herd")
public class HerdController {

    @Autowired
    HerdService herdService;

    @GetMapping("/repos")
    public ResponseEntity<List<Repo>> listRepos(@RequestParam Map<String, String> reqestParamMap) {
        return ControllerUtils.respondListAndCount(reqestParamMap, herdService::listRepos,herdService::countRepos);
    }

    @GetMapping("/medias")
    public ResponseEntity<List<Media>> listMedias(@RequestParam Map<String, String> reqestParamMap) {
        return ControllerUtils.respondListAndCount(reqestParamMap, herdService::listMedias,herdService::countMedias);
    }

    @PostMapping("/repos")
    public ResponseEntity<AffectCount> actOnRepos(@RequestParam Map<String, String> reqestParamMap,@RequestParam("action") String action) {
        QueryParamsSuite qps = QueryParamsBuildUtils.buildQueryParamsSuite(reqestParamMap);
        List<Repo> repos=herdService.listRepos(qps);

        switch (action) {
            case "sync":
                return ResponseEntity.ok().body(herdService.synchonizeAndAnalyzeAll(repos));
            case "thumb20":
                return ResponseEntity.ok().body(AffectCount.ofOnlyCreated(herdService.thumbMedias20()));
            case "clear":
                return ResponseEntity.ok().body(herdService.clearAll());
            default:
        }
        return ResponseEntity.badRequest().body(AffectCount.NOTHING);
    }

    @ResponseBody
    @GetMapping(value = "/pic2/{hash}.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> testphoto(@PathVariable String hash) {
        try {
            byte[] res = herdService.getMediaFileContent(hash);
            return ResponseEntity.ok().body(res);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

}
