package party.threebody.skean.samples.navyapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import party.threebody.skean.samples.navyapp.domain.Ship;
import party.threebody.skean.web.mvc.controller.SinglePKCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.SinglePKUriVarCrudRestController;

import java.util.Map;

@RestController
@RequestMapping("v2/fleets/{fleetCode}/ships")

public class FleetShipController extends SinglePKUriVarCrudRestController<Ship,String> {

    @Override
    public void buildCrudFunctions(SinglePKCrudFunctionsBuilder<Ship, String> builder) {
        builder.fromSinglePKCrudDAO(null);
    }

    @GetMapping("{code}")
    public Object get1(@PathVariable String fleetCode, @PathVariable String code) {
        return fleetCode + "," + code;
    }

    @GetMapping("a/{x}")
    public Object get1(@PathVariable Map<String,String> params){
        return params;

    }
}
