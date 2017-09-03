package party.threebody.herd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import party.threebody.herd.domain.Repo;
import party.threebody.herd.domain.RepoFile;
import party.threebody.herd.service.HerdService;
import party.threebody.skean.mvc.generic.AffectCount;
import party.threebody.skean.mvc.generic.ControllerUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/herd")
public class HerdController {

    @Autowired
    HerdService herdService;

    @GetMapping("/repos")
    public List<Repo> listRepos() {
        return herdService.listAliveRepos();
    }

    @PostMapping("/repos")
    public ResponseEntity<AffectCount> actOnRepos(@RequestParam("action") String action,
                                                  @RequestParam(required = false, name = "syncMode") String pSyncMode) {
        HerdService.SyncMode syncMode = (pSyncMode == null) ?
                HerdService.SyncMode.UPDATE : HerdService.SyncMode.valueOf(pSyncMode);
        switch (action) {
            case "track":
                return ResponseEntity.ok().body(herdService.trackAllRepos(syncMode));
            case "analyze":
                return ResponseEntity.ok().body(herdService.analyzeAllRepoFiles());
            default:
        }
        return ResponseEntity.badRequest().body(AffectCount.NOTHING);
    }

    @ResponseBody
    @GetMapping(value = "/pic2/{hash}.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> testphoto(@PathVariable String hash) {
        try {
            byte[] res = herdService.getRepoFileBytes(hash);
            return ResponseEntity.ok().body(res);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @GetMapping("/files")
    public ResponseEntity<List<RepoFile>> listHerdFiles(@RequestParam Map<String, String> reqestParamMap) {
        return ControllerUtils.respondListAndCount(reqestParamMap, herdService::listRepoFiles, herdService::countRepoFiles);
    }
}
