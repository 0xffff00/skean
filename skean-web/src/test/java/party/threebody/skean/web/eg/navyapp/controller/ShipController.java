package party.threebody.skean.web.eg.navyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import party.threebody.skean.web.mvc.MapUtils;
import party.threebody.skean.web.mvc.controller.CrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.SinglePKCrudRestController;
import party.threebody.skean.web.eg.navyapp.domain.Ship0;
import party.threebody.skean.web.eg.navyapp.service.ShipService;

import java.util.Map;

@RestController
@RequestMapping("ships")
public class ShipController extends SinglePKCrudRestController<Ship0, String> {

    @Autowired
    ShipService shipService;

    @Override
    public void buildCrudFunctions(CrudFunctionsBuilder<Ship0, String> builder) {
        builder.countReader(shipService::countShips)
                .listReader(shipService::listShips)
                .oneReader(shipService::getShip)
                .creatorWithReturn(shipService::createAndGet)
                .entireUpdater(shipService::update)
                .partialUpdater(shipService::partialUpdate)
                .deleter(shipService::delete)
                .pkGetter(Ship0::getCode);
    }


    @GetMapping("/2222")
    @Override
    public ResponseEntity<Ship0> httpReadOne(String s) {
        return super.httpReadOne(s);
    }

    @GetMapping("/{pk}")
    public ResponseEntity<Object> httpReadOne(@MatrixVariable String a, @MatrixVariable Integer b) {

        return ResponseEntity.ok().body(a + ":" + b);
    }

    // illegal: #%/;
    @GetMapping("/x/{x:.+}")
    public ResponseEntity<Object> x(@MatrixVariable Map<String, String> matrixVars) {

        return ResponseEntity.ok().body(matrixVars);
    }

    @GetMapping("/y/{y}")
    public ResponseEntity<Object> y(@PathVariable String[] y) {

        return ResponseEntity.ok().body(y);
    }

    @GetMapping("/z/{z:.+}")
    public ResponseEntity<Object> z(@MatrixVariable MultiValueMap<String, String> matrixVars) {
        return ResponseEntity.ok().body(MapUtils.toMap(matrixVars));
    }
}

