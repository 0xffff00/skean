package party.threebody.herd.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import party.threebody.skean.dict.model.DualRel;
import party.threebody.skean.dict.model.Ge1Rel;
import party.threebody.skean.dict.model.Ge2Rel;
import party.threebody.skean.dict.model.Word;
import party.threebody.skean.dict.service.WordService;
import party.threebody.skean.mvc.generic.ControllerUtils;

@RestController
@RequestMapping("/f")
public class FileReadController {
	

}
