package party.threebody.skean.web.eg.navyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import party.threebody.skean.web.mvc.controller.CrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.SinglePKCrudRestController;
import party.threebody.skean.web.eg.navyapp.domain.Ship;
import party.threebody.skean.web.eg.navyapp.service.ShipService;

import java.util.Map;

@RestController
@RequestMapping("ships")
public class ShipController extends SinglePKCrudRestController<Ship, String> {

    @Autowired
    ShipService shipService;

    @Override
    public void buildCrudFunctions(CrudFunctionsBuilder<Ship, String> builder) {
        builder.countReader(shipService::countShips)
                .listReader(shipService::listShips)
                .oneReader(shipService::getShip)
                .creator(shipService::createAndGet)
                .entireUpdater(shipService::update)
                .partialUpdater(shipService::partialUpdate)
                .deleter(shipService::delete)
                .pkGetter(Ship::getCode);
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

    @GetMapping("/x/{x}")
    public ResponseEntity<Object> x(@MatrixVariable Map<String, Object> matrixVars) {

        return ResponseEntity.ok().body(matrixVars);
    }

    @GetMapping("/y/{y}")
    public ResponseEntity<Object> y(@PathVariable String[] y) {

        return ResponseEntity.ok().body(y);
    }

    @GetMapping("/z/{z}")
    public ResponseEntity<Object> z(@MatrixVariable MultiValueMap<String, Object> matrixVars) {


        return ResponseEntity.ok().body( matrixVars.toSingleValueMap());
    }
}

