package party.threebody.skean.samples.navyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import party.threebody.skean.samples.navyapp.dao.ShipDAO;
import party.threebody.skean.samples.navyapp.domain.Ship;
import party.threebody.skean.web.mvc.controller.SinglePKCrudFunctionsBuilder;
import party.threebody.skean.web.mvc.controller.SinglePKUriVarCrudRestController;

@RestController
@RequestMapping("ships2")
public class ShipController2 extends SinglePKUriVarCrudRestController<Ship, String> {

    @Autowired
    ShipDAO shipDAO;


    @Override
    public void buildCrudFunctions(SinglePKCrudFunctionsBuilder<Ship, String> builder) {
        builder.fromSinglePKCrudDAO(shipDAO);
    }


}

