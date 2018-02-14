package party.threebody.skean.samples.navyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import party.threebody.skean.samples.navyapp.domain.Ship;
import party.threebody.skean.samples.navyapp.service.ShipService;
import party.threebody.skean.web.mvc.MultiValueMaps;
import party.threebody.skean.web.mvc.controller.SinglePKCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.SinglePKUriVarCrudRestController;

import java.util.Map;

@RestController
@RequestMapping("ships")
public class ShipController extends SinglePKUriVarCrudRestController<Ship, String> {

    @Autowired
    ShipService shipService;


    @Override
    public void buildCrudFunctions(SinglePKCrudFunctionsBuilder<Ship, String> builder) {
        builder.pkNameSupplier("code")
                .pkGetter(Ship::getCode)
                .countReader(shipService::countShips)
                .listReader(shipService::listShips)
                .oneReader(shipService::getShip)
                .oneCreatorWithReturn(shipService::createAndGet)
                .oneUpdater(shipService::update)
                .onePartialUpdater(shipService::partialUpdate)
                .oneDeleter(shipService::delete);
    }

    @GetMapping("/hi")
    public String hi() {
        return "hi";
    }

    @GetMapping("/2222")
    @Override
    public ResponseEntity<Ship> httpReadOne(String s) {
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
        return ResponseEntity.ok().body(MultiValueMaps.toMap(matrixVars));
    }
}

