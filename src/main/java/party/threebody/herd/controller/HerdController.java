package party.threebody.herd.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import party.threebody.herd.domain.HerdCatalog;
import party.threebody.herd.domain.HerdFile;
import party.threebody.herd.service.HerdService;
import party.threebody.skean.mvc.generic.AffectCount;
import party.threebody.skean.mvc.generic.ControllerUtils;

@RestController
@RequestMapping("/herd")
public class HerdController {

	@Autowired
	HerdService herdService;
	
	@GetMapping("/catalogs")
	public List<HerdCatalog> listHerdCatalogs() {
		return herdService.listCatalogs();
	}
	
	@PostMapping("/catalogs")
	public ResponseEntity<AffectCount> syncCatalogs(@RequestParam("action") String action) {
		switch(action){
		case "sync":
			return ResponseEntity.ok().body(herdService.syncCatalogs());
			default:
		}
		return ResponseEntity.badRequest().body(AffectCount.NOTHING);
		
	}
	
	@GetMapping("/files")
	public ResponseEntity<List<HerdFile>> listHerdFiles(@RequestParam Map<String, String> reqestParamMap) {
		return ControllerUtils.respondListAndCount(reqestParamMap, herdService::listHerdFiles, herdService::countHerdFiles);
	}
}
