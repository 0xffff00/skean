package party.threebody.skean.web.eg.navyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import party.threebody.skean.web.eg.navyapp.dao.ShipDAO;
import party.threebody.skean.web.eg.navyapp.domain.Ship;
import party.threebody.skean.web.eg.navyapp.service.ShipService;
import party.threebody.skean.web.mvc.MultiValueMaps;
import party.threebody.skean.web.mvc.controller.SinglePKCrudFunctions;
import party.threebody.skean.web.mvc.controller.SinglePKUriParamCrudRestController;

import java.util.Map;

@RestController
@RequestMapping("ships2")
public class ShipController2 extends SinglePKUriParamCrudRestController<Ship, String> {

    @Autowired
    ShipDAO shipDAO;


    @Override
    public void buildCrudFunctions(SinglePKCrudFunctions.Builder<Ship, String> builder) {
        builder.fromSinglePKJpaCrudDAO(shipDAO);
    }


}

