package party.threebody.herd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import party.threebody.herd.domain.Media;
import party.threebody.herd.domain.MediaPath;
import party.threebody.herd.domain.Repo;
import party.threebody.herd.service.HerdService;
import party.threebody.herd.util.ImageConverter;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.mvc.generic.AffectCount;
import party.threebody.skean.mvc.generic.ControllerUtils;
import party.threebody.skean.core.query.QueryParamsBuildUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/herd")
public class HerdController {

    @Autowired
    HerdService herdService;

    @GetMapping("/repos")
    public ResponseEntity<List<Repo>> listRepos(@RequestParam Map<String, String> reqestParamMap) {
        return ControllerUtils.respondListAndCountByPLOx(reqestParamMap, herdService::listRepos, herdService::countRepos);
    }

    @GetMapping("/medias")
    public ResponseEntity<List<Media>> listMedias(@RequestParam Map<String, String> reqestParamMap) {
        return ControllerUtils.respondListAndCountByPLOx(reqestParamMap, herdService::listMedias, herdService::countMedias);
    }

    /**
     * [4:3,3:2,16:9]
     * Quarter 1k  [960,1080,1280]x720 0.7~0.9MP
     * Half    2k  [1920,2160,2560]x1440 2.4~3.7MP
     * Full    4k  [3840,4320,5120]x2880 11~15MP
     *
     * @param reqestParamMap
     * @param action
     * @return
     */
    @PostMapping("/repos")
    public ResponseEntity<AffectCount> actOnRepos(@RequestParam Map<String, String> reqestParamMap, @RequestParam("action") String action) {
        QueryParamsSuite qps = QueryParamsBuildUtils.buildQueryParamsSuiteByPLOx(
                reqestParamMap,
                Arrays.asList("name")
        );
        List<Repo> repos = herdService.listRepos(qps);
        List<String> repoNames = repos.stream().map(Repo::getName).collect(Collectors.toList());
        List<MediaPath> mediaPaths = herdService.listMediaPathByRepoNames(repoNames);
        List<Media> medias = herdService.listMediasByRepoNames(repoNames);
        AffectCount afc = AffectCount.NOTHING;
        switch (action) {
            case "sync":
                afc = herdService.synchonizeAndAnalyzeAll(repos);
                break;
            case "sync.path":
                afc = herdService.synchonizeMediaPaths(repos, LocalDateTime.now());
                break;
            case "sync.info.brief":
                afc = herdService.synchonizeMedias(mediaPaths, LocalDateTime.now());
                break;
            case "sync.info.senior":
                afc = herdService.analyzeMedias(medias, LocalDateTime.now());
                break;

            case "clear":
                afc = herdService.clearAll();
                break;
            case "clear.path":
                afc = herdService.clearMediaPaths(repos);
                break;
            case "clear.info.brief":
                afc = herdService.clearMedias(repos);
                break;
            case "clear.info.senior":
                afc = herdService.clearImageMedias(repos);
                break;

            case "convert2jpg.1Kq5":
                ImageConverter JPGC_1Kq5 = ImageConverter.toJPG().name("1Kq5")
                        .edgeNoLessThan(720).edgeNoMoreThan(720 * 4)
                        .compressQuality(0.5).noCompressIfBppBelow(0.12);
                afc = herdService.convertToJpgByMedias(medias, LocalDateTime.now(), JPGC_1Kq5);
                break;
            case "convert2jpg.2Kq7":
                ImageConverter JPGC_2Kq7 = ImageConverter.toJPG().name("2Kq7")
                        .edgeNoLessThan(1440).edgeNoMoreThan(1440 * 4)
                        .compressQuality(0.7).noCompressIfBppBelow(0.12);
                afc = herdService.convertToJpgByMedias(medias, LocalDateTime.now(), JPGC_2Kq7);
                break;
            default:
        }
        return ResponseEntity.badRequest().body(afc);
    }


    @ResponseBody
    @GetMapping(value = "/pic2/{hash}.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> testphoto(@PathVariable String hash,
                                            @RequestParam(name = "cache", required = false) String cacheCategory) {
        try {
            byte[] res = herdService.getMediaFileContent(hash, cacheCategory);
            if (res!=null){
                return ResponseEntity.ok().body(res);
            }else{
                return ResponseEntity.notFound().build();
            }

        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

    }

}
