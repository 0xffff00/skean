package party.threebody.herd.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import party.threebody.herd.domain.Repo;
import party.threebody.herd.domain.RepoFile;
import party.threebody.herd.service.HerdService;
import party.threebody.skean.mvc.generic.AffectCount;
import party.threebody.skean.mvc.generic.ControllerUtils;

@RestController
@RequestMapping("/herd")
public class HerdController {

	@Autowired
	HerdService herdService;
	
	@GetMapping("/repos")
	public List<Repo> listRepos() {
		return herdService.listRepos();
	}
	
	@PostMapping("/repos")
	public ResponseEntity<AffectCount> syncRepos(@RequestParam("action") String action) {
		switch(action){
		case "sync":
			return ResponseEntity.ok().body(herdService.syncRepos());
			case "peek":
				return ResponseEntity.ok().body(AffectCount.ofOnlyCreated((int)herdService.peekRepos()));
			default:
		}
		return ResponseEntity.badRequest().body(AffectCount.NOTHING);
		
	}

	@ResponseBody
	@GetMapping(value = "/pic2/{hash}.jpg", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> testphoto(@PathVariable String hash) {
		try {
			byte[] res=herdService.getRepoFileBytes(hash);
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
